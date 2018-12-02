package co.m1ke.matrix.util;

import co.m1ke.matrix.callback.Callback;
import co.m1ke.matrix.logging.Logger;

public class Timings {

    private long start;
    private String task;

    private Logger logger;

    public Timings(String prefix, String task) {
        this.start = System.currentTimeMillis();
        this.task = task;
        this.logger = new Logger(prefix);
    }

    public void complete() {
        long elapsed = (System.currentTimeMillis() - start);
        logger.info(task + " took " + Lang.getColorForElaspedTime(elapsed) + elapsed + "ms" + Lang.RESET + ".");
    }

    public void complete(String custom) {
        long elapsed = (System.currentTimeMillis() - start);
        logger.info(parse(custom, elapsed));
    }

    public void complete(Callback callback) {
        long elapsed = (System.currentTimeMillis() - start);
        logger.info(task + " took " + Lang.getColorForElaspedTime(elapsed) + elapsed + "ms" + Lang.RESET + ".");
        callback.complete();
    }

    public void complete(Callback callback, String custom) {
        long elapsed = (System.currentTimeMillis() - start);
        logger.info(parse(custom, elapsed));
        callback.complete();
    }

    private String parse(String input, long elapsed) {
        return input.replaceAll("%t", Long.toString(elapsed))
                .replaceAll("%c", Lang.getColorForElaspedTime(elapsed));
    }

}
