package com.example.explorer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class FileExplorerRemoteTest {
    FileExplorerRemote fileExplorerRemote;
    String pathSeparator = File.separator;

    @BeforeEach
    void setUp() {
        fileExplorerRemote = new FileExplorerRemote();
    }

    @AfterEach
    void tearDown() {
        fileExplorerRemote = null;
    }

    @Test
    void goToParent() {
        boolean result = fileExplorerRemote.goToDirectory("C:\\");
        assertTrue(result);
        result = fileExplorerRemote.goToParent();
        String json = fileExplorerRemote.getOngoingDirectoryJSON();
        assertFalse(result);
        assertNull(json);
    }

    @Test
    void goToDirectory() {
        boolean result = fileExplorerRemote.goToDirectory("C:\\");
        String json = fileExplorerRemote.getOngoingDirectoryJSON();
        assertTrue(result);
        assertNotNull(json);
    }

    @Test
    void delete() {
        boolean result = fileExplorerRemote.goToDirectory("C:\\");
        assertTrue(result);
        result = fileExplorerRemote.createDirectory("C:\\abcxyz");
        String json = fileExplorerRemote.getOngoingDirectoryJSON();
        assertTrue(result);
        assertNotNull(json);
        result = fileExplorerRemote.delete("C:\\abcxyz");
        json = fileExplorerRemote.getOngoingDirectoryJSON();
        assertTrue(result);
        assertNotNull(json);
    }

    @Test
    void createDirectory() {
        boolean result = fileExplorerRemote.goToDirectory("C:\\");
        assertTrue(result);
        result = fileExplorerRemote.createDirectory("C:\\abcxyz");
        String json = fileExplorerRemote.getOngoingDirectoryJSON();
        assertTrue(result);
        assertNotNull(json);
        result = fileExplorerRemote.delete("C:\\abcxyz");
        json = fileExplorerRemote.getOngoingDirectoryJSON();
        assertTrue(result);
        assertNotNull(json);
    }

    @Test
    void copyFile() {

        boolean result = fileExplorerRemote.goToDirectory("C:\\");
        String json = fileExplorerRemote.getOngoingDirectoryJSON();
        assertTrue(result);
        assertNotNull(json);

        result = fileExplorerRemote.createDirectory("C:\\TestExplorer");
        json = fileExplorerRemote.getOngoingDirectoryJSON();
        assertTrue(result);
        assertNotNull(json);

        String pathOne = System.getProperty("user.dir") + pathSeparator + "pom.xml";
        String pathTwo = "C:\\TestExplorer\\pom.xml";
        result = fileExplorerRemote.exportFile(pathOne, pathTwo);
        json = fileExplorerRemote.getOngoingDirectoryJSON();
        assertTrue(result);
        assertNotNull(json);

        pathOne = "C:\\TestExplorer\\pom.xml";
        pathTwo = "C:\\TestExplorer\\abc.xml";
        result = fileExplorerRemote.copyFile(pathOne, pathTwo);
        json = fileExplorerRemote.getOngoingDirectoryJSON();
        File file = new File("C:\\TestExplorer\\abc.xml");
        assertTrue(result);
        assertNotNull(json);
        assertTrue(file.exists());

        result = fileExplorerRemote.delete(pathOne);
        json = fileExplorerRemote.getOngoingDirectoryJSON();
        assertTrue(result);
        assertNotNull(json);

        result = file.delete();
        assertTrue(result);

        file = new File("C:\\TestExplorer");
        result = file.delete();
        assertTrue(result);

    }

    @Test
    void rename() {
        boolean result = fileExplorerRemote.goToDirectory("C:\\");
        assertTrue(result);
        result = fileExplorerRemote.createDirectory("C:\\abcxyz");
        String json = fileExplorerRemote.getOngoingDirectoryJSON();
        assertTrue(result);
        assertNotNull(json);
        result = fileExplorerRemote.rename("C:\\abcxyz", "C:\\xyzabc");
        json = fileExplorerRemote.getOngoingDirectoryJSON();
        assertTrue(result);
        assertNotNull(json);
        result = fileExplorerRemote.delete("C:\\xyzabc");
        json = fileExplorerRemote.getOngoingDirectoryJSON();
        assertTrue(result);
        assertNotNull(json);
    }

    @Test
    void exportFile() {
        boolean result = fileExplorerRemote.goToDirectory("C:\\");
        String json = fileExplorerRemote.getOngoingDirectoryJSON();
        assertTrue(result);
        assertNotNull(json);

        result = fileExplorerRemote.createDirectory("C:\\TestExplorer");
        json = fileExplorerRemote.getOngoingDirectoryJSON();
        assertTrue(result);
        assertNotNull(json);

        String pathOne = System.getProperty("user.dir") + pathSeparator + "pom.xml";
        String pathTwo = "C:\\TestExplorer\\pom.xml";
        result = fileExplorerRemote.exportFile(pathOne, pathTwo);
        json = fileExplorerRemote.getOngoingDirectoryJSON();
        assertTrue(result);
        assertNotNull(json);

        pathOne = "C:\\TestExplorer\\pom.xml";
        pathTwo = "C:\\TestExplorer\\abc.xml";
        result = fileExplorerRemote.importFile(pathOne, pathTwo);
        json = fileExplorerRemote.getOngoingDirectoryJSON();
        File file = new File("C:\\TestExplorer\\abc.xml");
        assertTrue(result);
        assertNotNull(json);
        assertTrue(file.exists());

        result = fileExplorerRemote.delete(pathOne);
        json = fileExplorerRemote.getOngoingDirectoryJSON();
        assertTrue(result);
        assertNotNull(json);

        result = file.delete();
        assertTrue(result);

        file = new File("C:\\TestExplorer");
        result = file.delete();
        assertTrue(result);
    }

    @Test
    void importFile() {
        boolean result = fileExplorerRemote.goToDirectory("C:\\");
        String json = fileExplorerRemote.getOngoingDirectoryJSON();
        assertTrue(result);
        assertNotNull(json);

        result = fileExplorerRemote.createDirectory("C:\\TestExplorer");
        json = fileExplorerRemote.getOngoingDirectoryJSON();
        assertTrue(result);
        assertNotNull(json);

        String pathOne = System.getProperty("user.dir") + pathSeparator + "pom.xml";
        String pathTwo = "C:\\TestExplorer\\pom.xml";
        result = fileExplorerRemote.exportFile(pathOne, pathTwo);
        json = fileExplorerRemote.getOngoingDirectoryJSON();
        assertTrue(result);
        assertNotNull(json);

        pathOne = "C:\\TestExplorer\\pom.xml";
        pathTwo = "C:\\TestExplorer\\abc.xml";
        result = fileExplorerRemote.importFile(pathOne, pathTwo);
        json = fileExplorerRemote.getOngoingDirectoryJSON();
        File file = new File("C:\\TestExplorer\\abc.xml");
        assertTrue(result);
        assertNotNull(json);
        assertTrue(file.exists());

        result = fileExplorerRemote.delete(pathOne);
        json = fileExplorerRemote.getOngoingDirectoryJSON();
        assertTrue(result);
        assertNotNull(json);

        result = file.delete();
        assertTrue(result);

        file = new File("C:\\TestExplorer");
        result = file.delete();
        assertTrue(result);
    }

    @Test
    void getOngoingDirectoryJSON() {
        boolean result = fileExplorerRemote.goToDirectory("C:\\");
        assertTrue(result);
        String json = fileExplorerRemote.getOngoingDirectoryJSON();
        assertNotNull(json);
    }

    @Test
    void getOngoingDirectory() {
        OngoingDirectory ongoingDirectory = fileExplorerRemote.getOngoingDirectory();
        assertNotNull(ongoingDirectory);
        ArrayList<String> directories = ongoingDirectory.getDirectories();
        assertNotNull(directories);
    }

}
