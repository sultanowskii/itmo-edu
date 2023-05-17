package lib.serialization;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class Serializator {
    public static <T> ByteBuffer objectToBuffer(T object) throws IOException {
        try (
            var byteOut = new ByteArrayOutputStream();
            var objectStream = new ObjectOutputStream(byteOut);
        ) {
            objectStream.writeObject(object);

            byte[] objectBytes = byteOut.toByteArray();

            int objectSize = objectBytes.length;
            var buffer = ByteBuffer.allocate(Integer.BYTES + objectSize);

            buffer.putInt(objectSize);
            buffer.put(objectBytes);

            return buffer;
        }
    }

    public static <T> T bytesToObject(byte[] data) throws IOException, ClassNotFoundException {
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        ObjectInputStream is = new ObjectInputStream(in);
        return (T) is.readObject();
    }

    public static <T> T bytesToObject(byte[] data, int offset) throws IOException, ClassNotFoundException {
        var slice =  Arrays.copyOfRange(data, offset, data.length);
        return bytesToObject(slice);
    }
}
