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

    public void raw(String msg) {
        System.out.println(msg);
    }

    public void log(String body) {
        raw(Lang.WHITE + TimeUtil.format(System.currentTimeMillis()) + Lang.GREEN + " | " + Lang.RESET + body);
    }

    public void log(LoggingLevel level, String body) {
        raw(Lang.WHITE + TimeUtil.format(System.currentTimeMillis()) + " " + level.getColor() + level.name() + Lang.GREEN + " | " + Lang.RESET + body);
    }

    public void log(String color, String head, String body) {
        raw(Lang.WHITE + TimeUtil.format(System.currentTimeMillis()) + Lang.GREEN + " | [" + color + head + "] " + Lang.RESET + body);
    }

    public void log(boolean condition, String color, String head, String body) {
        if (condition)
            raw(Lang.WHITE + TimeUtil.format(System.currentTimeMillis()) + Lang.GREEN + " | [" + color + head + "] " + Lang.RESET + body);
    }

    public void info(String body) {
        log(LoggingLevel.INFO, Lang.WHITE + "[" + this.name + "] " + Lang.RESET + body);
    }

    public void debug(String body) {
        log(LoggingLevel.DEBUG, Lang.WHITE + "[" + this.name + "] " + Lang.RESET + body);
    }

    public void warning(String body) {
        log(LoggingLevel.WARNING, Lang.WHITE + "[" + this.name + "] " + Lang.RESET + body);
    }

    public void severe(String body) {
        log(LoggingLevel.SEVERE, Lang.WHITE + "[" + this.name + "] " + Lang.RESET + body);
    }

    public String getName() {
        return name;
    }

}
