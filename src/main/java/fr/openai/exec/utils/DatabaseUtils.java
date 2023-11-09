package fr.openai.exec.utils;

import com.mongodb.client.MongoCollection;
import org.bson.Document;

public class DatabaseUtils {
    public void deleteDocuments(MongoCollection<Document> collection, Document filter) {
        collection.deleteMany(filter);
    }
}
