package co.m1ke.matrix.error.plugin;

import co.m1ke.matrix.error.MatrixException;
import co.m1ke.matrix.plugin.Plugin;

public class ConfigNotReadyException extends MatrixException {

    private Plugin origin;

    public ConfigNotReadyException(String message, Plugin origin) {
        super(message);
        this.origin = origin;
    }

    public Plugin getOrigin() {
        return origin;
    }

}
