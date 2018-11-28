package co.m1ke.matrix;

import co.m1ke.matrix.logging.Logger;
import co.m1ke.matrix.plugin.PluginManager;
import co.m1ke.matrix.util.JavaUtils;
import co.m1ke.matrix.util.Timings;

public class Matrix {

    private static Logger logger;
    private static PluginManager pluginManager;

    public static void main(String[] args) {
        Timings timings = new Timings("Base", "Initialization");

        logger = new Logger("Base");
        pluginManager = new PluginManager(logger);

        pluginManager.loadAll(JavaUtils.getJarLocation());

        timings.complete();
    }

}
