package co.m1ke.matrix.event.events;

import co.m1ke.matrix.event.interfaces.Event;

import java.lang.annotation.Annotation;

public class ShutdownEvent implements Event {

    @Override
    public int priority() {
        return 50;
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return null;
    }

}
