package fr.openai.online;

import com.mongodb.client.MongoCollection;
import org.bson.Document;

public class DatabaseUtils {
    public static void createDocument(MongoCollection<Document> collection, Document document) {
        collection.insertOne(document);
    }

    public static void deleteDocuments(MongoCollection<Document> collection, Document filter) {
        collection.deleteMany(filter);
    }
}
