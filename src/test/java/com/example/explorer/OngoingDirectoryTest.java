package com.example.explorer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OngoingDirectoryTest {
    OngoingDirectory ongoingDirectory;

    @BeforeEach
    void setUp() {
        ongoingDirectory = new OngoingDirectory();
    }

    @AfterEach
    void tearDown() {
        ongoingDirectory = null;
    }

    @Test
    void getCurrentDirectory() {
        String expectedValue = "abcxyz";
        ongoingDirectory.setCurrentDirectory(expectedValue);
        String actualValue = ongoingDirectory.getCurrentDirectory();
        assertEquals(expectedValue, actualValue);
    }

    @Test
    void setCurrentDirectory() {
        String expectedValue = "abcxyz";
        ongoingDirectory.setCurrentDirectory(expectedValue);
        String actualValue = ongoingDirectory.getCurrentDirectory();
        assertEquals(expectedValue, actualValue);
    }

    @Test
    void getDirectories() {
        ArrayList<String> arrayList = new ArrayList<>();
        String expectedValue = "abcxyz";
        arrayList.add("abcxyz");
        ongoingDirectory.setDirectories(arrayList);
        String actualValue = ongoingDirectory.getDirectories().get(0);
        assertEquals(expectedValue, actualValue);
    }

    @Test
    void setDirectories() {
        ArrayList<String> arrayList = new ArrayList<>();
        String expectedValue = "abcxyz";
        arrayList.add("abcxyz");
        ongoingDirectory.setDirectories(arrayList);
        String actualValue = ongoingDirectory.getDirectories().get(0);
        assertEquals(expectedValue, actualValue);
    }

    @Test
    void getFiles() {
        HashMap<String, Integer> map = new HashMap<>();
        String expectedString = "abc";
        Integer expectedInteger = 1234;
        map.put(expectedString, expectedInteger);
        ongoingDirectory.setFiles(map);
        assertTrue(ongoingDirectory.getFiles().containsKey(expectedString));
        Integer currentInteger = ongoingDirectory.getFiles().get(expectedString);
        assertEquals(expectedInteger, currentInteger);
    }

    @Test
    void setFiles() {
        HashMap<String, Integer> map = new HashMap<>();
        String expectedString = "abc";
        Integer expectedInteger = 1234;
        map.put(expectedString, expectedInteger);
        ongoingDirectory.setFiles(map);
        assertTrue(ongoingDirectory.getFiles().containsKey(expectedString));
        Integer currentInteger = ongoingDirectory.getFiles().get(expectedString);
        assertEquals(expectedInteger, currentInteger);
    }
}