package co.m1ke.matrix.prefs.models;

public enum CompatibilityMode {

    STANDALONE, SPIGOT;

    public static CompatibilityMode match(String s) {
        for (CompatibilityMode cm : values()) {
            if (cm.name().equalsIgnoreCase(s)) {
                return cm;
            }
        }
        return null;
    }

}
