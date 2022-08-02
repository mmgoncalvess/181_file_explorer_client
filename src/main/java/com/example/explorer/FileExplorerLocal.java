package com.example.explorer;

import com.example.network.NetworkClient;
import com.example.network.Request;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;

public class FileExplorerLocal implements FileExplorer {
    private File currentDirectory;

    @Override
    public boolean goToParent() {
        if (currentDirectory == null) return false;
        currentDirectory = currentDirectory.getParentFile();
        return true;
    }

    @Override
    public boolean goToDirectory(String path) {
        File file = new File(path);
        boolean directoryExist = file.exists() && file.isDirectory();
        if (directoryExist) currentDirectory = file;
        return directoryExist;
    }

    @Override
    public boolean delete(String path) {
        File file = new File(path);
        if (file.exists()) return file.delete();
        return false;
    }

    @Override
    public boolean createDirectory(String absolutePath) {
        File file = new File(absolutePath);
        return file.mkdir();
    }

    @Override
    public boolean copyFile(String sourcePath, String targetPath) {
        File source = new File(sourcePath);
        File target = new File(targetPath);
        Path path = null;
        if (source.exists() && !target.exists()) {
            try {
                path = Files.copy(source.toPath(), target.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return target.toPath().equals(path);
    }

    @Override
    public boolean rename(String currentName, String newName) {
        File currentNameFile = new File(currentName);
        File newNameFile= new File(newName);
        if (currentNameFile.exists() && !newNameFile.exists() && currentNameFile.getParent().equals(newNameFile.getParent())) {
            return currentNameFile.renameTo(newNameFile);
        }
        return false;
    }

    @Override
    public boolean exportFile(String source, String target) {
        // This method is not implemented in the local file explorer
        return false;
    }

    @Override
    public boolean importFile(String source, String target) {
        NetworkClient networkClient = new NetworkClient();
        networkClient.setPathOne(source);
        networkClient.setPathTwo(target);
        networkClient.setInstruction(Request.IMPORT);
        return networkClient.connect();
    }

    @Override
    public OngoingDirectory getOngoingDirectory() {
        // This local implementation reads from filesystem. The remote implementation must have an
        // OngoingDirectory member that is updated with each request/response from the client/server
        OngoingDirectory ongoingDirectory = new OngoingDirectory();
        if (currentDirectory == null) {
            ongoingDirectory.setCurrentDirectory("");
            ArrayList<String> arrayList = new ArrayList<>();
            File[] roots = File.listRoots();
            for (File root : roots) {
                arrayList.add(root.getAbsolutePath());
            }
            ongoingDirectory.setDirectories(arrayList);
            ongoingDirectory.setFiles(new HashMap<>());
        } else {
            ongoingDirectory.setCurrentDirectory(currentDirectory.getAbsolutePath());
            ArrayList<String> directoriesList = new ArrayList<>();
            HashMap<String, Integer> filesCollection = new HashMap<>();
            File[] files = currentDirectory.listFiles();
            assert files != null;
            for (File file : files) {
                if (file.isDirectory()) directoriesList.add(file.getName());
                if (file.isFile()) {
                    try {
                        filesCollection.put(file.getName(), (int) Files.size(file.toPath()));
                    } catch (IOException e) {
                        e.printStackTrace();
                        filesCollection.put(file.getName(), 0);
                    }
                }
            }
            ongoingDirectory.setDirectories(directoriesList);
            ongoingDirectory.setFiles(filesCollection);
        }
        return ongoingDirectory;
    }

    @Override
    public String getOngoingDirectoryJSON() {

        OngoingDirectory ongoingDirectory = getOngoingDirectory();
        String json = "";
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            json = objectMapper.writeValueAsString(ongoingDirectory);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }
}
