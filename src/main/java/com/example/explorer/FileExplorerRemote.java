package com.example.explorer;

import com.example.network.NetworkClient;
import com.example.network.Request;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FileExplorerRemote implements FileExplorer {
    private String json;

    @Override
    public boolean goToParent() {
        NetworkClient networkClient = new NetworkClient();
        networkClient.setInstruction(Request.PARENT);
        boolean result = networkClient.connect();
        json = networkClient.getJson();
        return result;
    }

    @Override
    public boolean goToDirectory(String path) {
        NetworkClient networkClient = new NetworkClient();
        networkClient.setPathOne(path);
        networkClient.setInstruction(Request.DIRECTORY);
        boolean result = networkClient.connect();
        json = networkClient.getJson();
        return result;
    }

    @Override
    public boolean delete(String path) {
        NetworkClient networkClient = new NetworkClient();
        networkClient.setPathOne(path);
        networkClient.setInstruction(Request.DELETE);
        boolean result = networkClient.connect();
        json = networkClient.getJson();
        return result;
    }

    @Override
    public boolean createDirectory(String path) {
        NetworkClient networkClient = new NetworkClient();
        networkClient.setPathOne(path);
        networkClient.setInstruction(Request.NEW_DIRECTORY);
        boolean result = networkClient.connect();
        json = networkClient.getJson();
        return result;
    }

    @Override
    public boolean copyFile(String source, String target) {
        NetworkClient networkClient = new NetworkClient();
        networkClient.setPathOne(source);
        networkClient.setPathTwo(target);
        networkClient.setInstruction(Request.COPY);
        boolean result = networkClient.connect();
        json = networkClient.getJson();
        return result;
    }

    @Override
    public boolean rename(String currentName, String newName) {
        NetworkClient networkClient = new NetworkClient();
        networkClient.setPathOne(currentName);
        networkClient.setPathTwo(newName);
        networkClient.setInstruction(Request.RENAME);
        boolean result = networkClient.connect();
        json = networkClient.getJson();
        return result;
    }

    @Override
    public boolean exportFile(String source, String target) {
        NetworkClient networkClient = new NetworkClient();
        networkClient.setPathOne(source);
        networkClient.setPathTwo(target);
        networkClient.setInstruction(Request.EXPORT);
        File file = new File(source);
        try {
            int size = (int) Files.size(file.toPath());
            networkClient.setSizeFileToSend(size);
        } catch (IOException e) {
            e.printStackTrace();
        }
        boolean result = networkClient.connect();
        json = networkClient.getJson();
        return result;
    }

    @Override
    public boolean importFile(String source, String target) {
        NetworkClient networkClient = new NetworkClient();
        networkClient.setPathOne(source);
        networkClient.setPathTwo(target);
        networkClient.setInstruction(Request.IMPORT);
        boolean result = networkClient.connect();
        json = networkClient.getJson();
        return result;
    }

    @Override
    public String getOngoingDirectoryJSON() {
        return json;
    }

    @Override
    public OngoingDirectory getOngoingDirectory() {
        if (json == null) {
            NetworkClient networkClient = new NetworkClient();
            networkClient.setInstruction(Request.ONGOING_DIRECTORY);
            boolean result = networkClient.connect();
            if (result) json = networkClient.getJson();
        }

        OngoingDirectory ongoingDirectory = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            ongoingDirectory = mapper.readValue(json, OngoingDirectory.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ongoingDirectory;
    }
}
