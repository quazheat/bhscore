package fr.openai.online;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import fr.openai.database.ConfigManager;
import fr.openai.database.ConnectDb;
import fr.openai.discordfeatures.DiscordRPCDiag;
import fr.openai.ui.panels.OnlineUserLoaderGUI;
import org.bson.Document;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class OnlineUserLoader {
    private final ConfigManager configManager = new ConfigManager();
    private static final String COLLECTION_NAME = "online";
    OnlineUserLoaderGUI gui = new OnlineUserLoaderGUI();

    public void loadOnlineUsers() {
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        ArrayList<String> onlineUsers = new ArrayList<>();

        MongoCollection<Document> collection = ConnectDb.getMongoCollection(COLLECTION_NAME);
        FindIterable<Document> documents = collection.find();
        MongoCursor<Document> cursor = documents.iterator();
        String yourUsername = configManager.getUsername();
        if (yourUsername == null || yourUsername.length() <= 3) {
            yourUsername = DiscordRPCDiag.getUsername();
        }

        while (cursor.hasNext()) {
            Document document = cursor.next();
            String username = document.getString("username");
            String serverID = document.getString("userText");
            long timestamp = document.getLong("timestamp");

            Date date = new Date(timestamp);
            String timeString = timeFormat.format(date);

            onlineUsers.add(username + " на " + serverID + " (вход в " + timeString + ")");
        }

        cursor.close();
        gui.createAndShowGUI(onlineUsers, yourUsername);
    }
}
