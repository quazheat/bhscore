package fr.openai.handler.filter;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import fr.openai.database.JsonManager;
import fr.openai.notify.NotificationSystem;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class FloodWarn {
    private NotificationSystem notificationSystem;
    public FloodWarn() {
        this.notificationSystem = new NotificationSystem();
    }
    private final JsonManager jsonManager = new JsonManager();
    private final ViolationRecorder violationRecorder = new ViolationRecorder(); // Экземпляр нового класса
    private static final String CHAT_PATH = "livechat.json"; // Путь к файлу chat
    private static final String VIOLATIONS_PATH = "violations.json"; // Путь к файлу violations.json
    public void checkWarn() {
        boolean fileNotFound = false;

        while (!fileNotFound) {
            File chatFile = new File(CHAT_PATH);

            if (!chatFile.exists() || chatFile.length() == 0) return;

            jsonManager.isExist(VIOLATIONS_PATH);
            try (FileReader reader = new FileReader(CHAT_PATH))
            {
                if (chatFile.length() > 0)
                {
                    JsonArray jsonArray = JsonParser.parseReader(reader).getAsJsonArray();
                    Map<String, Integer> messageCounts = new HashMap<>();
                    for (int i = 0; i < jsonArray.size(); i++) {
                        JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
                        String playerName = jsonObject.get("player_name").getAsString();
                        String message = jsonObject.get("message").getAsString();
                        if (playerName != null && !playerName.equalsIgnoreCase("Unknown")) {
                            String messageKey = playerName + ":" + message;
                            messageCounts.put(messageKey, messageCounts.getOrDefault(messageKey, 0) + 1);
                            if (messageCounts.get(messageKey) >= 3) {
                                if (!violationRecorder.isRecorded(playerName, message)) {
                                    violationRecorder.recordViolation(playerName, message);
                                    System.out.println("FLOOD WARNING: " + playerName + " sent the same message 3 times: " + message);
                                    showNotification(playerName);
                                }
                            }
                        }
                    }
                } fileNotFound = true;
            } catch (IOException e) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
    private void showNotification(String playerName) {
        notificationSystem.showNotification(playerName, "3 similar messages");
    }
}
