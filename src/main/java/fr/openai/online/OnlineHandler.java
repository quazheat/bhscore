package fr.openai.online;

import com.mongodb.client.MongoCollection;
import fr.openai.database.ConfigManager;
import fr.openai.database.ConnectDb;
import fr.openai.discordfeatures.DiscordRPCDiag;
import org.bson.Document;

public class OnlineHandler {
    final ConfigManager configManager = new ConfigManager();
    public final String COLLECTION_NAME = "online";

    public void addOnlineUser(String line) {
        String username = configManager.getUsername();
        if (username == null || username.length() <= 3) {
            username = DiscordRPCDiag.getUsername();
            if (username == null || username.length() <= 3) {
                return;
            }
        }

        MongoCollection<Document> collection = ConnectDb.getMongoCollection(COLLECTION_NAME);
        Document filter = new Document("username", username);
        DatabaseUtils.deleteDocuments(collection, filter);

        long currentTimestamp = System.currentTimeMillis();
        long fiveHoursAgo = currentTimestamp - (5 * 60 * 60 * 1000);

        Document timestampFilter = new Document("timestamp", new Document("$lt", fiveHoursAgo));
        DatabaseUtils.deleteDocuments(collection, timestampFilter);

        String serverID = extractUserText(line);
        Document document = new Document("userText", serverID)
                .append("username", username)
                .append("timestamp", System.currentTimeMillis()); // Добавляем текущее время в формате UNIX timestamp

        collection.insertOne(document);
    }

    public String extractUserText(String line) {
        int startIndex = line.indexOf("Join to server") + "Join to server".length();
        return line.substring(startIndex).trim();
    }
}
