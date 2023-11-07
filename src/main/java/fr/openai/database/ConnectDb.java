package fr.openai.database;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.*;
import org.bson.Document;

public class ConnectDb {
    private static MongoClient mongoClient;

    public MongoClient getMongoClient() {
        if (mongoClient == null) {
            initializeMongoDB();
        }
        return mongoClient;
    }
    public void closeMongoClient() {
        if (mongoClient != null) {
            mongoClient.close();
        }
    }

    private void initializeMongoDB() {
        String connectionString = "mongodb+srv://quazheat:54tp1EtyKj0oLTuV@bhscore.w1rtmok.mongodb.net/?retryWrites=true&w=majority";
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(connectionString))
                .build();

        mongoClient = MongoClients.create(settings);
    }

    public static MongoCollection<Document> getMongoCollection(String collectionName) {
        ConnectDb connectDb = new ConnectDb();
        MongoClient mongoClient = connectDb.getMongoClient();
        MongoDatabase database = mongoClient.getDatabase("BHScore");

        return database.getCollection(collectionName);
    }

}
