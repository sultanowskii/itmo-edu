package client.network;

import client.runtime.ClientContext;
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
    private ClientContext clientContext;

    public Client(String hostname, int port) throws UnknownHostException, SocketException {
        this.hostname = hostname;
        this.port = port;

        InetAddress addr = InetAddress.getByName(hostname);
        SocketAddress sockaddr = new InetSocketAddress(addr, port);

        this.socket = new DatagramSocket();
        this.socket.connect(sockaddr);
    }

    public void setClientContext(ClientContext clientContext) {
        this.clientContext = clientContext;
    }

    public void sendRequest(String commandName, String[] arguments, Serializable additionalObject) throws IOException {
        ClientRequest request = new ClientRequest(this.clientContext.getCredentials(), commandName, arguments, additionalObject);
        ByteBuffer requestBuffer;
        requestBuffer = Serializator.objectToBuffer(request);

        byte[] rawRequest = new byte[requestBuffer.position()];
        requestBuffer.flip();
        requestBuffer.get(rawRequest);

        DatagramPacket packet = new DatagramPacket(rawRequest, rawRequest.length);

        this.socket.send(packet);
    }

    public String getResponse() throws IOException, ClassNotFoundException {
        var rawResponse = new byte[BUFFER_SIZE];

        DatagramPacket responsePacket = new DatagramPacket(rawResponse, BUFFER_SIZE);
        this.socket.receive(responsePacket);

        int responseSize = Converter.intFromByteArray(responsePacket.getData());

        if (responseSize > BUFFER_SIZE) {
            var extendedRawResponse = new byte[responseSize];
            var tmp = new DatagramPacket(extendedRawResponse, responseSize - responsePacket.getLength());
            tmp.setData(responsePacket.getData());
            responsePacket = tmp;
            this.socket.receive(responsePacket);
        }

        return Serializator.bytesToObject(responsePacket.getData(), 4);
    }
}

