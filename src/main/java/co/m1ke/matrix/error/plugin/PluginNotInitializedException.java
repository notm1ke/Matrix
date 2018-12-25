package co.m1ke.matrix.error.plugin;

import co.m1ke.matrix.error.MatrixException;
import co.m1ke.matrix.plugin.Plugin;

public class PluginNotInitializedException extends MatrixException {

    private Plugin origin;

    public PluginNotInitializedException(Plugin origin) {
        super(origin.getName() + " has not been initialized.");
        this.origin = origin;
    }

    public Plugin getOrigin() {
        return origin;
    }

}
