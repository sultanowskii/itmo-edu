package lib.auth;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

public class Hasher {
    public static String sha512(String input, ResourceBundle messageBundle) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");

            byte[] messageDigestRaw = messageDigest.digest(input.getBytes());

            BigInteger no = new BigInteger(1, messageDigestRaw);

            StringBuilder hashtext = new StringBuilder(no.toString(16));

            while (hashtext.length() < 128) {
                hashtext.insert(0, "0");
            }
  
            return hashtext.toString();
        }

        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(messageBundle.getString("error.programming") + ": " + e.getMessage());
        }
    }
}
