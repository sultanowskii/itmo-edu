package server.network;

import lib.auth.Credentials;
import lib.auth.Hasher;
import lib.command.Command;
import lib.command.RawCollectionCommand;
import lib.command.exception.InvalidCommandArgumentException;
import lib.command.parse.CommandResult;
import lib.form.validation.ValidationException;
import lib.manager.ProgramStateManager;
import lib.network.ByteBufferHeadacheResolver;
import lib.network.ClientRequest;
import lib.serialization.Serializator;
import server.db.Database;
import server.runtime.Context;
import server.schema.User;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.locks.ReentrantLock;

public class Server implements Runnable {
    private static final int BUFFER_SIZE = 64 * 1000;
    private static final int TIMEOUT = 300; // (milliseconds)
    private final DatagramChannel socketChannel;
    private final Selector selector;
    private final PrintWriter printWriter;
    private final Context context;

    public Server(Context context, PrintWriter printWriter) throws IOException {
        this("127.0.0.1", 9999, printWriter, context);
    }

    static class ClientRecord {
        public SocketAddress address;
        public Action nextAction = Action.TO_RECEIVE;
        public ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
        public boolean isOccupied = false;
    }

    public Server(String hostname, int port, PrintWriter printWriter, Context context) throws IOException, SocketException {
        this.context = context;
        this.printWriter = printWriter;

        this.socketChannel = DatagramChannel.open();
        this.socketChannel.configureBlocking(false);
        this.socketChannel.socket().bind(new InetSocketAddress(hostname, port));

        this.selector = Selector.open();
        this.socketChannel.register(selector, SelectionKey.OP_READ, new ClientRecord());
    }

    public void run() {
        this.loop();
    }

    public void loop() {
        var messageBundle = context.getMessageBundle();

        ThreadPoolExecutor receiveExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(20);
        ThreadPoolExecutor handleExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(20);
        ThreadPoolExecutor sendExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(20);

        ReentrantLock locker = new ReentrantLock();

        ProgramStateManager programStateManager = ProgramStateManager.getInstance();

        while (programStateManager.getIsRunning()) {
            try {
                locker.lock();
                var keyNumber = this.selector.select(TIMEOUT);
                locker.unlock();

                if (keyNumber == 0) {
                    continue;
                }
            } catch (IOException e) {
                printWriter.println(messageBundle.getString("error.io") + ": " + e.getMessage());
                continue;
            }

            Iterator<SelectionKey> keyIter = selector.selectedKeys().iterator();
            while (keyIter.hasNext()) {
                SelectionKey key = keyIter.next();

                ClientRecord clientRecord = (ClientRecord) key.attachment();

                if (!clientRecord.isOccupied) {
                    if (key.isReadable() && clientRecord.nextAction == Action.TO_RECEIVE) {
                        clientRecord.isOccupied = true;
                        if (clientRecord.address != null) {
                            key.interestOps(SelectionKey.OP_WRITE);
                        }
                        receiveExecutor.execute(
                            () -> {
                                try {
                                    handleRead(key);
                                } catch (IOException e) {
                                    printWriter.println(messageBundle.getString("error.io") + ": " + e.getMessage());
                                }

                                if (clientRecord.address != null) {
                                    key.interestOps(SelectionKey.OP_WRITE);
                                }
                                clientRecord.nextAction = Action.TO_HANDLE;

                                clientRecord.isOccupied = true;

                                handleExecutor.execute(
                                    () -> {
                                        try {
                                            handleRequest(key);
                                        } catch (IOException e) {
                                            printWriter.println(messageBundle.getString("error.io") + ": " + e.getMessage());
                                        } catch (ClassNotFoundException e) {
                                            printWriter.println(messageBundle.getString("error.serialization") + ": " + e.getMessage());
                                        } finally {
                                            clientRecord.isOccupied = false;
                                        }
                                    }
                                );

                                if (clientRecord.address != null) {
                                    key.interestOps(SelectionKey.OP_WRITE);
                                }
                                clientRecord.nextAction = Action.TO_SEND;
                            }
                        );
                    }

                    if (key.isValid() && key.isWritable() && clientRecord.nextAction == Action.TO_SEND) {
                        clientRecord.isOccupied = true;
                        sendExecutor.execute(
                            () -> {
                                int bytesSent = 0;

                                try {
                                    bytesSent = handleWrite(key);
                                } catch (IOException e) {
                                    printWriter.println(messageBundle.getString("error.io") + ": " + e.getMessage());
                                } finally {
                                    clientRecord.isOccupied = false;
                                }

                                if (bytesSent != 0) {
                                    clientRecord.nextAction = Action.TO_RECEIVE;
                                    key.interestOps(SelectionKey.OP_READ);
                                }
                            }
                        );
                    }
                }

                keyIter.remove();
            }
        }
    }

