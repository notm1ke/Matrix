package co.m1ke.matrix.event;

import co.m1ke.matrix.event.listener.Listener;
import co.m1ke.matrix.event.listener.ListenerAdapter;

public class EventManager {

    private EventExecutor eventExecutor;
    private ListenerAdapter listenerAdapter;
    private boolean debug;

    public EventManager(boolean debug, Listener... listeners) {
        this.debug = debug;
        this.eventExecutor = new EventExecutor(debug);
        this.listenerAdapter = new ListenerAdapter();

        for (Listener listener : listeners) {
            this.listenerAdapter.register(listener);
        }

        this.listenerAdapter.loadAll();
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
