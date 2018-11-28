package co.m1ke.matrix.plugin;

import co.m1ke.matrix.error.plugin.DatabaseNotReadyException;
import co.m1ke.matrix.event.EventManager;
import co.m1ke.matrix.logging.Logger;
import co.m1ke.matrix.util.Database;

public abstract class Plugin {

    private String name;
    private String author;

    private Database database;
    private Logger logger;
    private EventManager eventManager;
    private PluginManager pluginManager;

    public Plugin() {
        this.name = "Unspecified";
        this.author = "Unspecified";

        this.database = null;
        this.logger = new Logger(this.name);
        this.eventManager = new EventManager(false);
        this.pluginManager = new PluginManager(this.logger);
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

    public void setName(String name) {
        this.name = name;
    }

    public void setAuthor(String author) {
        this.author = author;
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

    public EventManager getEventManager() {
        return eventManager;
    }

    public PluginManager getPluginManager() {
        return pluginManager;
    }

}
