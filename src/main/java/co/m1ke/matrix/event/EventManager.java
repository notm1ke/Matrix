package co.m1ke.matrix.event;

import co.m1ke.matrix.event.listener.Listener;
import co.m1ke.matrix.event.listener.ListenerAdapter;
import co.m1ke.matrix.plugin.Plugin;

public class EventManager {

    private Plugin plugin;
    private EventExecutor eventExecutor;
    private ListenerAdapter listenerAdapter;
    private boolean debug;

    public EventManager(Plugin plugin, boolean debug, Listener... listeners) {
        this.plugin = plugin;
        this.debug = debug;

        this.plugin.resetLogger();

        this.eventExecutor = new EventExecutor(plugin, debug);
        this.listenerAdapter = new ListenerAdapter();

        for (Listener listener : listeners) {
            this.listenerAdapter.register(listener);
        }

        this.listenerAdapter.loadAll();
    }

    public Plugin getPlugin() {
        return plugin;
    }

    public EventExecutor getEventExecutor() {
        return eventExecutor;
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

}
