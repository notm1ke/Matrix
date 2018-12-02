package co.m1ke.matrix.logging;

import co.m1ke.matrix.util.Lang;

public enum LoggingLevel {

    INFO("INFO", Lang.GREEN), DEBUG("DEBUG", Lang.CYAN), WARNING("WARN", Lang.YELLOW), SEVERE("SEVERE", Lang.RED);

    private String name;
    private String color;

    LoggingLevel(String name, String color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

}
