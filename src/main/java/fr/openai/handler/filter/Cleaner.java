package fr.openai.handler.filter;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import fr.openai.database.DatabaseManager;
import fr.openai.runtime.MessageManager;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.*;
import java.time.Instant;
import java.util.Iterator;
import java.util.List; // Добавляем импорт для List

public class Cleaner implements Runnable {
    private final List<JSONObject> liveChat; // Список live_chat
    private boolean running = true;

    public Cleaner(List<JSONObject> liveChat) {
        this.liveChat = liveChat;
    }

    public void run() {
        while (running) {
            // Создаем новый поток для выполнения cleanMessages
            Thread cleanMessagesThread = new Thread(() -> {
                cleanMessages();
            });
            cleanMessagesThread.start();

            cleanViolations(); // Добавляем очистку старых нарушений

            try {
                long CLEANING_INTERVAL_MILLISECONDS = 100;
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
        long currentTimestamp = Instant.now().getEpochSecond();
        Iterator<JSONObject> iterator = liveChat.iterator(); // Используем live_chat
        JSONArray jsonArray = new JSONArray(); // Создаем JSONArray для хранения сообщений

        while (iterator.hasNext()) {
            JSONObject jsonMessage = iterator.next();

            // Получаем значения player_name и message
            String playerName = (String) jsonMessage.get("player_name");
            String message = (String) jsonMessage.get("message");

            // Проверяем, что player_name не равен "Unknown" и message не равен null
            if (playerName != null && !playerName.equals("Unknown") && message != null) {
                long messageTimestamp = (long) jsonMessage.get("timestamp");

                int MESSAGE_EXPIRATION_SECONDS = 58;
                if (currentTimestamp - messageTimestamp <= MESSAGE_EXPIRATION_SECONDS) {
                    jsonArray.add(jsonMessage); // Добавляем сообщение в JSONArray
                }
            }
        }

        DatabaseManager.saveMessages(jsonArray); // Сохраняем JSONArray в базу данных
    }


    private void cleanViolations() {
        File violationsFile = new File("violations.json");

        if (!violationsFile.exists()) {
            // Файл не существует, продолжаем цикл
            return;
        }

        long currentTimestamp = System.currentTimeMillis() / 1000L;
        long cutoffTimestamp = currentTimestamp - 59;

        try (FileReader reader = new FileReader(violationsFile)) {
            JsonElement jsonElement = JsonParser.parseReader(reader);

            if (jsonElement != null && jsonElement.isJsonArray()) {
                JsonArray jsonArray = jsonElement.getAsJsonArray();

                Iterator<JsonElement> iterator = jsonArray.iterator();
                while (iterator.hasNext()) {
                    JsonObject violation = iterator.next().getAsJsonObject();
                    long violationTimestamp = violation.get("timestamp").getAsLong();

                    if (violationTimestamp < cutoffTimestamp) {
                        iterator.remove();
                    }
                }

                try (FileWriter writer = new FileWriter(violationsFile)) {
                    writer.write(jsonArray.toString());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
