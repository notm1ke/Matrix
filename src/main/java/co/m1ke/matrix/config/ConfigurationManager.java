package co.m1ke.matrix.config;

import co.m1ke.matrix.Matrix;
import co.m1ke.matrix.config.filter.JsonFilter;
import co.m1ke.matrix.logging.Logger;
import co.m1ke.matrix.plugin.Plugin;
import co.m1ke.matrix.util.JsonUtils;
import co.m1ke.matrix.util.Lang;
import co.m1ke.matrix.util.LegacyTimings;
import co.m1ke.matrix.util.TimeUtil;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class ConfigurationManager {

    private HashMap<Plugin, ArrayList<Configuration>> configs = new HashMap<>();
    private Logger logger;

    public ConfigurationManager() {
        this.logger = new Logger("Configuration Service");

        for (Plugin plugin : Matrix.getPluginManager().getPlugins()) {
            LegacyTimings lt = new LegacyTimings();
            List<File> matches = Arrays.asList(Objects.requireNonNull(plugin.getDataFolder().listFiles(new JsonFilter())));
            if (matches.isEmpty()) {
                logger.info(plugin.getName() + " has no configuration files (skipping..)");
                continue;
            }

            for (File f : matches) {
                try {
                    JSONObject obj = JsonUtils.readFromFile(f);
                    if (obj == null) {
                        logger.warning("Error reading Json schema from file [" + f.getName() + "] at [" + f.getPath() + "] (aborting)");
                        continue;
                    }
                    if (obj.isNull("configVersion")) {
                        logger.warning("Could not find a valid configuration version in file [" + f.getName() + "] at [" + f.getPath() + "] (aborting)");
                        continue;
                    }
                    ArrayList<Configuration> c = getConfigurations(plugin);
                    c.add(Configuration.of(plugin, f));

                    configs.put(plugin, c);

                    c.clear();
                } catch (Exception e) {
                    logger.severe("Error parsing configuration file [" + f.getName() + "] at [" + f.getPath() + "] for " + plugin.getName() + "..");
                    logger.severe("Printing stacktrace:");
                    e.printStackTrace();
                    continue;
                }
            }

            ArrayList<Configuration> temp = getConfigurations(plugin);
            logger.info("Loaded " + temp.size() + " configuration file" + TimeUtil.numberEnding(temp.size()) + " in for " + plugin.getName() + " " + Lang.getColorForElaspedTime(lt.stop()) + lt.stop() + "ms" + Lang.RESET + ".");
        }

    }

    public ArrayList<Configuration> getConfigurations(Plugin plugin) {
        return configs.getOrDefault(plugin, new ArrayList<>());
    }

}
