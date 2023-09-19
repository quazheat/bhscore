package fr.openai.handler.filter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import java.awt.*;
import javax.swing.*;

public class Flooding {
    private static final int MAX_SIMILAR_LINES = 3; // Максимальное количество похожих строк для нарушения
    private static final long TIMEOUT_MS = 60000; // Время ожидания для удаления строк (60 секунд)

    private List<String> lines = new ArrayList<>();
    private Map<String, Long> lineTimestamps = new HashMap<>();
    private int messageCounter = 0;
    private boolean firstMessageReceived = false;

    public void addMessage(String message) {
        messageCounter++;
        String messageWithNumber = "[" + messageCounter + "] " + message;

        if (!firstMessageReceived) {
            System.out.println("DEBUG " + messageCounter + ": " + messageWithNumber);
            firstMessageReceived = true;
        } else {
            System.out.println("DEBUG New message received: " + messageCounter + ": " + messageWithNumber);
        }

        lines.add(messageWithNumber);
        lineTimestamps.put(messageWithNumber, System.currentTimeMillis());

        checkForViolations();
    }

    private void checkForViolations() {
        if (lines.size() >= MAX_SIMILAR_LINES) {
            Map<String, Integer> lineCounts = new HashMap<>();

            // Считаем, сколько раз встречается каждое сообщение
            for (String message : lines) {
                lineCounts.put(message, lineCounts.getOrDefault(message, 0) + 1);
            }

            // Ищем сообщения, которые встречаются наиболее часто
            for (Map.Entry<String, Integer> entry : lineCounts.entrySet()) {
                if (entry.getValue() >= MAX_SIMILAR_LINES) { // Изменено условие на >=
                    String violationMessage = "Нарушение: " + entry.getKey();
                    System.out.println(violationMessage);

                    // Здесь можно выполнить действия в случае обнаружения нарушения
                }
            }
        }

        // Удаляем сообщения, истекшие по времени
        long currentTime = System.currentTimeMillis();
        List<String> messagesToRemove = new ArrayList<>();
        for (String message : lines) {
            if (currentTime - lineTimestamps.getOrDefault(message, 0L) >= TIMEOUT_MS) {
                messagesToRemove.add(message);
            }
        }
        lines.removeAll(messagesToRemove);
    }

}
