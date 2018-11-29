package co.m1ke.matrix.config;

import co.m1ke.matrix.Matrix;
import co.m1ke.matrix.plugin.Plugin;

import java.util.HashMap;

public class ConfigurationMatrix {

    private HashMap<String, Configuration> configurations;
    private Plugin plugin;

    public ConfigurationMatrix(Plugin plugin) {
        this.plugin = plugin;
        this.configurations = new HashMap<>();

        Matrix.getConfigurationManager().getConfigurations(this.plugin).forEach(c -> configurations.put(c.getFile().getName(), c));
    }

}
