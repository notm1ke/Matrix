package co.m1ke.matrix.config;

import co.m1ke.matrix.Matrix;
import co.m1ke.matrix.closable.Closable;
import co.m1ke.matrix.config.filter.JsonFilter;
import co.m1ke.matrix.logging.Logger;
import co.m1ke.matrix.plugin.Plugin;
import co.m1ke.matrix.util.JsonUtils;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class ConfigurationMatrix implements Closable {

    private HashMap<Plugin, ArrayList<Configuration>> configs = new HashMap<>();
    private Logger logger;

    public ConfigurationMatrix() {
        this.logger = new Logger("Prefs");

        for (Plugin plugin : Matrix.getPluginManager().getPlugins()) {
            List<File> matches = Arrays.asList(Objects.requireNonNull(plugin.getDataFolder().listFiles(new JsonFilter())));
            if (matches.isEmpty()) {
                continue;
            }

            for (File f : matches) {
                try {
                    JSONObject obj = JsonUtils.getFromFile(f);
                    if (obj == null) {
                        continue;
                    }
                    if (obj.isNull("configurationVersion")) {
                        continue;
                    }
                    ArrayList<Configuration> c = getConfigurations(plugin);
                    c.add(Configuration.of(plugin, f));

                    configs.put(plugin, c);

                    c.clear();
                } catch (Exception e) {
                    e.printStackTrace();
                    continue;
                }
            }
        }

    }

    public ArrayList<Configuration> getConfigurations(Plugin plugin) {
        return configs.getOrDefault(plugin, new ArrayList<>());
    }

    @Override
    public void close() {
        this.configs.clear();
    }

}
