package com.example.explorer;

public final class CopyBoard {
    private static CopyBoard copyBoard;
    private String parent;
    private String fileName;
    private String hostname;
    private String separator;
    private boolean filled;
    private boolean directory;

    private CopyBoard() {}

    public static CopyBoard getInstance() {
        if (copyBoard == null) {
            copyBoard = new CopyBoard();
        }
        return copyBoard;
    }

    public void fillCopyBoard(String parent, String fileName, String hostname, String separator, boolean isDirectory) {
        this.parent = parent;
        this.fileName = fileName;
        this.hostname = hostname;
        this.separator = separator;
        this.filled = true;
        this.directory = isDirectory;
    }

    public void clean() {
        this.parent = null;
        this.fileName = null;
        this.hostname = null;
        filled = false;
    }

    public String getParent() {
        return parent;
    }

    public String getFileName() {
        return fileName;
    }

    public String getHostname() {
        return hostname;
    }

    public String getSeparator() {
        return separator;
    }

    public boolean isFilled() {
        return filled;
    }

    public boolean isDirectory() {
        return directory;
    }

    @Override
    public String toString() {
        return String.format("Parent: %10s  Filename: %10s  Host name: %10s  Is filled: %5s  Is directory: %5s",
                parent, fileName, hostname, filled, directory);
    }
}
