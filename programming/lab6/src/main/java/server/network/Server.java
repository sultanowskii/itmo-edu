package server.network;

import lib.command.Command;
import lib.network.ClientRequest;
import lib.serialization.Serializator;
import server.runtime.Context;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Server {
    private static final int BUFFER_SIZE = 128 * 1024;
    private static final int TIMEOUT = 3000; // (milliseconds)
    private final DatagramChannel socketChannel;
    private final Selector selector;
    private boolean active;
    private Context context;

    public Server(Context context) throws IOException {
        this("localhost", 9999, context);
    }

    static class ClientRecord {
        public SocketAddress address;
        public ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
    }

    public Server(String hostname, int port, Context context) throws IOException {
        this.context = context;

        this.socketChannel = DatagramChannel.open();
        this.socketChannel.configureBlocking(false);
        this.socketChannel.socket().bind(new InetSocketAddress(hostname, port));

        this.selector = Selector.open();
        this.socketChannel.register(selector, SelectionKey.OP_READ, new ClientRecord());
        this.active = true;
    }

    public void loop() throws IOException, ClassNotFoundException {
        while (this.active) {
            if (this.selector.select(TIMEOUT) == 0) {
                continue;
            }

            Iterator<SelectionKey> keyIter = selector.selectedKeys().iterator();
            while (keyIter.hasNext()) {
                SelectionKey key = keyIter.next();

                if (key.isReadable()) {
                    var request = handleRead(key);
                    handleRequest(key, request);
                }

                if (key.isValid() && key.isWritable()) {
                    handleWrite(key);
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
        if (clientRecord.address != null) {
            key.interestOps(SelectionKey.OP_WRITE);
        }
        byte[] bytes = new byte[clientRecord.buffer.remaining()];
        clientRecord.buffer.get(bytes);

        ClientRequest request = Serializator.bytesToObject(bytes);

        String msg = new String(bytes);
        System.out.println("got: '" + msg + "' from: " + clientRecord.address);

        return request;
    }

    public void handleRequest(SelectionKey key, ClientRequest request) {
        try {
            Command command = this.context.getCommandManager().getCommandByName(request.getCommandName());
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            PrintWriter printWriter = new PrintWriter(byteStream);

            // TODO: ПОДУМАТЬ НАД ЗАПИСЬЮ: ЧЕ С БУФФЕРОМ?
            ClientRecord clientRecord = (ClientRecord) key.attachment();
            var bytes = byteStream.toByteArray();
            clientRecord.buffer.put(bytes, 0, bytes.length);

            command.exec(printWriter, request.getArguments(), request.getObjectArgument(), context);
        } catch (NoSuchElementException e) {
            System.out.println("command not found :c");
        } catch (IOException e) {
            // TODO: ОБРАБОТАТЬ ИСКЛЮЧЕНИЯ (от исполнения команды)
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

