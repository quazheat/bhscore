package fr.openai.handler.filter;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import fr.openai.database.DatabaseManager;

import java.time.Instant;
import java.util.Iterator;
import java.util.List;

public class Cleaner implements Runnable {
    private final List<JsonObject> liveChat; // Список live_chat
    private boolean running = true;

    public Cleaner(List<JsonObject> liveChat) {
        this.liveChat = liveChat;
    }

    public void run() {
        while (running) {
            // Создаем новый поток для выполнения cleanMessages
            Thread cleanMessagesThread = new Thread(this::cleanMessages);
            cleanMessagesThread.start();

            try {
                long CLEANING_INTERVAL_MILLISECONDS = 900;
                Thread.sleep(CLEANING_INTERVAL_MILLISECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void stop() {
        running = false;
    }

    public void cleanMessages() {
        if (liveChat.isEmpty()) return;
        long currentTimestamp = Instant.now().getEpochSecond();
        Iterator<JsonObject> iterator = liveChat.iterator();
        JsonArray jsonArray = new JsonArray(); // Используем JsonArray для хранения сообщений

        while (iterator.hasNext()) {
            JsonObject jsonMessage = iterator.next();

            if (!jsonMessage.has("player_name") || !jsonMessage.has("message")) {
                break; // Прерываем цикл, если player_name или message отсутствуют
            }

            JsonElement playerNameElement = jsonMessage.get("player_name");
            JsonElement messageElement = jsonMessage.get("message");

            if (playerNameElement.isJsonNull() || messageElement.isJsonNull()) {
                break; // Прерываем цикл, если player_name или message равны null
            }

            String playerName = playerNameElement.getAsString();
            String message = messageElement.getAsString();

            // Проверяем, что player_name не равен "Unknown" и message не равен пустой строке
            if (playerName.equals("Unknown") || message.isEmpty()) {
                break; // Прерываем цикл, если player_name равен "Unknown" или message пустое
            }

            // Check if timestamp exists and is not null
            if (!jsonMessage.has("timestamp")) {
                break; // Прерываем цикл, если timestamp отсутствует
            }

            JsonElement timestampElement = jsonMessage.get("timestamp");

            if (timestampElement.isJsonNull()) {
                break; // Прерываем цикл, если timestamp равен null
            }

            long messageTimestamp = timestampElement.getAsLong();

            int MESSAGE_EXPIRATION_SECONDS = 58;
            if (currentTimestamp - messageTimestamp <= MESSAGE_EXPIRATION_SECONDS) {
                jsonArray.add(jsonMessage); // Добавляем сообщение в JsonArray
            }
        }

        DatabaseManager.saveMessages(jsonArray); // Сохраняем JsonArray в базу данных
        FloodWarn.checkWarn("livechat.json");
    }

}
