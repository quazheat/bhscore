package fr.openai.ui.panels;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;
import fr.openai.filter.Filtering;

public class StatsDatabaseManager {
    private final MongoCollection<Document> collection = fr.openai.database.b.Zxc("mutes_warns");

    public void saveMutesAndWarns(String username) {
        int mutes = Filtering.getMutes();
        int warns = Filtering.getWarns();

        Document searchQuery = new Document("username", username);
        FindIterable<Document> findIterable = collection.find(searchQuery);
        MongoCursor<Document> cursor = findIterable.iterator();

        if (cursor.hasNext()) {
            Document existingDocument = cursor.next();
            int existingMutes = existingDocument.getInteger("mutes", 0);
            int existingWarns = existingDocument.getInteger("warns", 0);

            existingDocument.put("mutes", existingMutes + mutes);
            existingDocument.put("warns", existingWarns + warns);

            collection.replaceOne(searchQuery, existingDocument);
            return;
        }

        Document newDocument = new Document();
        newDocument.put("username", username);
        newDocument.put("mutes", mutes);
        newDocument.put("warns", warns);

        collection.insertOne(newDocument);
    }

    public void deleteDocumentIfMatch(String username) {

        Document searchQuery = new Document("username", username);
        FindIterable<Document> findIterable = collection.find(searchQuery);
        MongoCursor<Document> cursor = findIterable.iterator();

        if (cursor.hasNext()) {
            Document updateDocument = new Document("$set", new Document("mutes", 0).append("warns", 0));
            collection.updateOne(searchQuery, updateDocument);
        }
    }

    public int getMutes(String username) {

        Document searchQuery = new Document("username", username);
        FindIterable<Document> findIterable = collection.find(searchQuery);
        MongoCursor<Document> cursor = findIterable.iterator();

        if (cursor.hasNext()) {
            Document document = cursor.next();
            return document.getInteger("mutes", 0);
        }

        return 0;
    }

    public int getWarns(String username) {

        Document searchQuery = new Document("username", username);
        FindIterable<Document> findIterable = collection.find(searchQuery);
        MongoCursor<Document> cursor = findIterable.iterator();

        if (cursor.hasNext()) {
            Document document = cursor.next();
            return document.getInteger("warns", 0);
        }

        return 0;
    }
}