    public void handleRead(SelectionKey key) throws IOException {
        DatagramChannel channel = (DatagramChannel) key.channel();
        ClientRecord clientRecord = (ClientRecord) key.attachment();

        clientRecord.buffer.clear();

        clientRecord.address = channel.receive(clientRecord.buffer);

        int requestSize = clientRecord.buffer.getInt(0);

        ByteBufferHeadacheResolver.removeBytesFromStart(clientRecord.buffer, Integer.BYTES);

        if (requestSize > clientRecord.buffer.capacity()) {
            var tmp = ByteBuffer.allocate(requestSize);
            tmp.put(clientRecord.buffer);
            clientRecord.buffer = tmp;
        }
    }

    private User getUserFromCredentials(Credentials credentials, PrintWriter printWriter) {
        if (credentials.getLogin().isEmpty() || credentials.getPassword().isEmpty()) {
            printWriter.println("Permission denied. Please sign in.");
            return null;
        }

        var hashedPassword = Hasher.sha512(credentials.getPassword(), context.getMessageBundle());

        User user;
        try {
            user = this.context.getDB().getUser(credentials.getLogin(), hashedPassword);
        } catch (SQLException e) {
            printWriter.println("DB error: " + e.getMessage());
            return null;
        }

        if (user == null) {
            printWriter.println("Incorrect login/password.");
        }
        return user;
    }

    public void handleRequest(SelectionKey key) throws IOException, ClassNotFoundException {
        ClientRecord clientRecord = (ClientRecord) key.attachment();

        var messageBundle = context.getMessageBundle();

        byte[] rawRequest = new byte[clientRecord.buffer.position()];
        clientRecord.buffer.flip();
        clientRecord.buffer.get(rawRequest);

        ClientRequest request = Serializator.bytesToObject(rawRequest);

        clientRecord.buffer.clear();

        try {
            Command command = this.context.getCommandManager().getCommandByName(request.getCommandName(), messageBundle);
            System.out.println(command.getName());

            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            PrintWriter resultPrintWriter = new PrintWriter(byteStream, true);

            User user = null;
            if (command.loginRequired()) {
                user = getUserFromCredentials(request.getCredentials(), resultPrintWriter);
            }

            boolean badScenario = (command.loginRequired() && user == null);

            boolean success = false;

            if (!badScenario) {
                success = command.exec(resultPrintWriter, request.getArguments(), request.getObjectArgument(), context, user);
            }

            ByteBuffer rawResult;
            if (command instanceof RawCollectionCommand) {
                rawResult = Serializator.objectToBuffer(context.getPersonManager());
            } else {
                var commandResult = new CommandResult(byteStream.toString(), success);
                rawResult = Serializator.objectToBuffer(commandResult);
            }
            rawResult.flip();

            if (rawResult.capacity() > clientRecord.buffer.capacity()) {
                clientRecord.buffer = rawResult;
            } else {
                clientRecord.buffer.put(rawResult);
            }
        } catch (IOException | NoSuchElementException e) {
            var bytes = e.getMessage().getBytes();
            clientRecord.buffer.put(bytes, 0, bytes.length);
        } catch (InvalidCommandArgumentException e) {
            var bytes = (messageBundle.getString("error.invalidArgument") + ": " + e.getMessage()).getBytes();
            clientRecord.buffer.put(bytes, 0, bytes.length);
        } catch (ValidationException e) {
            var bytes = (messageBundle.getString("error.validation") + ": " + e.getMessage()).getBytes();
            clientRecord.buffer.put(bytes, 0, bytes.length);
        }
    }

    public int handleWrite(SelectionKey key) throws IOException {
        DatagramChannel channel = (DatagramChannel) key.channel();
        ClientRecord clientRecord = (ClientRecord) key.attachment();

        clientRecord.buffer.flip();

        return channel.send(clientRecord.buffer, clientRecord.address);
    }
}

