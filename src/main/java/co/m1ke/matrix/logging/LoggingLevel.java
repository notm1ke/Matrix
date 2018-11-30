package co.m1ke.matrix.logging;

import co.m1ke.matrix.util.Lang;

public enum LoggingLevel {

    INFO(Lang.GREEN), DEBUG(Lang.CYAN), WARNING(Lang.YELLOW), SEVERE(Lang.RED);

    private String color;

    LoggingLevel(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }

}
