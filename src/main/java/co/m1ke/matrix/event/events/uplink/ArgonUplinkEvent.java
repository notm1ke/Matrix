package co.m1ke.matrix.event.events.uplink;

import co.m1ke.matrix.event.interfaces.Event;

import java.lang.annotation.Annotation;

public class ArgonUplinkEvent implements Event {

    @Override
    public int priority() {
        return 50;
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return null;
    }

    private long time;

    // TODO: Add UplinkResponse object to this

    public ArgonUplinkEvent() {
        this.time = System.currentTimeMillis();
    }

    public long getTime() {
        return time;
    }

}
