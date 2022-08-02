package com.example.network;

import com.example.network.Request;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class RequestTest {

    @Test
    void values() {
        Request[] requests = Request.values();
        assertNotNull(requests);
        assertEquals(9, requests.length);
    }

    @Test
    void valueOf() {
        String string = "PARENT";
        Request expectedValue = Request.valueOf(string);
        Request actualValue = Request.PARENT;
        assertEquals(expectedValue, actualValue);
        assertEquals("ONGOING_DIRECTORY", Request.ONGOING_DIRECTORY.name());
        assertEquals("PARENT", Request.PARENT.name());
        assertEquals("DIRECTORY", Request.DIRECTORY.name());
        assertEquals("DELETE", Request.DELETE.name());
        assertEquals("NEW_DIRECTORY", Request.NEW_DIRECTORY.name());
        assertEquals("RENAME", Request.RENAME.name());
        assertEquals("COPY", Request.COPY.name());
        assertEquals("IMPORT", Request.IMPORT.name());
        assertEquals("EXPORT", Request.EXPORT.name());
    }
}