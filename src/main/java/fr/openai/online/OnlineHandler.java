package fr.openai.online;

import com.mongodb.client.MongoCollection;
import fr.openai.database.ConnectDb;
import fr.openai.database.UsernameProvider;
import fr.openai.exec.utils.DatabaseUtils;
import org.bson.Document;

import java.util.TimeZone;

public class OnlineHandler extends UsernameProvider {
    private final DatabaseUtils databaseUtils = new DatabaseUtils();
    public final String COLLECTION_NAME = "online";

    public void addOnlineUser(String line) {
        String username = getUsername();
        if (username == null || username.length() <=3 ) {
            return;
        }

        MongoCollection<Document> collection = ConnectDb.getMongoCollection(COLLECTION_NAME);
        Document filter = new Document("username", username);
        databaseUtils.deleteDocuments(collection, filter);

        long currentTimestamp = System.currentTimeMillis();
        long fiveHoursAgo = currentTimestamp - (5 * 60 * 60 * 1000);

        Document timestampFilter = new Document("timestamp", new Document("$lt", fiveHoursAgo));
        databaseUtils.deleteDocuments(collection, timestampFilter);

        String serverID = extractUserText(line);
        Document document = new Document("userText", serverID)
                .append("username", username)
                .append("timestamp", System.currentTimeMillis())
                .append("timezone", TimeZone.getDefault().getID());

        collection.insertOne(document);
    }

    public String extractUserText(String line) {
        int startIndex = line.indexOf("Join to server") + "Join to server".length();
        return line.substring(startIndex).trim();
    }
}
