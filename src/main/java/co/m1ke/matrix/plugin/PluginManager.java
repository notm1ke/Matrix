package co.m1ke.matrix.plugin;

import co.m1ke.matrix.Matrix;
import co.m1ke.matrix.error.plugin.InvalidMetadataException;
import co.m1ke.matrix.error.plugin.PluginAlreadyInitializedException;
import co.m1ke.matrix.error.plugin.PluginNotInitializedException;
import co.m1ke.matrix.logging.Logger;
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

public class PluginManager {

    private ArrayList<Plugin> plugins;
    private File root;
    private Logger logger;

    public PluginManager(File root, Logger logger) {
        this.plugins = new ArrayList<>();
        this.root = root;
        this.logger = logger;
    }

    public void loadAll() {

        Timings timings = new Timings("Plugin Loader", "Load plugins");
        logger.info("Attempting to load all plugins..");

        File pluginsFolder = new File(root, "plugins");
        if (!pluginsFolder.exists()) {
            try {
                if (!pluginsFolder.mkdir()) {
                    throw new IOException("Error creating plugins directory.");
                }
            } catch (IOException e) {
                logger.severe("Unable to load or create plugins directory. Aborting with exit code -1.");
                e.printStackTrace();
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

                JSONObject meta = null;
                try {
                    meta = new JSONObject(IOUtils.toString(reader));
                } catch (JSONException e) {
                    logger.severe("Could not load [" + f.getName() + "] because their plugin metadata file is not valid.");
                    continue;
                }

                Class load = null;
                try {
                    load = Class.forName(meta.getString("main"), true, child);
                } catch (ClassNotFoundException e) {
                    logger.severe("Could not find main class [" + meta.getString("main") + "] in [" + f.getName() + "].");
                    continue;
                }

                Plugin plugin = (Plugin) load.newInstance();

                try {
                    plugin.init(meta.getString("name"), meta.getString("author"));
                    plugin.resetLogger();
                } catch (JSONException e) {
                    throw new InvalidMetadataException("One or more required elements are missing from the plugin.json file.");
                }

                plugin.onLoad();

                if (!plugin.getDataFolder().exists()) {
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
        timings.complete();

        timings = new Timings("Plugin Loader", "Enable plugins");
        plugins.forEach(Plugin::onEnable);
        timings.complete();

    }

    public void enablePlugin(Plugin plugin) {

        if (plugins.contains(plugin)) {
            throw new PluginAlreadyInitializedException(plugin);
        }

        plugins.add(plugin);

        plugin.onLoad();
        plugin.onEnable();
    }

    public void disablePlugin(Plugin plugin) {

        if (!plugins.contains(plugin)) {
            throw new PluginNotInitializedException(plugin);
        }

        plugins.remove(plugin);
        plugin.onDisable();
    }

    public ArrayList<Plugin> getPlugins() {
        return plugins;
    }

    public File getRoot() {
        return root;
    }

}
