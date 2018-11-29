package co.m1ke.matrix.config.filter;

import java.io.File;
import java.io.FilenameFilter;

public class JsonFilter implements FilenameFilter {

    @Override
    public boolean accept(final File dir, final String name) {
        return name.endsWith(".json");
    }

}
