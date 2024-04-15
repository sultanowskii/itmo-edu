package web.lab4.server.util;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

public class KeyGenerator {
    private final Key secretKeySpec;

    public KeyGenerator(String keyMaterial) {
        this(keyMaterial, "HmacSHA512");
    }

    public KeyGenerator(String keyword, String algorithm) {
        secretKeySpec = new SecretKeySpec(keyword.getBytes(), 0, keyword.getBytes().length, algorithm);
    }

    public Key generate() {
        return secretKeySpec;
    }
}
