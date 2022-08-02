package com.example.network;


import com.example.network.NetworkConnection;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NetworkConnectionTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach

    @Test
    void getConnection() {
        Socket connection = NetworkConnection.getConnection();
        assert connection != null;
        boolean result =  connection.isConnected();
        int port = connection.getPort();
        assertTrue(result);
        assertEquals(6767, port);
        try {
            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}