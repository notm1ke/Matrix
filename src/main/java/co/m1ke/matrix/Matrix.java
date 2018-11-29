package co.m1ke.matrix;

import co.m1ke.matrix.config.ConfigurationManager;
import co.m1ke.matrix.event.EventMatrix;
import co.m1ke.matrix.event.events.ReadyEvent;
import co.m1ke.matrix.logging.Logger;
import co.m1ke.matrix.plugin.PluginManager;
import co.m1ke.matrix.util.Defaults;
import co.m1ke.matrix.util.Lang;
import co.m1ke.matrix.util.Timings;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Matrix {

    private static Logger logger;
    private static PluginManager pluginManager;
    private static ConfigurationManager configurationManager;
    private static EventMatrix eventManager;

    public static void main(String[] args) {
        Timings timings = new Timings("Base", "Initialization");

        logger = new Logger("Base");

        logger.raw(Lang.GREEN + "\n                            _        _      \n" +
                "                /\\/\\   __ _| |_ _ __(_)_  __\n" +
                "               /    \\ / _` | __| '__| \\ \\/ /\n" +
                "              / /\\/\\ \\ (_| | |_| |  | |>  < \n" +
                "              \\/    \\/\\__,_|\\__|_|  |_/_/\\_\\\n" +
                "                                            " + Lang.RESET);
        logger.raw("\t  Argon Development (c) " + new SimpleDateFormat("YYYY").format(new Date()) + " ―― internal use only.\n\n");

        pluginManager = new PluginManager(Defaults.ROOT, logger);
        configurationManager = new ConfigurationManager();
        eventManager = new EventMatrix(pluginManager);

        pluginManager.loadAll();

        timings.complete(() -> eventManager.emit(new ReadyEvent(System.currentTimeMillis())));
    }

    public static ConfigurationManager getConfigurationManager() {
        return configurationManager;
    }

    public static EventMatrix getEventManager() {
        return eventManager;
    }

    public static PluginManager getPluginManager() {
        return pluginManager;
    }
}
