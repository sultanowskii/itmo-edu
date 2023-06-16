package lib.network;

import java.nio.ByteBuffer;

public class ByteBufferHeadacheResolver {
    public static void removeBytesFromStart(ByteBuffer bf, int n) {
        int index = 0;
        for(int i = n; i < bf.position(); i++) {
            bf.put(index++, bf.get(i));
            bf.put(i, (byte)0);
        }
        bf.position(index);
    }
}
