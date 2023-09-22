package fr.openai.runtime;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Cleaner implements Runnable {
    private static final String JSON_FILE_PATH = "floodDB.json"; // Путь к JSON-файлу
    private List<JSONObject> messages = new ArrayList<>();
    private boolean running = true; // Флаг для остановки потока

    public void run() {
        while (running) {
            cleanMessages();
            try {
                Thread.sleep(500); // Периодическая проверка каждые 0.5 секунды
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void stop() {
        running = false;
    }

    public void addMessage(JSONObject jsonMessage) {
        messages.add(jsonMessage);
    }

    public void cleanMessages() {
        long currentTimestamp = Instant.now().getEpochSecond();
        Iterator<JSONObject> iterator = messages.iterator();
        JSONArray jsonArray = new JSONArray(); // Create a JSON array to store the updated messages

        while (iterator.hasNext()) {
            JSONObject jsonMessage = iterator.next();
            long messageTimestamp = (long) jsonMessage.get("timestamp");

            // If the message is older than 58 seconds, remove it from the list
            if (currentTimestamp - messageTimestamp > 58) {
                iterator.remove();
            } else {
                // If the message is not older than 58 seconds, add it to the JSON array
                jsonArray.add(jsonMessage);
            }
        }

        // Сохраняем и снова открываем базу данных после очистки
        saveDatabase(jsonArray);
        reloadDatabase();

        // Debug message to check the messages after cleaning
        /*System.out.println("After cleaning: " + messages);*/
    }

    // Метод для сохранения сообщений в JSON-файл
    private void saveDatabase(JSONArray jsonArray) {
        try (FileWriter fileWriter = new FileWriter(JSON_FILE_PATH)) {
            String newline = System.getProperty("line.separator");

            fileWriter.write("[" + newline);

            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject jsonMessage = (JSONObject) jsonArray.get(i); // Приведение к типу JSONObject
                fileWriter.write(jsonMessage.toJSONString());

                if (i < jsonArray.size() - 1) {
                    fileWriter.write("," + newline); // Добавляем запятую и новую строку после каждой записи, кроме последней
                }
            }

            fileWriter.write("]" + newline);

            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // Метод для перезагрузки базы данных
    public void reloadDatabase() {
        closeDatabase(); // Закрываем текущее соединение с базой данных
        loadDatabase(); // Перечитываем базу данных из файла JSON
        openDatabase(); // Затем снова открываем файл базы данных
    }

    // Метод для закрытия базы данных
    private void closeDatabase() {
        // Здесь можно добавить логику закрытия файла JSON
    }

    // Метод для открытия базы данных
    private void openDatabase() {
        // Здесь можно добавить логику открытия файла JSON
    }

    // Метод для загрузки базы данных
    private void loadDatabase() {
        try {
            File file = new File(JSON_FILE_PATH);
            if (!file.exists() || file.length() == 0) {
                // File is empty or doesn't exist; handle this situation accordingly.
                return;
            }

            BufferedReader reader = new BufferedReader(new FileReader(JSON_FILE_PATH));
            StringBuilder jsonContent = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonContent.append(line);
            }
            reader.close();

            JSONParser parser = new JSONParser();
            JSONArray jsonArray = (JSONArray) parser.parse(jsonContent.toString());
            messages.clear(); // Clear the database before loading new data
            for (int i = 0; i < jsonArray.size(); i++) {
                messages.add((JSONObject) jsonArray.get(i));
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
}