package co.m1ke.matrix.logging;

import co.m1ke.matrix.util.Lang;
import co.m1ke.matrix.util.TimeUtil;

public class Logger {

    private String name;

    public Logger() {
        this.name = "Matrix";
    }

    public Logger(String name) {
        this.name = name;
    }

    public void logRaw(String msg) {
        System.out.println(msg);
    }

    public void log(String body) {
        logRaw(Lang.WHITE + TimeUtil.format(System.currentTimeMillis()) + Lang.GREEN + " | " + Lang.RESET + body);
    }

    public void log(String color, String head, String body) {
        logRaw(Lang.WHITE + TimeUtil.format(System.currentTimeMillis()) + Lang.GREEN + " | [" + color + head + "] " + Lang.RESET + body);
    }

    public void log(boolean condition, String color, String head, String body) {
        if (condition)
            logRaw(Lang.WHITE + TimeUtil.format(System.currentTimeMillis()) + Lang.GREEN + " | [" + color + head + "] " + Lang.RESET + body);
    }

    public void info(String body) {
        log(Lang.GREEN + "[Info] " + Lang.WHITE + "[" + this.name + "] " + Lang.RESET + body);
    }

    public void debug(String body) {
        log(Lang.GREEN + "[Debug] " + Lang.WHITE + "[" + this.name + "] " + Lang.RESET + body);
    }

    public void warning(String body) {
        log(Lang.YELLOW + "[Warning] " + Lang.WHITE + "[" + this.name + "] " + Lang.RESET + body);
    }

    public void severe(String body) {
        log(Lang.RED + "[Severe] " + Lang.WHITE + "[" + this.name + "] " + Lang.RESET + body);
    }

    public String getName() {
        return name;
    }

}
