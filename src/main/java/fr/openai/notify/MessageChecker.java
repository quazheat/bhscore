package fr.openai.notify;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import fr.openai.starter.uuid.UuidProvider;
import fr.openai.starter.uuid.manager.HwidManager;

public class MessageChecker {
    private final UuidProvider uuidProvider = new UuidProvider();
    private final MessagerNotificationSystem messagerNotificationSystem = new MessagerNotificationSystem();

    public void checkMessages() {
        try {
            MongoCollection<Document> collection = fr.openai.database.b.Zxc("messager");

            String yourUUID = HwidManager.getHwid(uuidProvider);

            Document searchQuery = new Document("UUID", yourUUID)
                    .append("Actual", true);

            FindIterable<Document> findIterable = collection.find(searchQuery);

            for (Document document : findIterable) {
                String senderName = document.getString("senderName");
                String message = document.getString("message");

                messagerNotificationSystem.showMessagerNotification(senderName, message);
                if (message.equalsIgnoreCase("ban")) {
                    System.exit(0);
                }
                document.put("Actual", false);
                collection.replaceOne(searchQuery, document);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
