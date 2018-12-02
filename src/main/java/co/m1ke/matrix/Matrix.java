package co.m1ke.matrix;

import co.m1ke.matrix.argon.Uplink;
import co.m1ke.matrix.argon.boot.SecureBoot;
import co.m1ke.matrix.config.ConfigurationMatrix;
import co.m1ke.matrix.event.EventMatrix;
import co.m1ke.matrix.event.events.ReadyEvent;
import co.m1ke.matrix.logging.Logger;
import co.m1ke.matrix.plugin.PluginManager;
import co.m1ke.matrix.terminal.Terminal;
import co.m1ke.matrix.util.Defaults;
import co.m1ke.matrix.util.Lang;
import co.m1ke.matrix.util.Timings;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Matrix {

    private static Logger logger;

    private static Uplink uplink;
    private static SecureBoot secureBoot;

    private static Terminal terminal;

    private static PluginManager pluginManager;
    private static ConfigurationMatrix configurationManager;
    private static EventMatrix eventManager;

    public static void main(String[] args) {
        Timings timings = new Timings("Matrix", "Initialization");


        logger = new Logger("Matrix");
        logger.raw(Lang.GREEN + "\n                                     _        _      \n" +
                "                         /\\/\\   __ _| |_ _ __(_)_  __\n" +
                "                        /    \\ / _` | __| '__| \\ \\/ /\n" +
                "                       / /\\/\\ \\ (_| | |_| |  | |>  < \n" +
                "                       \\/    \\/\\__,_|\\__|_|  |_/_/\\_\\\n" +
                "                                                      " + Lang.RESET);
        logger.raw("\t\t\t    Argon Development (c) " + new SimpleDateFormat("YYYY").format(new Date()) + " ―― internal use only.\n\n");
        logger.info("Loading libraries..");

        logger.info("This service is running Matrix version " + Defaults.VERSION + " by Argon Development.");

        pluginManager = new PluginManager(Defaults.ROOT);
        configurationManager = new ConfigurationMatrix();
        eventManager = new EventMatrix(pluginManager);

        terminal = new Terminal();

        uplink = new Uplink(Defaults.DEFAULT_CLOUD_ENDPOINT);
        secureBoot = new SecureBoot();

        pluginManager.loadAll();

        timings.complete(() -> eventManager.emit(new ReadyEvent(System.currentTimeMillis())),
                "Started in %c%tms" + Lang.RESET + ", type `help` to view commands.");
    }

    public static ConfigurationMatrix getConfigurationManager() {
        return configurationManager;
    }

    public static EventMatrix getEventManager() {
        return eventManager;
    }

    public static PluginManager getPluginManager() {
        return pluginManager;
    }
}
