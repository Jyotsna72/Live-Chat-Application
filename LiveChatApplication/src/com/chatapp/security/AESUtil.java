package com.chatapp.security;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class AESUtil {

    private static final String SECRET_KEY =
            "1234567890123456";

    public static String encrypt(String message) {

        try {

            SecretKeySpec key =
                    new SecretKeySpec(
                            SECRET_KEY.getBytes(),
                            "AES");

            Cipher cipher =
                    Cipher.getInstance("AES");

            cipher.init(
                    Cipher.ENCRYPT_MODE,
                    key);

            byte[] encrypted =
                    cipher.doFinal(
                            message.getBytes());

            return Base64
                    .getEncoder()
                    .encodeToString(encrypted);

        } catch(Exception e) {

            e.printStackTrace();
        }

        return null;
    }

    public static String decrypt(String encryptedMessage) {

        try {

            SecretKeySpec key =
                    new SecretKeySpec(
                            SECRET_KEY.getBytes(),
                            "AES");

            Cipher cipher =
                    Cipher.getInstance("AES");

            cipher.init(
                    Cipher.DECRYPT_MODE,
                    key);

            byte[] decoded =
                    Base64
                    .getDecoder()
                    .decode(encryptedMessage);

            byte[] decrypted =
                    cipher.doFinal(decoded);

            return new String(decrypted);

        } catch(Exception e) {

            e.printStackTrace();
        }

        return null;
    }
}