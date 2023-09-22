package fr.openai.handler.filter;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class FloodWarn {
    private static final String CHAT_PATH = "livechat.json"; // Путь к файлу chat
    private static final String VIOLATIONS_PATH = "violations.json"; // Путь к файлу violations.json

    public void checkWarn() {
        boolean fileNotFound = false;

        while (!fileNotFound) {
            File chatFile = new File(CHAT_PATH);

            if (!chatFile.exists() || chatFile.length() == 0) {
                return;
            }

            isExist(); // Проверяем и создаем violations.json, если его нет

            try (FileReader reader = new FileReader(CHAT_PATH)) {
                // Сначала проверяем, не пустой ли файл, и только затем парсим его
                if (chatFile.length() > 0) {
                    JsonArray jsonArray = JsonParser.parseReader(reader).getAsJsonArray();

                    Map<String, Integer> messageCounts = new HashMap<>();

                    for (int i = 0; i < jsonArray.size(); i++) {
                        JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
                        String playerName = jsonObject.get("player_name").getAsString();
                        String message = jsonObject.get("message").getAsString();

                        // Проверяем, что никнейм игрока не равен null и не равен "Unknown"
                        if (playerName != null && !playerName.equalsIgnoreCase("Unknown")) {
                            String messageKey = playerName + ":" + message;

                            messageCounts.put(messageKey, messageCounts.getOrDefault(messageKey, 0) + 1);

                            if (messageCounts.get(messageKey) >= 3) {
                                // Проверяем нарушение в файле violations.json
                                if (!isRecorded(playerName, message)) {
                                    // Нарушение еще не записано, записываем его и отправляем уведомление
                                    recordViolation(playerName, message);
                                    System.out.println("FLOOD WARNING: " + playerName + " sent the same message 3 times: " + message);
                                }
                            }
                        }
                    }
                }

                // Если дошли до этой точки, файл существует и был успешно обработан
                fileNotFound = true;
            } catch (IOException e) {
                // Если файл не найден, подождем перед следующей попыткой
                try {
                    Thread.sleep(100); // Подождать 100 миллисекунд перед следующей попыткой
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }


    private void isExist() {
        File violationsFile = new File(VIOLATIONS_PATH);

        if (!violationsFile.exists()) {
            try {
                if (violationsFile.createNewFile()) {
                    System.out.println("Created violations.json");
                    // Если файл был успешно создан, записываем в него пустой JSON-массив
                    try (FileWriter writer = new FileWriter(VIOLATIONS_PATH)) {
                        writer.write("[]");
                    }
                } else {
                    System.out.println("Failed to create violations.json");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private boolean isRecorded(String playerName, String message) {
        // Проверяем нарушение в файле violations.json
        try (FileReader reader = new FileReader(VIOLATIONS_PATH)) {
            JsonArray jsonArray = JsonParser.parseReader(reader).getAsJsonArray();

            for (int i = 0; i < jsonArray.size(); i++) {
                JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
                String rNick = jsonObject.get("player_name").getAsString();
                String rMessage = jsonObject.get("message").getAsString();

                // Если нарушение уже записано в файле, возвращаем true
                if (rNick.equals(playerName) && rMessage.equals(message)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false; // Нарушение не найдено
    }

    private void recordViolation(String playerName, String message) {
        // Записываем нарушение в файл violations.json с текущим временем в формате UNIX
        long currentTimestamp = System.currentTimeMillis() / 1000L;

        try (FileReader reader = new FileReader(VIOLATIONS_PATH)) {
            JsonArray jsonArray = JsonParser.parseReader(reader).getAsJsonArray();

            JsonObject newViolation = new JsonObject();
            newViolation.addProperty("player_name", playerName);
            newViolation.addProperty("message", message);
            newViolation.addProperty("timestamp", currentTimestamp);

            jsonArray.add(newViolation);

            // Записываем обновленный массив нарушений обратно в файл
            try (FileWriter writer = new FileWriter(VIOLATIONS_PATH)) {
                writer.write(jsonArray.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
