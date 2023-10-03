package fr.openai.database.files;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

public class ConnectDb {
    private static MongoClient mongoClient; // Singleton instance for MongoDB client

    private ConnectDb() {
        // Private constructor to prevent instantiation
    }

    public static MongoClient getMongoClient() {
        if (mongoClient == null) {
            initializeMongoDB();
        }
        return mongoClient;
    }

    private static void initializeMongoDB() {
        String connectionString = "mongodb+srv://quazheat:54tp1EtyKj0oLTuV@bhscore.w1rtmok.mongodb.net/?retryWrites=true&w=majority";
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(connectionString))
                .build();

        mongoClient = MongoClients.create(settings);
    }

    public static void closeMongoClient() {
        if (mongoClient != null) {
            mongoClient.close();
        }
    }
}
