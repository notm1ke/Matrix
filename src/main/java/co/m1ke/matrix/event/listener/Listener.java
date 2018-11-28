package co.m1ke.matrix.event.listener;

import co.m1ke.matrix.event.EventManager;
import co.m1ke.matrix.event.interfaces.EventListener;
import co.m1ke.matrix.logging.Logger;

public abstract class Listener implements EventListener {

    private String name;
    private EventManager manager;
    private Logger logger;

    public Listener(String name, EventManager manager) {
        this.name = name;
        this.manager = manager;
        this.logger = new Logger(this.name);
    }

    public abstract void init();

    public EventManager getManager() {
        return manager;
    }

    public void log(String msg) {
        logger.info(msg);
    }

    public void logRaw(String msg) {
        logger.logRaw(msg);
    }

    public void registerSelf() {
        manager.getEventExecutor().registerListener(this);
    }

    public void unregisterSelf() {
        manager.getEventExecutor().unregisterAll(this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
