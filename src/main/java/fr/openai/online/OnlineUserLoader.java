package fr.openai.online;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import fr.openai.database.b;
import fr.openai.database.UsernameProvider;
import org.bson.Document;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

public class OnlineUserLoader extends UsernameProvider {
    private static final String COLLECTION_NAME = "online";

    public void loadOnlineUsers() {
        ArrayList<String> onlineUsers = new ArrayList<>();
        OnlineUserLoaderGUI gui = new OnlineUserLoaderGUI();

        MongoCollection<Document> collection = b.Zxc(COLLECTION_NAME);
        FindIterable<Document> documents = collection.find();
        MongoCursor<Document> cursor = documents.iterator();
        String yourUsername = getUsername();
        if (yourUsername == null || yourUsername.length() <=3 ) {
            return;
        }

        while (cursor.hasNext()) {
            Document document = cursor.next();
            String username = document.getString("username");
            String serverID = document.getString("userText");
            long timestamp = document.getLong("timestamp");
            String userTimezone = document.getString("timezone");

            Date date = new Date(timestamp);
            SimpleDateFormat userTimeFormat = new SimpleDateFormat("HH:mm z");
            userTimeFormat.setTimeZone(TimeZone.getTimeZone(userTimezone));
            String timeString = userTimeFormat.format(date);

            onlineUsers.add(username + " на " + serverID + " (вход в " + timeString + ")");
        }

        cursor.close();

        if (onlineUsers.isEmpty()) {
            onlineUsers.add("          Мы не смогли никого найти            ");
        }

        gui.createAndShowGUI(onlineUsers, yourUsername);
    }
}
