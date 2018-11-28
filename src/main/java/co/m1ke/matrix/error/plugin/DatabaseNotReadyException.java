package co.m1ke.matrix.error.plugin;

import co.m1ke.matrix.error.MatrixException;
import co.m1ke.matrix.plugin.Plugin;

public class DatabaseNotReadyException extends MatrixException {

    private Plugin origin;

    public DatabaseNotReadyException(String message, Plugin origin) {
        super(message);
        this.origin = origin;
    }

    public Plugin getOrigin() {
        return origin;
    }

}
