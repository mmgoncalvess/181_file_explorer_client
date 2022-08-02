package com.example.network;

import com.example.network.NetworkClient;
import com.example.network.Request;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NetworkClientTest {
    NetworkClient networkClient;
    String pathSeparator = "\\";

    @BeforeEach
    void setUp() {
        networkClient = new NetworkClient();
    }

    @AfterEach
    void tearDown() {
        networkClient = null;
    }

    @Test
    void connectRequestOngoingDirectory() {
        networkClient.setInstruction(Request.ONGOING_DIRECTORY);
        boolean result = networkClient.connect();
        String json = networkClient.getJson();
        assertTrue(result);
        assertNotNull(json);
    }

    @Test
    void connectRequestParent() {
        String pathOne = System.getProperty("user.dir");
        networkClient.setPathOne(pathOne);
        networkClient.setInstruction(Request.DIRECTORY);
        networkClient.connect();
        networkClient = new NetworkClient();
        networkClient.setInstruction(Request.PARENT);
        boolean result = networkClient.connect();
        String json = networkClient.getJson();
        assertTrue(result);
        assertNotNull(json);
    }

    @Test
    void connectRequestDirectory() {
        String pathOne = System.getProperty("user.dir");
        networkClient.setPathOne(pathOne);
        networkClient.setInstruction(Request.DIRECTORY);
        boolean result = networkClient.connect();
        String json = networkClient.getJson();
        assertTrue(result);
        assertNotNull(json);
    }

    @Test
    void connectRequestDelete() {
        String pathOne = System.getProperty("user.dir");
        networkClient.setPathOne(pathOne);
        networkClient.setInstruction(Request.DIRECTORY);
        boolean result = networkClient.connect();
        assertTrue(result);
        networkClient = new NetworkClient();
        pathOne = pathOne + pathSeparator +"new_directory_test_xyz";
        networkClient.setPathOne(pathOne);
        networkClient.setInstruction(Request.NEW_DIRECTORY);
        result = networkClient.connect();
        String json = networkClient.getJson();
        assertTrue(result);
        assertNotNull(json);
        networkClient = new NetworkClient();
        networkClient.setPathOne(pathOne);
        networkClient.setInstruction(Request.DELETE);
        result = networkClient.connect();
        json = networkClient.getJson();
        assertTrue(result);
        assertNotNull(json);
    }

    @Test
    void connectRequestNewDirectory() {
        String pathOne = System.getProperty("user.dir");
        networkClient.setPathOne(pathOne);
        networkClient.setInstruction(Request.DIRECTORY);
        boolean result = networkClient.connect();
        assertTrue(result);
        networkClient = new NetworkClient();
        pathOne = pathOne + pathSeparator +"new_directory_test_xyz";
        networkClient.setPathOne(pathOne);
        networkClient.setInstruction(Request.NEW_DIRECTORY);
        result = networkClient.connect();
        String json = networkClient.getJson();
        assertTrue(result);
        assertNotNull(json);
        networkClient = new NetworkClient();
        networkClient.setPathOne(pathOne);
        networkClient.setInstruction(Request.DELETE);
        result = networkClient.connect();
        json = networkClient.getJson();
        assertTrue(result);
        assertNotNull(json);
    }

    @Test
    void connectRequestRename() {
        String pathOne = System.getProperty("user.dir");
        networkClient.setPathOne(pathOne);
        networkClient.setInstruction(Request.DIRECTORY);
        boolean result = networkClient.connect();
        assertTrue(result);
        networkClient = new NetworkClient();
        String saveString = pathOne;
        pathOne = saveString + pathSeparator +"new_directory_test_xyz";
        networkClient.setPathOne(pathOne);
        networkClient.setInstruction(Request.NEW_DIRECTORY);
        result = networkClient.connect();
        String json = networkClient.getJson();
        assertTrue(result);
        assertNotNull(json);
        networkClient = new NetworkClient();
        String pathTwo = saveString + pathSeparator +"directory_renamed";
        networkClient.setPathOne(pathOne);
        networkClient.setPathTwo(pathTwo);
        networkClient.setInstruction(Request.RENAME);
        result = networkClient.connect();
        json = networkClient.getJson();
        assertTrue(result);
        assertNotNull(json);
        networkClient = new NetworkClient();
        networkClient.setPathOne(pathTwo);
        networkClient.setInstruction(Request.DELETE);
        result = networkClient.connect();
        json = networkClient.getJson();
        assertTrue(result);
        assertNotNull(json);
    }

    @Test
    void connectRequestCopy() {
        String pathOne = System.getProperty("user.dir");
        networkClient.setPathOne(pathOne);
        networkClient.setInstruction(Request.DIRECTORY);
        boolean result = networkClient.connect();
        assertTrue(result);

        networkClient = new NetworkClient();
        String saveString = pathOne;
        pathOne = saveString + pathSeparator +"pom.xml";
        String pathTwo = saveString + pathSeparator + "pomCopy.xml";
        networkClient.setPathOne(pathOne);
        networkClient.setPathTwo(pathTwo);
        networkClient.setInstruction(Request.COPY);
        result = networkClient.connect();
        String json = networkClient.getJson();
        assertTrue(result);
        assertNotNull(json);

        networkClient = new NetworkClient();
        networkClient.setPathOne(pathTwo);
        networkClient.setInstruction(Request.DELETE);
        result = networkClient.connect();
        json = networkClient.getJson();
        assertTrue(result);
        assertNotNull(json);
    }

    @Test
    void connectRequestExport() {
        String currentDirectory = System.getProperty("user.dir");
        networkClient.setPathOne(currentDirectory);
        networkClient.setInstruction(Request.DIRECTORY);
        boolean result = networkClient.connect();
        assertTrue(result);
        networkClient = new NetworkClient();
        String newDirectory = currentDirectory + pathSeparator +"new_directory_test_mno";
        networkClient.setPathOne(newDirectory);
        networkClient.setInstruction(Request.NEW_DIRECTORY);
        result = networkClient.connect();
        String json = networkClient.getJson();
        assertTrue(result);
        assertNotNull(json);

        networkClient = new NetworkClient();
        String pathOne = currentDirectory + pathSeparator + "pom.xml";
        String pathTwo = newDirectory + pathSeparator + "pomCopied.xml";
        File file = new File(pathOne);
        try {
            int fileSize = (int) Files.size(file.toPath());
            networkClient.setSizeFileToSend(fileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
        networkClient.setPathOne(pathOne);
        networkClient.setPathTwo(pathTwo);
        networkClient.setInstruction(Request.EXPORT);
        result = networkClient.connect();
        json = networkClient.getJson();
        assertTrue(result);
        assertNotNull(json);

        networkClient = new NetworkClient();
        networkClient.setPathOne(pathTwo);
        networkClient.setInstruction(Request.DELETE);
        result = networkClient.connect();
        json = networkClient.getJson();
        assertTrue(result);
        assertNotNull(json);

        networkClient = new NetworkClient();
        networkClient.setPathOne(newDirectory);
        networkClient.setInstruction(Request.DELETE);
        result = networkClient.connect();
        json = networkClient.getJson();
        assertTrue(result);
        assertNotNull(json);
    }

    @Test
    void connectRequestImport() {
        String currentDirectory = System.getProperty("user.dir");
        networkClient.setPathOne(currentDirectory);
        networkClient.setInstruction(Request.DIRECTORY);
        boolean result = networkClient.connect();
        assertTrue(result);

        networkClient = new NetworkClient();
        String newDirectory = currentDirectory + pathSeparator +"new_directory_test_mno";
        networkClient.setPathOne(newDirectory);
        networkClient.setInstruction(Request.NEW_DIRECTORY);
        result = networkClient.connect();
        String json = networkClient.getJson();
        assertTrue(result);
        assertNotNull(json);

        networkClient = new NetworkClient();
        String pathOne = currentDirectory + pathSeparator + "pom.xml";
        String pathTwo = newDirectory + pathSeparator + "pomCopied.xml";
        File file = new File(pathOne);
        try {
            int fileSize = (int) Files.size(file.toPath());
            networkClient.setSizeFileToReceive(fileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
        networkClient.setPathOne(pathOne);
        networkClient.setPathTwo(pathTwo);
        networkClient.setInstruction(Request.IMPORT);
        result = networkClient.connect();
        json = networkClient.getJson();
        assertTrue(result);
        assertNotNull(json);

        networkClient = new NetworkClient();
        networkClient.setPathOne(pathTwo);
        networkClient.setInstruction(Request.DELETE);
        result = networkClient.connect();
        json = networkClient.getJson();
        assertTrue(result);
        assertNotNull(json);

        networkClient = new NetworkClient();
        networkClient.setPathOne(newDirectory);
        networkClient.setInstruction(Request.DELETE);
        result = networkClient.connect();
        json = networkClient.getJson();
        assertTrue(result);
        assertNotNull(json);
    }
}
