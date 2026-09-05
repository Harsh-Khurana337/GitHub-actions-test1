package com.example.GitHub_actions_test1;

import org.springframework.stereotype.Component;
import com.mongodb.client.MongoClient;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;

@Component
public class ConnectivityService {

    @Autowired
    private MongoClient mongoClient;

    /**
     * Run a simple ping command against the server to verify connectivity.
     */
    public boolean ping() {
        try {
            Document result = mongoClient.getDatabase("mongodb").runCommand(new Document("ping", 1));
            Object ok = result.get("ok");
            if (ok instanceof Number) {
                return ((Number) ok).doubleValue() == 1.0;
            }
            return "1.0".equals(ok != null ? ok.toString() : null) || "1".equals(ok != null ? ok.toString() : null);
        } catch (Exception e) {
            System.err.println("Mongo ping failed: " + e.getMessage());
            return false;
        }
    }
}
