package co.m1ke.matrix.error.plugin;

import co.m1ke.matrix.error.MatrixException;
import co.m1ke.matrix.plugin.Plugin;

public class PluginAlreadyInitializedException extends MatrixException {

    private Plugin origin;

    public PluginAlreadyInitializedException(Plugin origin) {
        super("Plugin [" + origin.getName() + "] is already initialized.");
        this.origin = origin;
    }

    public Plugin getOrigin() {
        return origin;
    }

}
