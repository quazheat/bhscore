package fr.openai.filter;

import fr.openai.filter.ChatMessage;
import fr.openai.notify.ClipboardUtil;
import fr.openai.notify.NotificationSystem;
import fr.openai.notify.WindowsNotification;
import fr.openai.reader.handler.LiveChatWriter;
import fr.openai.filter.Filtering;


import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static fr.openai.filter.Filtering.isRage;


public class LiveChatAnalyzer {
    private final ScheduledExecutorService executorService;
    private final Map<String, Long> lastNotificationTimes;
    private final NotificationSystem notificationSystem;

    public LiveChatAnalyzer(NotificationSystem notificationSystem) {
        this.notificationSystem = notificationSystem;
        this.executorService = Executors.newScheduledThreadPool(1);
        this.lastNotificationTimes = new HashMap<>();

    }

    public void startFindingFlood(String filePath) {
        executorService.scheduleAtFixedRate(() -> findRepeatingMessages(filePath), 0, 1, TimeUnit.SECONDS);
        System.out.println("LiveChatAnalyzer");
    }

    public void findRepeatingMessages(String filePath) {
        List<ChatMessage> chatMessages = LiveChatWriter.readLiveChat(filePath);

        if (chatMessages != null) {
            Map<String, Map<String, Integer>> playerMessageCount = new HashMap<>();

            for (ChatMessage message : chatMessages) {
                String name = message.getPlayerName();
                String messageText = message.getMessage();

                // Создаем мапу для подсчета одинаковых сообщений по игрокам
                Map<String, Integer> messageCountMap = playerMessageCount.computeIfAbsent(name, k -> new HashMap<>());
                int messageCount = messageCountMap.getOrDefault(messageText, 0);
                messageCountMap.put(messageText, messageCount + 1);

                if (messageCount + 1 == 3) {
                    long currentTime = System.currentTimeMillis();
                    if (!lastNotificationTimes.containsKey(name) || currentTime - lastNotificationTimes.get(name) >= 60000) {
                        lastNotificationTimes.put(name, currentTime);

                        // Используйте переменную isRage из класса Filtering
                        if (!Filtering.isRage) {
                            System.out.println("DETECTED: 5 одинаковых слов");
                            notificationSystem.showNotification(name, "3 same messages");
                        } else {
                            WindowsNotification.showWindowsNotification("RAGE", "2.10", TrayIcon.MessageType.ERROR);
                            String textToCopy = "/mute " + name + " 2.10";
                            ClipboardUtil.copyToClipboard(textToCopy);
                        }

                    }
                }
            }
        }
    }
}