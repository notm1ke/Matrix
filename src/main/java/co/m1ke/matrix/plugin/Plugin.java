package co.m1ke.matrix.plugin;

import co.m1ke.matrix.Matrix;
import co.m1ke.matrix.config.ConfigurationManager;
import co.m1ke.matrix.error.MatrixExceptionImpl;
import co.m1ke.matrix.error.plugin.ConfigNotReadyException;
import co.m1ke.matrix.error.plugin.DatabaseNotReadyException;
import co.m1ke.matrix.event.EventManager;
import co.m1ke.matrix.event.listener.Listener;
import co.m1ke.matrix.logging.Logger;
import co.m1ke.matrix.util.Database;
import co.m1ke.matrix.util.Defaults;

import java.io.File;

public abstract class Plugin {

    private String name;
    private String author;
    private boolean debug;
    private double version;

    private ConfigurationManager configuration;
    private Database database;
    private Logger logger;
    private EventManager eventManager;

    public Plugin() {
    }

    public void init(String name, String author, boolean debug, double version) {
        this.name = name;
        this.author = author;
        this.debug = debug;
        this.version = version;

        this.database = null;
        this.logger = new Logger(this.name);
        this.eventManager = new EventManager(this, this.debug);
        this.configuration = new ConfigurationManager(this);
    }

    public void onLoad() {
    }

    public void onEnable() {
    }

    public void onDisable() {
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public double getVersion() {
        return version;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public boolean isDebug() {
        return debug;
    }

    public void setVersion(double version) {
        this.version = version;
    }

    public ConfigurationManager getConfiguration() {
        if (configuration == null)
            throw new ConfigNotReadyException("Configuration Manager is not ready.", this);
        return configuration;
    }

    public Database getDatabase() {
        if (database == null)
            throw new DatabaseNotReadyException("Database is not setup.", this);
        return database;
    }

    public Database setupDatabase(Database.Preferences preferences) {
        this.database = new Database(preferences);
        return this.database;
    }

    public Logger getLogger() {
        return logger;
    }

    public void resetLogger() {
        this.logger = new Logger(this.name);
    }

    public File getDataFolder() {
        return new File(Defaults.PLUGINS, this.name);
    }

    public EventManager getEventManager() {
        return eventManager;
    }

    public void listen(Listener... listeners) {
        if (listeners.length == 0) {
            throw new MatrixExceptionImpl("Listener vararg cannot be empty.");
        }
        for (Listener l : listeners) {
            this.eventManager.getEventManager().registerListener(l);
        }
    }

    public PluginManager getPluginManager() {
        return Matrix.getPluginManager();
    }

    @Override
    public String toString() {
        return "Plugin{" +
                "name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", version=" + version +
                ", configuration=" + configuration.toJson() +
                ", database=" + database +
                ", logger=" + logger.toJson() +
                ", eventManager=" + eventManager.toJson() +
                '}';
    }

}
