package co.m1ke.matrix.error.plugin;

import co.m1ke.matrix.error.MatrixException;
import co.m1ke.matrix.plugin.Plugin;

public class InvalidPluginException extends MatrixException {

    private Plugin origin;

    public InvalidPluginException(String message, Plugin origin) {
        super(message);
        this.origin = origin;
    }

    public Plugin getOrigin() {
        return origin;
    }

}
