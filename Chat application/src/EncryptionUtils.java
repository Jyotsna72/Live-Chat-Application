import java.util.Base64;

public class EncryptionUtils {
    public static String encrypt(String message) {
        return Base64.getEncoder().encodeToString(message.getBytes());
    }

    public static String decrypt(String encryptedMessage) {
        return new String(Base64.getDecoder().decode(encryptedMessage));
    }
}
