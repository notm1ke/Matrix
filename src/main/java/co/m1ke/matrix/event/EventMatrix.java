package co.m1ke.matrix.event;

import co.m1ke.matrix.closable.Closable;
import co.m1ke.matrix.closable.ClosableOverride;
import co.m1ke.matrix.error.plugin.PluginEventManagerException;
import co.m1ke.matrix.event.interfaces.Event;
import co.m1ke.matrix.plugin.Plugin;
import co.m1ke.matrix.plugin.PluginManager;

import java.util.HashMap;

public class EventMatrix implements Closable, ClosableOverride {

    private HashMap<Plugin, EventManager> eventManagers = new HashMap<>();

    private PluginManager pluginManager;

    public EventMatrix(PluginManager pluginManager) {
        this.pluginManager = pluginManager;
    }

    public void registerPlugin(Plugin plugin) {
        this.eventManagers.put(plugin, plugin.getEventManager());
    }

    public void emit(Event event) {
        this.eventManagers.forEach((p, e) -> e.getEventManager().emit(event));
    }

    public void unregisterPlugin(Plugin plugin) {
        if (!eventManagers.containsKey(plugin)) {
            return;
        }

        unregisterAll(plugin);
        eventManagers.remove(plugin);
    }

    public void unregisterAll() {
        for (Plugin pl : pluginManager.getPlugins()) {
            this.unregisterAll(pl);
        }
    }

    public void unregisterAll(Plugin plugin) {

        if (!eventManagers.containsKey(plugin)) {
            throw new PluginEventManagerException(plugin);
        }

        EventManager manager = eventManagers.get(plugin);
        if (manager == null) {
            throw new PluginEventManagerException(plugin);
        }

        manager.getEventManager().unregisterAll();
    }

    @Override
    public void close() {
        this.eventManagers.clear();

        this.eventManagers = null;
        this.pluginManager = null;
    }

    public HashMap<Plugin, EventManager> getEventManagers() {
        return eventManagers;
    }

}
