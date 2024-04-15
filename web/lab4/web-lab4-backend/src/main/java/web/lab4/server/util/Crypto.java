package web.lab4.server.util;

import org.apache.commons.codec.digest.DigestUtils;

public class Crypto {
    public static String hash(String s) {
        return DigestUtils.sha256Hex(s);
    }
}
