package co.m1ke.matrix;

import co.m1ke.matrix.argon.Uplink;
import co.m1ke.matrix.argon.boot.SecureBoot;
import co.m1ke.matrix.closable.Closable;
import co.m1ke.matrix.closable.ClosableOverride;
import co.m1ke.matrix.compat.SpigotCompatibility;
import co.m1ke.matrix.config.ConfigurationMatrix;
import co.m1ke.matrix.event.EventMatrix;
import co.m1ke.matrix.event.events.ReadyEvent;
import co.m1ke.matrix.logging.Logger;
import co.m1ke.matrix.plugin.PluginManager;
import co.m1ke.matrix.plugin.self.MatrixPlugin;
import co.m1ke.matrix.prefs.Preferences;
import co.m1ke.matrix.terminal.Terminal;
import co.m1ke.matrix.util.Defaults;
import co.m1ke.matrix.util.Lang;
import co.m1ke.matrix.util.Timings;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Matrix {

    private static Logger logger;
    private static MatrixPlugin selfPlugin;
    private static Preferences preferences;

    private static Uplink uplink;
    private static SecureBoot secureBoot;

    private static Terminal terminal;

    private static PluginManager pluginManager;
    private static ConfigurationMatrix configurationManager;
    private static EventMatrix eventManager;

    private static SpigotCompatibility spigotCompat;

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
        preferences = new Preferences();

        terminal = new Terminal();

        uplink = new Uplink(Defaults.DEFAULT_CLOUD_ENDPOINT);
        secureBoot = new SecureBoot();

        selfPlugin = new MatrixPlugin();
        selfPlugin.init("Matrix", "Mike M", false, 0.1);

        pluginManager.loadAll();

        spigotCompat = new SpigotCompatibility(preferences);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                logger.info("Closing services..");

                terminal.close();
                pluginManager.close();
                preferences.close();
                eventManager.close();

                Set<URL> classPathList = new HashSet<>(ClasspathHelper.forClassLoader());
                Set<Class<? extends Closable>> result = new Reflections(new ConfigurationBuilder()
                        .setScanners(new SubTypesScanner())
                        .setUrls(classPathList))
                        .getSubTypesOf(Closable.class);

                for (Class<? extends Closable> autoClosable : result) {
                    if (autoClosable.isAssignableFrom(ClosableOverride.class) || Arrays.asList(autoClosable.getInterfaces()).contains(ClosableOverride.class)) {
                        continue;
                    }
                    logger.info(autoClosable.getSimpleName() + " closed.");
                    autoClosable.newInstance().close();
                }
            } catch (Exception e) {
                logger.except(e, "Error closing services");
            }
            logger.info("Matrix service deactivated.");
        }));

        timings.complete(() -> eventManager.emit(new ReadyEvent(System.currentTimeMillis())),
                "Started in %c%tms" + Lang.RESET + ", type `help` to view commands.");
    }

    public static MatrixPlugin getSelfPlugin() {
        return selfPlugin;
    }

    public static Preferences getPreferences() {
        return preferences;
    }

    public static ConfigurationMatrix getConfigurationManager() {
        return configurationManager;
    }

    public static Terminal getTerminal() {
        return terminal;
    }

    public static EventMatrix getEventManager() {
        return eventManager;
    }

    public static PluginManager getPluginManager() {
        return pluginManager;
    }

}
