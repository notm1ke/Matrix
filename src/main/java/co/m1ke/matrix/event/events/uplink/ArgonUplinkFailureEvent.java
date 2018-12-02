package co.m1ke.matrix.event.events.uplink;

import co.m1ke.matrix.event.interfaces.Event;

import java.lang.annotation.Annotation;

public class ArgonUplinkFailureEvent implements Event {

    @Override
    public int priority() {
        return 50;
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return null;
    }

    private long time;
    private Throwable exception;

    public ArgonUplinkFailureEvent(Throwable exception) {
        this.time = System.currentTimeMillis();
        this.exception = exception;
    }

    public long getTime() {
        return time;
    }

    public Throwable getException() {
        return exception;
    }

}
