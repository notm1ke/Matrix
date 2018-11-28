package co.m1ke.matrix.util;

import co.m1ke.matrix.callback.Callback;
import co.m1ke.matrix.logging.Logger;

public class Timings {

    private long start;
    private String prefix;
    private String task;

    private Logger logger;

    public Timings(String prefix, String task) {
        this.start = System.currentTimeMillis();
        this.prefix = (prefix == null) ? "Timings" : prefix;
        this.task = task;
        this.logger = new Logger(prefix);
    }

    public void complete() {
        long elapsed = (System.currentTimeMillis() - start);
        logger.log(Lang.GREEN, prefix, task + " took " + Lang.getColorForElaspedTime(elapsed) + elapsed + "ms" + Lang.RESET + ".");
    }

    public void complete(Callback callback) {
        long elapsed = (System.currentTimeMillis() - start);
        logger.log(Lang.GREEN, prefix, task + " took " + Lang.getColorForElaspedTime(elapsed) + elapsed + "ms" + Lang.RESET + ".");
        callback.complete();
    }

}
