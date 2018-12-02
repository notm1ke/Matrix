package co.m1ke.matrix.util;

import java.io.File;

public class Defaults {

    public static final double VERSION = 0.1;

    public static final String DEFAULT_CLOUD_ENDPOINT = "https://cloud.argon.club/v1";
    public static final String ARGON_SERVICE_PATTERN = "argon.club";

    public static final File ROOT = JavaUtils.getJarLocation();
    public static final File PLUGINS = new File(ROOT, "plugins");

}
