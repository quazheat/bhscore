package fr.openai.runtime;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.*;
import java.time.Instant;
import java.util.Iterator;

public class Cleaner implements Runnable {
    private final MessageManager messageManager;
    private boolean running = true;

    public Cleaner(MessageManager messageManager) {
        this.messageManager = messageManager;
    }

    public void run() {
        while (running) {
            cleanMessages();
            cleanOldViolations(); // Добавляем очистку старых нарушений
            try {
                long CLEANING_INTERVAL_MILLISECONDS = 500;
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
        Iterator<JSONObject> iterator = messageManager.getMessages().iterator();
        JSONArray jsonArray = new JSONArray(); // Создаем JSONArray для хранения очищенных сообщений

        while (iterator.hasNext()) {
            JSONObject jsonMessage = iterator.next();
            long messageTimestamp = (long) jsonMessage.get("timestamp");

            int MESSAGE_EXPIRATION_SECONDS = 58;
            if (currentTimestamp - messageTimestamp > MESSAGE_EXPIRATION_SECONDS) {
                iterator.remove();
            } else {
                jsonArray.add(jsonMessage); // Добавляем сообщение в JSONArray
            }
        }

        DatabaseManager.saveMessages(jsonArray); // Сохраняем JSONArray в базу данных
    }

    private void cleanOldViolations() {
        long currentTimestamp = System.currentTimeMillis() / 1000L;
        long cutoffTimestamp = currentTimestamp - 58; // Определяем метку времени, старше которой нарушения будут удалены

        try (FileReader reader = new FileReader("violations.json")) {
            JsonElement jsonElement = JsonParser.parseReader(reader);

            // Проверяем, не является ли jsonElement null
            if (jsonElement != null && jsonElement.isJsonArray()) {
                JsonArray jsonArray = jsonElement.getAsJsonArray();

                Iterator<com.google.gson.JsonElement> iterator = jsonArray.iterator();
                while (iterator.hasNext()) {
                    JsonObject violation = iterator.next().getAsJsonObject();
                    long violationTimestamp = violation.get("timestamp").getAsLong();

                    if (violationTimestamp < cutoffTimestamp) {
                        iterator.remove(); // Удаляем нарушение, если оно старше заданной метки времени
                    }
                }

                // Записываем обновленный массив нарушений обратно в файл
                try (FileWriter writer = new FileWriter("violations.json")) {
                    writer.write(jsonArray.toString());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
