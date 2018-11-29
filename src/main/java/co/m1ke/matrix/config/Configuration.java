package co.m1ke.matrix.config;

import co.m1ke.matrix.plugin.Plugin;
import co.m1ke.matrix.util.JsonUtils;
import co.m1ke.matrix.util.container.Key;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Configuration {

    private Plugin plugin;
    private File file;
    private JSONObject body;

    public Configuration(Plugin plugin, File file, JSONObject body) {
        this.plugin = plugin;
        this.file = file;
        this.body = body;
    }

    public static Configuration getDefault(Plugin plugin) {
        return new Configuration(plugin, new File(plugin.getDataFolder(), "config.json"), new JSONObject());
    }

    public static Configuration of(Plugin plugin, File file) {
        return new Configuration(plugin, file, JsonUtils.readFromFile(file));
    }

    public Key getKey(String path) {
        if (body.isNull(path))
            return null;
        return Key.of(body.getString(path));
    }

    public void set(String path, Key key) {
        this.body.put(path, key);
    }

    public void save() throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(this.file);

        writer.write("");
        writer.write(this.body.toString());
        writer.close();
    }

    public Plugin getPlugin() {
        return plugin;
    }

    public File getFile() {
        return file;
    }

    public JSONObject getBody() {
        return body;
    }

}
