package fr.openai.runtime;

import fr.openai.runtime.DatabaseManager;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.time.Instant;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Cleaner implements Runnable {
    private MessageManager messageManager;
    private static final String JSON_FILE_PATH = "floodDB.json";
    private boolean running = true;
    private static final int MESSAGE_EXPIRATION_SECONDS = 58;
    private static final long CLEANING_INTERVAL_MILLISECONDS = 500;

    public Cleaner(MessageManager messageManager) {
        this.messageManager = messageManager;
    }

    public void run() {
        while (running) {
            cleanMessages();
            try {
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

            if (currentTimestamp - messageTimestamp > MESSAGE_EXPIRATION_SECONDS) {
                iterator.remove();
            } else {
                jsonArray.add(jsonMessage); // Добавляем сообщение в JSONArray
            }
        }

        DatabaseManager.saveMessages(jsonArray); // Сохраняем JSONArray в базу данных
    }
}
