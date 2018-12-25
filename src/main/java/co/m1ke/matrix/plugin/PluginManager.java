package co.m1ke.matrix.plugin;

import co.m1ke.matrix.Matrix;
import co.m1ke.matrix.closable.Closable;
import co.m1ke.matrix.closable.ClosableOverride;
import co.m1ke.matrix.error.MatrixException;
import co.m1ke.matrix.error.plugin.InvalidMetadataException;
import co.m1ke.matrix.error.plugin.PluginAlreadyInitializedException;
import co.m1ke.matrix.error.plugin.PluginNotInitializedException;
import co.m1ke.matrix.logging.Logger;
import co.m1ke.matrix.plugin.self.MatrixPlugin;
import co.m1ke.matrix.util.JavaUtils;
import co.m1ke.matrix.util.Lang;
import co.m1ke.matrix.util.TimeUtil;
import co.m1ke.matrix.util.Timings;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;

public class PluginManager implements Closable, ClosableOverride {

    private ArrayList<Plugin> plugins;
    private File root;
    private Logger logger;

    public PluginManager(File root) {
        this.plugins = new ArrayList<>();
        this.root = root;
        this.logger = new Logger("Plugins");
    }

    public void loadAll() {
        Timings timings = new Timings("Plugins", "Initialization");
        logger.unlisted(Lang.DIVIDER);

        new Thread(() -> {
            File pluginsFolder = new File(root, "plugins");
            if (!pluginsFolder.exists()) {
                try {
                    if (!pluginsFolder.mkdir()) {
                        throw new IOException("Error creating directory");
                    }
                } catch (IOException e) {
                    logger.except(e, "Unable to load or create plugins directory");
                    System.exit(-1);
                    return;
                }
            }
            ArrayList<File> jars = new ArrayList<>();

            File[] raw = pluginsFolder.listFiles();
            assert raw != null;

            for (File f : raw) {
                if (FilenameUtils.getExtension(f.getName()).equalsIgnoreCase("jar")) {
                    jars.add(f);
                }
            }

            for (File f : jars) {
                try {
                    URLClassLoader child = new URLClassLoader(new URL[] { f.toURL() }, this.getClass().getClassLoader());

                    InputStream in = child.getResourceAsStream("plugin.json");
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                    JSONObject meta;
                    try {
                        meta = new JSONObject(IOUtils.toString(reader));
                    } catch (JSONException e) {
                        logger.severe("Could not load [" + f.getName() + "] because their plugin manifest file is illegible.");
                        continue;
                    }

                    Class load;
                    try {
                        load = Class.forName(meta.getString("main"), true, child);
                    } catch (ClassNotFoundException e) {
                        logger.severe("Could not find main class [" + meta.getString("main") + "] in [" + f.getName() + "].");
                        continue;
                    }

                    Plugin plugin = (Plugin) load.newInstance();

                    try {
                        plugin.init(meta.getString("name"), meta.getString("author"), meta.getBoolean("debug"), meta.getDouble("version"));
                        plugin.resetLogger();
                    } catch (JSONException e) {
                        throw new InvalidMetadataException("One or more required elements are missing from the plugin manifest file.");
                    }

                    logger.info("Loading " + plugin.getName() + " v" + plugin.getVersion());

                    if (plugins.stream().anyMatch(p -> p.getName().equalsIgnoreCase(plugin.getName()))) {
                        logger.severe("Ambiguous plugin " + plugin.getName() + ", refusing to load.");
                        continue;
                    }

                    try {
                        plugin.onLoad();
                    } catch (MatrixException e) {
                        logger.except(e, "Error loading " + plugin.getName());
                        continue;
                    }

                    if (!plugin.getDataFolder().exists() && !(plugin instanceof MatrixPlugin)) {
                        plugin.getDataFolder().mkdir();
                    }

                    Matrix.getEventManager().registerPlugin(plugin);

                    plugins.add(plugin);
                } catch (Exception e) {
                    logger.severe("An error occurred while loading [" + f.getName() + "].");
                    logger.severe("Printing stacktrace:");
                    e.printStackTrace();
                }
            }

            ArrayList<Plugin> pendingDeath = new ArrayList<>();

            plugins.add(Matrix.getSelfPlugin());

            Plugin mp = plugins.get(plugins.size() - 1);
            logger.info("Loading " + mp.getName() + " v" + mp.getVersion());
            mp.onLoad();
            mp.resetLogger();

            Timings mpt = new Timings("Plugins", "Enable");

            logger.info("Enabling " + mp.getName() + " v" + mp.getVersion());
            mp.onEnable();
            mpt.complete("Enabled " + mp.getName() + " in %c%tms" + Lang.RESET + ".");

            for (Plugin plugin : plugins) {
                if (plugin instanceof MatrixPlugin) {
                    continue;
                }
                Timings enable = new Timings("Plugins", "Enable");
                logger.info("Enabling " + plugin.getName() + " v" + plugin.getVersion());

                try {
                    plugin.onEnable();
                    enable.complete("Enabled " + plugin.getName() + " in %c%tms" + Lang.RESET + ".");
                } catch (MatrixException e) {
                    logger.severe("An error occurred while enabling [" + plugin.getName() + "].");
                    logger.severe("Printing stacktrace:");
                    e.printStackTrace();
                    pendingDeath.add(plugin);
                    continue;
                }

                if (!plugin.getDataFolder().exists()) {
                    plugin.getDataFolder().mkdir();
                }
            }

            for (Plugin plugin : pendingDeath) {
                this.disable(plugin);
            }

            logger.unlisted(Lang.DIVIDER);
            logger.warning("Failed to load " + pendingDeath.size() + " plugin" + TimeUtil.numberEnding(pendingDeath.size()) + ".", pendingDeath.size() >= 1);

            pendingDeath.clear();

            timings.complete("Loaded all plugins in %c%tms" + Lang.RESET + ".");
        }, "Plugin Manager Thread").run();
    }

