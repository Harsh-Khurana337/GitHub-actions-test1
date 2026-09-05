package com.example.GitHub_actions_test1;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class ConnectivityIntegrationTest {

    @Autowired
    ConnectivityService connectivityService;

    @Test
    void mongoPing() {
        // This test requires the environment variable MONGODB_URI to be set (in GitHub Actions use a secret)
        assertTrue(connectivityService.ping(), "Ping to MongoDB should succeed. Set MONGODB_URI secret with a valid connection string.");
    }
}
