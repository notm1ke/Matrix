package co.m1ke.matrix.error.plugin;

import co.m1ke.matrix.error.MatrixException;
import co.m1ke.matrix.plugin.Plugin;

public class PluginEventManagerException extends MatrixException {

    private Plugin origin;

    public PluginEventManagerException(Plugin origin) {
        super(origin.getName() + " does not have a valid event manager.");
        this.origin = origin;
    }

    public Plugin getOrigin() {
        return origin;
    }

}
