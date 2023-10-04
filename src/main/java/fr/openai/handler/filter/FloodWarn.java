package fr.openai.handler.filter;

import com.google.gson.Gson;
import fr.openai.notify.NotificationSystem;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class FloodWarn {
    private static NotificationSystem notificationSystem;
    // Создаем Set для хранения имен игроков, нарушивших правило
    private static final Set<String> violations = new HashSet<>();

    public FloodWarn(NotificationSystem notificationSystem) {
        FloodWarn.notificationSystem = notificationSystem;
    }

    public static void checkWarn(String filePath) {
        try {
            // Чтение содержимого JSON файла
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            StringBuilder jsonContent = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonContent.append(line);
            }
            reader.close();

            // Разбор JSON в объекты
            Gson gson = new Gson();
            ChatMessage[] messages = gson.fromJson(jsonContent.toString(), ChatMessage[].class);

            // Создание мапы для подсчета одинаковых сообщений по игрокам
            Map<String, Map<String, Integer>> playerMessagesMap = new HashMap<>();

            // Подсчет одинаковых сообщений по игрокам
            for (ChatMessage message : messages) {
                String playerName = message.getPlayerName();
                String messageText = message.getMessage();

                Map<String, Integer> messageCountMap = playerMessagesMap.computeIfAbsent(playerName, k -> new HashMap<>());
                int messageCount = messageCountMap.getOrDefault(messageText, 0);
                messageCountMap.put(messageText, messageCount + 1);

                if (messageCount + 1 >= 3 && !violations.contains(playerName)) {
                    notificationSystem.showNotification(playerName, "3 same messages");
                    // Добавляем имя игрока в массив violations и устанавливаем таймер на 60 секунд
                    violations.add(playerName);
                    new java.util.Timer().schedule(
                            new java.util.TimerTask() {
                                @Override
                                public void run() {
                                    // Удаляем имя игрока из массива violations после 60 секунд
                                    violations.remove(playerName);
                                }
                            },
                            60000
                    );
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
