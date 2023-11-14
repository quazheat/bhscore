package fr.openai.online;

import com.mongodb.client.MongoCollection;
import fr.openai.database.b;
import fr.openai.database.UsernameProvider;
import fr.openai.exec.Names;
import fr.openai.exec.utils.DatabaseUtils;
import org.bson.Document;

import java.util.TimeZone;

public class OnlineHandler extends UsernameProvider {
    private final DatabaseUtils databaseUtils = new DatabaseUtils();
    public final String COLLECTION_NAME = "online";

    public void addOnlineUser(String line) {
        String serverID = extractUserText(line);
        String username = getUsername();
        if (username == null || username.length() <= 3) {
            return;
        }
        if (anonMod) {
            return;
        }

        MongoCollection<Document> collection = b.Zxc(COLLECTION_NAME);
        Document filter = new Document("username", username);
        databaseUtils.deleteDocuments(collection, filter);

        long currentTimestamp = System.currentTimeMillis();
        long fiveHoursAgo = currentTimestamp - (5 * 60 * 60 * 1000);

        Document timestampFilter = new Document("timestamp", new Document("$lt", fiveHoursAgo));
        databaseUtils.deleteDocuments(collection, timestampFilter);

        Document document = new Document("userText", serverID)
                .append("username", username)
                .append("timestamp", System.currentTimeMillis())
                .append("timezone", TimeZone.getDefault().getID());

        collection.insertOne(document);
    }

    private String extractUserText(String line) {
        int startIndex = line.indexOf("Join to server") + "Join to server".length();
        if (line.substring(startIndex).trim().contains("Хаб")) {
            System.out.println("hub ");
            hubDetected = true;
            return line.substring(startIndex).trim();
        }
        if (line.substring(startIndex).trim().contains("SkyBlock")) {
            Names.isSkyBlock = true;
            System.out.println("skyblock");

            return line.substring(startIndex).trim();
        }
        if (line.substring(startIndex).trim().contains("CSC ")) {
            cscDetected = true;
            System.out.println("CSC");

            return line.substring(startIndex).trim();
        }

        cscDetected = false;
        Names.isSkyBlock = false;
        hubDetected = false;
        System.out.println("skyblock and hub false");
        return line.substring(startIndex).trim();

    }

    public static boolean cscDetected = false;
    public static boolean hubDetected = false;
    public static boolean anonMod = true;
}
