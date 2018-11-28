package co.m1ke.matrix.event.interfaces;

public interface Cancellable {

    void setCancelled(boolean cancelled);
    boolean isCancelled();

}