    public void enable(Plugin plugin) {

        if (checkPermissions(plugin)) {
            return;
        }

        if (plugins.contains(plugin)) {
            throw new PluginAlreadyInitializedException(plugin);
        }

        plugins.add(plugin);

        new Thread(() -> {
            plugin.onLoad();
            plugin.onEnable();
        }, plugin.getName() + " Startup Thread").run();
    }

    public void disable(Plugin plugin) {

        if (checkPermissions(plugin)) return;

        if (!plugins.contains(plugin)) {
            throw new PluginNotInitializedException(plugin);
        }

        Timings t = new Timings("Plugins", "Disable");
        logger.info("Disabling " + plugin.getName() + " v" + plugin.getVersion());

        plugins.remove(plugin);
        plugin.getConfiguration().getConfigurations().forEach((s, c) -> {
            try {
                c.save();
            } catch (Exception e) {
                logger.except(e, "Error saving configuration for " + c.getPlugin().getName());
            }
        });
        plugin.onDisable();
        t.complete("Disabled " + plugin.getName() + " in %c%tms" + Lang.RESET + ".");
    }

    public Plugin get(Class<? extends Plugin> clazz) {
        return plugins.stream().findFirst().filter(p -> p.getClass().equals(clazz)).orElse(null);
    }

    private boolean checkPermissions(Plugin plugin) {
        if (plugin instanceof MatrixPlugin) {
            Class<?> caller = JavaUtils.getCaller();
            if (caller != Matrix.class) {
                if (caller == null) {
                    return true;
                }
                logger.severe(caller.getSimpleName() + " is not permitted to alter " + plugin.getClass().getSimpleName() + ".");
                return true;
            }
        }
        return false;
    }

    @Override
    public void close() {
        ArrayList<Plugin> temp = plugins;
        for (Plugin plugin : temp) {
            this.disable(plugin);
        }
        temp.clear();
    }

    public ArrayList<Plugin> getPlugins() {
        return plugins;
    }

    public File getRoot() {
        return root;
    }

}
