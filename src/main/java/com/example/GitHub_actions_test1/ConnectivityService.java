package com.example.GitHub_actions_test1;

import com.mongodb.client.MongoClient;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConnectivityService {

    @Autowired(required = false)
    private MongoClient mongoClient;

    public boolean ping() {
        if (mongoClient == null) {
            System.err.println("MongoClient not configured: MONGODB_URI not set.");
            return false;
        }
        try {
            // Use the auth database for ping checks. In Atlas this is typically "admin",
            // and it avoids failures when the application database name is not present.
            Document result = mongoClient.getDatabase("admin").runCommand(new Document("ping", 1));
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
