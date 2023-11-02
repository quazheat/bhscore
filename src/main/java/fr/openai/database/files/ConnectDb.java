package fr.openai.database.files;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.*;
import org.bson.Document;

import java.io.FileWriter;
import java.io.IOException;

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

    public static MongoCollection<Document> getMongoCollection(String collectionName) {
        MongoClient mongoClient = getMongoClient();
        MongoDatabase database = mongoClient.getDatabase("BHScore");

        return database.getCollection(collectionName);
    }

    public static void closeMongoClient() {
        if (mongoClient != null) {
            mongoClient.close();
        }
    }

    public static void getWordsDB() {
        // Получить коллекцию из MongoDB
        MongoCollection<Document> collection = ConnectDb.getMongoCollection("words");

        // Выполнить запрос к коллекции
        FindIterable<Document> documents = collection.find();

        // Создать файл JSON для записи
        try (FileWriter fileWriter = new FileWriter("words.json")) {
            for (Document document : documents) {
                fileWriter.write(document.toJson());
                fileWriter.write(System.lineSeparator()); // Добавить перевод строки между документами
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("успешно экспортирован файл 'words.json'.");
    }


}
