package com.example.GitHub_actions_test1;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assumptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class ConnectivityIntegrationTest {

    @Autowired
    ConnectivityService connectivityService;

    @BeforeAll
    static void requireEnv() {
        String uri = System.getenv("MONGODB_URI");
        String user = System.getenv("MONGO_USER");
        String pass = System.getenv("MONGO_PASS");
        boolean hasUri = uri != null && !uri.isBlank();
        boolean hasUserPass = user != null && !user.isBlank() && pass != null && !pass.isBlank();
        Assumptions.assumeTrue(hasUri || hasUserPass, "Skipping connectivity test: set MONGODB_URI or MONGO_USER & MONGO_PASS as secrets in the CI environment.");
    }

    @Test
    void mongoPing() {
        // This test requires either MONGODB_URI or MONGO_USER+MONGO_PASS to be provided by the environment
        assertTrue(connectivityService.ping(), "Ping to MongoDB should succeed. Ensure secrets are set in the workflow.");
    }
}
