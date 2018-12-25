package co.m1ke.matrix.logging;

import co.m1ke.matrix.util.Lang;

public enum LoggingLevel {

    INFO("INFO", Lang.GREEN, 1), DEBUG("DEV", Lang.CYAN, 2), WARNING("WARN", Lang.YELLOW, 1), SEVERE("ERR", Lang.RED, 2);

    private String name;
    private String color;
    private int spaces;

    LoggingLevel(String name, String color, int spaces) {
        this.name = name;
        this.color = color;
        this.spaces = spaces;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public int getSpaces() {
        return spaces;
    }

}
