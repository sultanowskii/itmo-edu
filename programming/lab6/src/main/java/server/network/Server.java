package server.network;

import lib.command.Command;
import lib.command.exception.InvalidCommandArgumentException;
import lib.form.validation.ValidationException;
import lib.manager.ProgramStateManager;
import lib.network.ByteBufferHeadacheResolver;
import lib.network.ClientRequest;
import lib.serialization.Serializator;
import server.runtime.Context;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Server implements Runnable {
    private static final int BUFFER_SIZE = 64 * 1000;
    private static final int TIMEOUT = 3000; // (milliseconds)
    private final DatagramChannel socketChannel;
    private final Selector selector;
    private final PrintWriter printWriter;
    private final Context context;

    public Server(Context context, PrintWriter printWriter) throws IOException {
        this("127.0.0.1", 9999, printWriter, context);
    }

    static class ClientRecord {
        public SocketAddress address;
        public ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
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
        ProgramStateManager programStateManager = ProgramStateManager.getInstance();
        while (programStateManager.getIsRunning()) {
            try {
                if (this.selector.select(TIMEOUT) == 0) {
                    continue;
                }
            } catch (IOException e) {
                printWriter.println("IO error during select: " + e.getMessage());
                continue;
            }

            Iterator<SelectionKey> keyIter = selector.selectedKeys().iterator();
            while (keyIter.hasNext()) {
                SelectionKey key = keyIter.next();

                if (key.isReadable()) {
                    ClientRequest request;

                    try {
                        request = handleRead(key);
                    } catch (IOException e) {
                        printWriter.println("IO error: " + e.getMessage());
                        continue;
                    } catch (ClassNotFoundException e) {
                        printWriter.println("Unexpected serialization error: " + e.getMessage());
                        continue;
                    }
                    handleRequest(key, request);
                }

                if (key.isValid() && key.isWritable()) {
                    try {
                        handleWrite(key);
                    } catch (IOException e) {
                        printWriter.println("IO error: " + e.getMessage());
                        continue;
                    }
                }

                keyIter.remove();
            }
        }
    }

    public ClientRequest handleRead(SelectionKey key) throws IOException, ClassNotFoundException {
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

        if (clientRecord.address != null) {
            key.interestOps(SelectionKey.OP_WRITE);
        }

        byte[] bytes = new byte[requestSize];
        clientRecord.buffer.flip();
        clientRecord.buffer.get(bytes);

        return Serializator.bytesToObject(bytes);
    }

    public void handleRequest(SelectionKey key, ClientRequest request) {
        ClientRecord clientRecord = (ClientRecord) key.attachment();
        clientRecord.buffer.clear();

        try {
            Command command = this.context.getCommandManager().getCommandByName(request.getCommandName());
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            PrintWriter printWriter = new PrintWriter(byteStream, true);

            command.exec(printWriter, request.getArguments(), request.getObjectArgument(), context);

            var rawResult = Serializator.objectToBuffer(byteStream.toString());
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
            var bytes = ("Invalid arguments: " + e.getMessage()).getBytes();
            clientRecord.buffer.put(bytes, 0, bytes.length);
        } catch (ValidationException e) {
            var bytes = ("Validation error: " + e.getMessage()).getBytes();
            clientRecord.buffer.put(bytes, 0, bytes.length);
        }
    }

    public void handleWrite(SelectionKey key) throws IOException {
        DatagramChannel channel = (DatagramChannel) key.channel();
        ClientRecord clientRecord = (ClientRecord) key.attachment();
        clientRecord.buffer.flip();
        int bytesSent = channel.send(clientRecord.buffer, clientRecord.address);
        if (bytesSent != 0) {
            key.interestOps(SelectionKey.OP_READ);
        }
    }
}

