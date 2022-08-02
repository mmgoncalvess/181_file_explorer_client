package com.example.explorer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CopyBoardTest {
    CopyBoard copyBoard;

    @BeforeEach
    void setUp() {
        copyBoard = CopyBoard.getInstance();
    }

    @AfterEach
    void tearDown() {
        copyBoard = null;
    }

    @Test
    void getInstance() {
        CopyBoard copyBoard = CopyBoard.getInstance();
        copyBoard.fillCopyBoard("a", "b", "c", "s", false);
        assertNotNull(copyBoard);
        int expectedValue = copyBoard.hashCode();
        //System.out.println("Hashcode one: " + expectedValue);
        CopyBoard copyBoardTwo = CopyBoard.getInstance();
        copyBoardTwo.fillCopyBoard("c", "d", "f", "s", true);
        int actualValue = CopyBoard.getInstance().hashCode();
        //System.out.println("Hashcode two: " + actualValue);
        assertEquals(expectedValue, actualValue);
    }

    @Test
    void fillCopyBoard() {
        assertFalse(copyBoard.isFilled());
        copyBoard.fillCopyBoard("a", "b", "c", "s", false);
        assertTrue(copyBoard.isFilled());
    }

    @Test
    void clean() {
        copyBoard.fillCopyBoard("a", "b", "c", "s", false);
        assertTrue(copyBoard.isFilled());
        copyBoard.clean();
        assertFalse(copyBoard.isFilled());
    }

    @Test
    void getParent() {
        copyBoard.fillCopyBoard("a", "b", "c", "s", false);
        String expectedValue = "a";
        String actualValue = copyBoard.getParent();
        assertEquals(expectedValue, actualValue);
    }

    @Test
    void getFileName() {
        copyBoard.fillCopyBoard("a", "b", "c", "s", false);
        String expectedValue = "b";
        String actualValue = copyBoard.getFileName();
        assertEquals(expectedValue, actualValue);
    }

    @Test
    void getHostname() {
        copyBoard.fillCopyBoard("a", "b", "c", "s", false);
        String expectedValue = "c";
        String actualValue = copyBoard.getHostname();
        assertEquals(expectedValue, actualValue);
    }

    @Test
    void isFilled() {
        copyBoard.fillCopyBoard("a", "b", "c", "s", false);
        assertTrue(copyBoard.isFilled());
        copyBoard.clean();
        assertFalse(copyBoard.isFilled());
    }

    @Test
    void isDirectory() {
        copyBoard.fillCopyBoard("a", "b", "c", "s", false);
        assertFalse(copyBoard.isDirectory());
    }

    @Test
    void testToString() {
        int a = copyBoard.toString().length();
        copyBoard.fillCopyBoard("a", "b", "abcdefghijklmnopq", "s", false);
        int b = copyBoard.toString().length();
        assertTrue(b > a);
    }
}