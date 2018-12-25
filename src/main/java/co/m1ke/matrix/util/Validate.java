package co.m1ke.matrix.util;

import co.m1ke.matrix.error.java.BadIndexException;

import java.util.List;
import java.util.Objects;

import com.google.errorprone.annotations.CanIgnoreReturnValue;

public class Validate {

    public static void checkArgument(boolean expr) {
        checkArgument(expr, null);
    }

    public static void checkArgument(boolean expr, String error) {
        if (expr) {
            if (error.isEmpty()) {
                throw new IllegalArgumentException();
            }
            throw new IllegalArgumentException(error);
        }
    }

    public static void checkState(boolean b) {
        checkState(b, null);
    }

    public static void checkState(boolean b, String error) {
        if (b) {
            if (error.isEmpty()) {
                throw new IllegalStateException();
            }
            throw new IllegalStateException(error);
        }
    }

    @CanIgnoreReturnValue
    public static <T> T notNull(T ref) {
        return notNull(ref, null);
    }

    @CanIgnoreReturnValue
    public static <T> T notNull(T ref, String error) {
        if (Objects.isNull(ref)) {
            if (error.isEmpty()) {
                throw new NullPointerException();
            }
            throw new NullPointerException(error);
        }
        return ref;
    }

    public static <T> void badIndex(List<T> list, int index) {
        if (index < 0) {
            throw new BadIndexException("Index (" + index + ") cannot be negative");
        }
        if (index > list.size() - 1) {
            throw new ArrayIndexOutOfBoundsException("Index (" + index + " ) is out of range for " + list.size() + " (+" + (index - (list.size() - 1)) + ")");
        }
    }

}
