package co.m1ke.matrix.event.listener;

import java.util.ArrayList;

public class ListenerAdapter {

    private ArrayList<Listener> listeners;

    public ListenerAdapter() {
        this.listeners = new ArrayList<>();
    }

    public void loadAll() {
        this.listeners.forEach(Listener::init);
    }

    public void register(Listener listener) {
        listeners.add(listener);
    }

}
