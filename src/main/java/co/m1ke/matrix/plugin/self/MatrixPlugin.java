package co.m1ke.matrix.plugin.self;

import co.m1ke.matrix.plugin.Plugin;

/**
 * Dummy virtual plugin which allows the the Matrix core
 * to use plugin features such as the Events system.
 *
 * Some features such as the Configuration system are
 * internally disabled to deter tampering with the core's
 * logic and preference systems.
 *
 * Additionally, the Plugin system exempts this plugin
 * from needing a manifest file and protects it from
 * unauthorized loading/unloading. This plugin does not
 * have any features and should only be accessed through
 * <code>Matrix.getSelfPlugin()</code>. If an unauthorized
 * process attempts to disable it, a SecurityException will
 * be thrown. The only authorized caller is {@link co.m1ke.matrix.Matrix}.
 *
 * @author Mike M
 * @since 1.1
 */
public class MatrixPlugin extends Plugin {

    public MatrixPlugin() {
        setName("Matrix");
        setAuthor("Mike M");
        setVersion(0.1);
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

}
