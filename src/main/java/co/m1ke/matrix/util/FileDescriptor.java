package co.m1ke.matrix.util;

import java.io.File;

public class FileDescriptor {

    private File file;
    private String name;
    private String path;
    private long size;
    private String type;

    public FileDescriptor(File file) {
        this.file = file;
        this.name = file.getName();
        this.path = file.getPath();
        this.size = file.length();
        this.type = file.getName().substring(file.getName().lastIndexOf(".") + 1);
    }

    public File getFile() {
        return file;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public long getSize() {
        return size;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "FileDescriptor{" +
                "file=" + file +
                ", name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", size=" + size +
                ", type='" + type + '\'' +
                '}';
    }

}
