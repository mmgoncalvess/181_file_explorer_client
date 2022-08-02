package com.example.explorer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class FileExplorerLocalTest {
    FileExplorerLocal fileExplorerLocal;
    private final String pathSeparator = File.separator;

    @BeforeEach
    void setUp() {
        fileExplorerLocal = new FileExplorerLocal();
    }

    @AfterEach
    void tearDown() {
        fileExplorerLocal = null;
    }

    @Test
    void goToParent() {
        String currentDirectory = System.getProperty("user.dir");
        fileExplorerLocal.goToDirectory(currentDirectory);
        File file = new File(currentDirectory);
        String expectedValue = file.getParent();
        fileExplorerLocal.goToParent();
        String currentValue = fileExplorerLocal.getOngoingDirectory().getCurrentDirectory();
        assertEquals(expectedValue, currentValue);
    }

    @Test
    void goToDirectory() {
        String currentDirectory = System.getProperty("user.dir");
        fileExplorerLocal.goToDirectory(currentDirectory);
        File file = new File(currentDirectory);
        String expectedValue = file.getParent();
        fileExplorerLocal.goToDirectory(expectedValue);
        String currentValue = fileExplorerLocal.getOngoingDirectory().getCurrentDirectory();
        assertEquals(expectedValue, currentValue);
    }

    @Test
    void delete() {
        String directoryName = "abc123";
        boolean result = fileExplorerLocal.createDirectory(directoryName);
        assertTrue(result);
        result = fileExplorerLocal.goToDirectory(directoryName);
        assertTrue(result);
        result = fileExplorerLocal.goToParent();
        assertTrue(result);
        result = fileExplorerLocal.delete(directoryName);
        assertTrue(result);
    }

    @Test
    void createDirectory() {
        String directoryName = "abc123";
        boolean result = fileExplorerLocal.createDirectory(directoryName);
        assertTrue(result);
        result = fileExplorerLocal.goToDirectory(directoryName);
        assertTrue(result);
        result = fileExplorerLocal.goToParent();
        assertTrue(result);
        result = fileExplorerLocal.delete(directoryName);
        assertTrue(result);
    }

    @Test
    void copyFile() {
        String currentDirectory = System.getProperty("user.dir");
        fileExplorerLocal.goToDirectory(currentDirectory);
        OngoingDirectory ongoingDirectory = fileExplorerLocal.getOngoingDirectory();
        HashMap<String, Integer> hashMap = ongoingDirectory.getFiles();
        Set<String> keySet = hashMap.keySet();
        ArrayList<String> files = new ArrayList<>(keySet);
        assertTrue(files.size() > 0);
        File fileOne = new File(ongoingDirectory.getCurrentDirectory() + pathSeparator + files.get(0));
        assertTrue(fileOne.exists());
        String newDirectory = "ABC123";
        fileExplorerLocal.createDirectory(newDirectory);
        File fileTwo = new File(ongoingDirectory.getCurrentDirectory()+ pathSeparator + newDirectory + pathSeparator + files.get(0));
        assertFalse(fileTwo.exists());
        boolean result = fileExplorerLocal.copyFile(fileOne.getPath(), fileTwo.getPath());
        assertTrue(result);
        assertTrue(fileTwo.exists());
        fileExplorerLocal.delete(fileTwo.getPath());
        result = fileExplorerLocal.delete(newDirectory);
        assertFalse(fileTwo.exists());
        assertTrue(result);
    }

    @Test
    void rename() {
        OngoingDirectory ongoingDirectory = fileExplorerLocal.getOngoingDirectory();
        String directoryPath = ongoingDirectory.getCurrentDirectory() + pathSeparator + "newDirectoryTest";
        File file = new File(directoryPath);
        assertFalse(file.exists());
        boolean result = fileExplorerLocal.createDirectory(directoryPath);
        assertTrue(result);
        result = fileExplorerLocal.rename(directoryPath, directoryPath + "Renamed");
        assertTrue(result);
        assertFalse(file.exists());
        File fileTwo = new File(directoryPath + "Renamed");
        assertTrue(fileTwo.exists());
        result = fileExplorerLocal.delete(fileTwo.getPath());
        assertTrue(result);
        assertFalse(fileTwo.exists());
    }

    @Test
    void exportFile() {
        // Method not implemented
    }

    @Test
    void importFile() {
        // Method not implemented
    }

    @Test
    void getOngoingDirectory() {
        String currentDirectory = System.getProperty("user.dir");
        fileExplorerLocal.goToDirectory(currentDirectory);
        OngoingDirectory ongoingDirectory = fileExplorerLocal.getOngoingDirectory();
        assertEquals(currentDirectory, ongoingDirectory.getCurrentDirectory());
    }

    @Test
    void getOngoingDirectoryJSON() {
        String currentDirectory = System.getProperty("user.dir");
        fileExplorerLocal.goToDirectory(currentDirectory);
        OngoingDirectory ongoingDirectory = fileExplorerLocal.getOngoingDirectory();
        String json = fileExplorerLocal.getOngoingDirectoryJSON();
        ObjectMapper mapper = new ObjectMapper();
        try {
            OngoingDirectory ongoingDirectoryTwo = mapper.readValue(json, OngoingDirectory.class);
            assertEquals(ongoingDirectory.getCurrentDirectory(), ongoingDirectoryTwo.getCurrentDirectory());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
