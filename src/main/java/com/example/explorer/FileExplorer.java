package com.example.explorer;

public interface FileExplorer {
    boolean goToParent();
    boolean goToDirectory(String path);
    boolean delete(String path);
    boolean createDirectory(String path);
    boolean copyFile(String source, String target);
    boolean rename(String currentName, String newName);
    boolean exportFile(String source, String target);
    boolean importFile(String source, String target);
    String getOngoingDirectoryJSON();
    OngoingDirectory getOngoingDirectory();
}
