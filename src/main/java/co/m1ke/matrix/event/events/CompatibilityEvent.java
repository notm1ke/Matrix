package co.m1ke.matrix.event.events;

import co.m1ke.matrix.event.interfaces.Event;
import co.m1ke.matrix.prefs.models.CompatibilityMode;

import java.lang.annotation.Annotation;

public class CompatibilityEvent implements Event {

    @Override
    public int priority() {
        return 50;
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return null;
    }

    private CompatibilityMode compatibilityMode;

    public CompatibilityEvent(CompatibilityMode compatibilityMode) {
        this.compatibilityMode = compatibilityMode;
    }

    public CompatibilityMode getCompatibilityMode() {
        return compatibilityMode;
    }

}
