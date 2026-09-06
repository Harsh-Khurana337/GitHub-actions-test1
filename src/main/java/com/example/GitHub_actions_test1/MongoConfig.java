package com.example.GitHub_actions_test1;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MongoConfig {

    @Value("${MONGODB_URI:${spring.data.mongodb.uri:mongodb://localhost:27017}}")
    private String mongoUri;

    @Bean
    public MongoClient mongoClient() {
        if (mongoUri == null || mongoUri.isBlank()) {
            mongoUri = "mongodb://localhost:27017";
        }

        // Basic validation to catch placeholder hosts like 'host', 'localhost', or 'example'
        try {
            String tmp = mongoUri;
            int at = tmp.indexOf('@');
            if (at != -1) tmp = tmp.substring(at + 1);
            else tmp = tmp.replaceFirst("(?i)^mongodb(\\+srv)?://", "");

            int slash = tmp.indexOf('/');
            String hostPart = (slash != -1) ? tmp.substring(0, slash) : tmp;
            int colon = hostPart.indexOf(':');
            if (colon != -1) hostPart = hostPart.substring(0, colon);
            hostPart = hostPart.toLowerCase();

            boolean isLocalMongo = "localhost".equals(hostPart) || "127.0.0.1".equals(hostPart) || "[::1]".equals(hostPart);
            if (("host".equals(hostPart) || hostPart.contains("example") || hostPart.contains("your-cluster")) && !isLocalMongo) {
                throw new IllegalStateException("MONGODB_URI contains a placeholder host '" + hostPart + "'. Replace with your Atlas cluster host (e.g. cluster0.ofvi4ed.mongodb.net) or provide a valid connection string.");
            }
        } catch (IllegalStateException e) {
            throw e;
        } catch (Exception ignored) {
            // parsing failed - let the driver surface DNS errors if any
        }

        return MongoClients.create(mongoUri);
    }
}
