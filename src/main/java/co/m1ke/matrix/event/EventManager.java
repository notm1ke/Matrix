package co.m1ke.matrix.event;

import co.m1ke.matrix.event.listener.ListenerAdapter;
import co.m1ke.matrix.plugin.Plugin;
import co.m1ke.matrix.util.JsonSerializable;

import org.json.JSONObject;

public class EventManager implements JsonSerializable {

    private Plugin plugin;
    private EventExecutor eventExecutor;
    private ListenerAdapter listenerAdapter;
    private boolean debug;

    public EventManager(Plugin plugin, boolean debug) {
        this.plugin = plugin;
        this.debug = debug;

        this.plugin.resetLogger();

        this.eventExecutor = new EventExecutor(plugin, debug);
        this.listenerAdapter = new ListenerAdapter();
    }

    public Plugin getPlugin() {
        return plugin;
    }

    public EventExecutor getEventManager() {
        return eventExecutor;
    }

    public ListenerAdapter getListenerManager() {
        return listenerAdapter;
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    @Override
    public JSONObject toJson() {
        return new JSONObject()
                .put("debug", this.debug);
    }

}
