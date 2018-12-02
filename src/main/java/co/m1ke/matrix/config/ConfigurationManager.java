package co.m1ke.matrix.config;

import co.m1ke.matrix.config.filter.JsonFilter;
import co.m1ke.matrix.logging.Logger;
import co.m1ke.matrix.plugin.Plugin;
import co.m1ke.matrix.util.JsonSerializable;
import co.m1ke.matrix.util.JsonUtils;
import co.m1ke.matrix.util.Lang;
import co.m1ke.matrix.util.LegacyTimings;
import co.m1ke.matrix.util.TimeUtil;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class ConfigurationManager implements JsonSerializable {

    private HashMap<String, Configuration> configurations;
    private Plugin plugin;

    public ConfigurationManager(Plugin plugin) {
        this.plugin = plugin;
        this.configurations = new HashMap<>();

        Logger logger = plugin.getLogger();

        LegacyTimings lt = new LegacyTimings();
        List<File> matches = Arrays.asList(Objects.requireNonNull(plugin.getDataFolder().listFiles(new JsonFilter())));
        if (matches.isEmpty()) {
            return;
        }

        for (File f : matches) {
            try {
                JSONObject obj = JsonUtils.getFromFile(f);
                if (obj == null) {
                    logger.warning("Error reading Json schema from file [" + f.getName() + "] at [" + f.getPath() + "] (aborting)");
                    continue;
                }
                if (obj.isNull("configurationVersion")) {
                    logger.warning("Could not find a valid configuration version in file [" + f.getName() + "] at [" + f.getPath() + "] (aborting)");
                    continue;
                }
                configurations.put(f.getName(), Configuration.of(plugin, f));
            } catch (Exception e) {
                logger.severe("Error parsing configuration file [" + f.getName() + "] at [" + f.getPath() + "] for " + plugin.getName() + "..");
                logger.severe("Printing stacktrace:");
                e.printStackTrace();
                continue;
            }
        }

        logger.info("Loaded " + configurations.size() + " configuration file" + TimeUtil.numberEnding(configurations.size()) + " for " + plugin.getName() + " in " + Lang.getColorForElaspedTime(lt.stop()) + lt.stop() + "ms" + Lang.RESET + ".");
    }

    public void create(String fileName, HashMap<String, Object> defaults) {
        File nf = new File(plugin.getDataFolder(), fileName.endsWith(".json") ? fileName : fileName + ".json");
        try {

            if (nf.exists() || exists(nf.getName())) {
                plugin.getLogger().warning("Configuration file " + nf.getName() + " already exists, aborting.");
                return;
            }
            if (!nf.createNewFile()) {
                throw new IOException("Error creating new configuration file.");
            }

            Configuration config = new Configuration(this.plugin, nf, new JSONObject(defaults));
            config.getBody().put("configurationVersion", 1);
            config.save();
            this.configurations.put(fileName, config);

            plugin.getLogger().info("Configuration file " + nf.getName() + " created.");
        } catch (IOException e) {
            plugin.getLogger().warning("Unable to create configuration file for " + plugin.getName() + ".");
        }
    }

    public Configuration get(String name) {

        if (!exists(name)) {
            this.create(name, new HashMap<>());
        }

        return configurations.get(name);
    }

    public Configuration getOrElse(String name, Configuration orElse) {

        if (!exists(name)) {
            return orElse;
        }

        return configurations.get(name);
    }

    public Configuration getOrCreate(String name, HashMap<String, Object> defaults) {

        if (!exists(name)) {
            this.create(name, defaults);
        }

        return configurations.get(name);
    }

    public boolean exists(String name) {
        return configurations.containsKey(name.endsWith(".json") ? name : name + ".json");
    }

    public HashMap<String, Configuration> getConfigurations() {
        return configurations;
    }

    @Override
    public JSONObject toJson() {
        return new JSONObject()
                .put("configurations", new JSONObject(configurations));
    }

}
