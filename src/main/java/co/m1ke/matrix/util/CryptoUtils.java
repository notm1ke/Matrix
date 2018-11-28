package co.m1ke.matrix.util;

import org.apache.commons.codec.binary.Base64;

import java.security.spec.KeySpec;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

public class CryptoUtils {

    private static final String DESEDE_ENCRYPTION_SCHEME = "DESede";
    private static final String UNICODE_FORMAT = "UTF8";

    private KeySpec ks;
    private SecretKeyFactory skf;
    private Cipher cipher;
    private byte[] arrayBytes;
    private String encryptionKey;
    private String encryptionScheme;
    private SecretKey key;

    public CryptoUtils() throws Exception {
        encryptionKey = "ede56e5b-9251-4636-bf5d-303d6d292c341fa898d9-61ec-4a0c-96f3-0a0a0734ca15";
        encryptionScheme = DESEDE_ENCRYPTION_SCHEME;
        arrayBytes = encryptionKey.getBytes(UNICODE_FORMAT);
        ks = new DESedeKeySpec(arrayBytes);
        skf = SecretKeyFactory.getInstance(encryptionScheme);
        cipher = Cipher.getInstance(encryptionScheme);
        key = skf.generateSecret(ks);
    }

    public String encrypt(String unencryptedString) {
        String encryptedString = null;
        try {
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] plainText = unencryptedString.getBytes(UNICODE_FORMAT);
            byte[] encryptedText = cipher.doFinal(plainText);
            encryptedString = new String(Base64.encodeBase64(encryptedText));
        } catch (Exception ignored) {}
        return encryptedString;
    }


    public String decrypt(String encryptedString) {
        String decryptedText = null;
        try {
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] encryptedText = Base64.decodeBase64(encryptedString);
            byte[] plainText = cipher.doFinal(encryptedText);
            decryptedText = new String(plainText);
        } catch (Exception ignored) {}
        return decryptedText;
    }

}
