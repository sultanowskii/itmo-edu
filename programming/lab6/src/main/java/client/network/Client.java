package client.network;

import lib.network.ClientRequest;
import lib.network.Converter;
import lib.serialization.Serializator;

import java.io.IOException;
import java.io.Serializable;
import java.net.*;
import java.nio.ByteBuffer;

public class Client {
    private static final int BUFFER_SIZE = 64 * 1000;
    private final DatagramSocket socket;
    private final String hostname;
    private final int port;

    public Client(String hostname, int port) throws UnknownHostException, SocketException {
        this.hostname = hostname;
        this.port = port;

        InetAddress addr = InetAddress.getByName(hostname);
        SocketAddress sockaddr = new InetSocketAddress(addr, port);

        this.socket = new DatagramSocket();
        this.socket.connect(sockaddr);
    }

    public void sendRequest(String commandName, String[] arguments, Serializable additionalObject) {
        ClientRequest request = new ClientRequest(commandName, arguments, additionalObject);
        ByteBuffer requestBuffer;
        try {
            requestBuffer = Serializator.objectToBuffer(request);
        } catch (IOException e) {
            // TODO: Обработать
            return;
        }

        byte[] rawRequest = new byte[requestBuffer.position()];
        requestBuffer.flip();
        requestBuffer.get(rawRequest);

        DatagramPacket packet = new DatagramPacket(rawRequest, rawRequest.length);

        try {
            this.socket.send(packet);
        } catch (IOException e) {
            // TODO: Обработать
            return;
        }
    }

    public String getResponse() {
        var rawResponse = new byte[BUFFER_SIZE];

        DatagramPacket responsePacket = new DatagramPacket(rawResponse, BUFFER_SIZE);

        try {
            this.socket.receive(responsePacket);
        } catch (IOException e) {
            // TODO: Обработать
            return "err";
        }

        // TODO: Обработка больших пакетов
        int responseSize = Converter.intFromByteArray(responsePacket.getData());

        try {
            return Serializator.bytesToObject(responsePacket.getData(), 4);
        } catch (IOException e) {
            // TODO: Обработать
            return "err";
        } catch (ClassNotFoundException e) {
            // TODO: Обработать!!
            return "err";
        }
    }
}

