package co.m1ke.matrix.event.events;

import co.m1ke.matrix.event.interfaces.Event;

import java.lang.annotation.Annotation;

public class ReadyEvent implements Event {

    @Override
    public int priority() {
        return 50;
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return null;
    }

    private long time;

    public ReadyEvent(long time) {
        this.time = time;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

}
