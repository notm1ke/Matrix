package co.m1ke.matrix.util;

import co.m1ke.matrix.Matrix;

import java.io.File;
import java.net.URISyntaxException;

public class JavaUtils {

    public static Class<?> getCaller() {
        final StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        String clazzName = stackTrace[stackTrace.length - 1].getClassName();
        try {
            return Class.forName(clazzName);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    public static File getJarLocation() {
        try {
            return new File(Matrix.class.getProtectionDomain().getCodeSource().getLocation().toURI());
        } catch (URISyntaxException e) {
            return null;
        }
    }

}
