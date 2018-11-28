package co.m1ke.matrix.util;

import java.util.UUID;

public class Comparables {

    public static boolean isNumeric(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isDouble(String input) {
        try {
            Double.parseDouble(input);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isFloat(String input) {
        try {
            Float.parseFloat(input);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isLong(String input) {
        try {
            Long.parseLong(input);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isBoolean(String input) {
        try {
            Boolean.parseBoolean(input);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isUUID(String input) {
        try {
            UUID.fromString(input);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
