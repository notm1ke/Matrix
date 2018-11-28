package co.m1ke.matrix.util;

import java.util.Base64;

public class CipherUtils {

    public static String base64Encode(String s) {
        return base64Encode(s.getBytes());
    }

    public static String base64Encode(byte[] bytes) {
        return new String(Base64.getEncoder().encode(bytes));
    }

    public static String base64Decode(String s) {
        return base64Decode(s.getBytes());
    }

    public static String base64Decode(byte[] bytes) {
        return new String(Base64.getDecoder().decode(bytes));
    }

}
