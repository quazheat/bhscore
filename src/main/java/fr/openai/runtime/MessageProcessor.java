package fr.openai.runtime;

import fr.openai.exec.ClipboardUtil;
import fr.openai.notify.NotificationSystem;
import fr.openai.notify.WindowsNotification;

import java.util.ArrayList;
import java.util.List;

import static java.awt.TrayIcon.MessageType.ERROR;
import static java.awt.TrayIcon.MessageType.INFO;

public class MessageProcessor {
    private final NotificationSystem notificationSystem;


    static boolean isRage;
    static boolean isLoyal;
    private List<MessageRecord> messageRecords;

    public MessageProcessor(NotificationSystem notificationSystem) {
        this.messageRecords = new ArrayList<>();
        this.notificationSystem = notificationSystem;
    }

    public void processMessage(String playerName, String message) {
        long currentTime = System.currentTimeMillis() / 1000;

        // Удаляем записи старше 60 секунд
        messageRecords.removeIf(record -> currentTime - record.getTimestamp() >= 60);

        // Проверяем наличие одинаковых сообщений от игрока в течение минуты
        int duplicateCount = 0;
        for (MessageRecord record : messageRecords) {
            if (record.getPlayerName().equals(playerName) && record.getMessage().equals(message)) {
                duplicateCount++;
            }
        }

        // Добавляем новую запись
        MessageRecord newRecord = new MessageRecord(playerName, currentTime, message);
        messageRecords.add(newRecord);

        if (duplicateCount >= 2) {
            if (isLoyal){
                WindowsNotification.showWindowsNotification("LOYAL", "2.10", INFO);
                String textToCopyF = "/warn " + playerName + " Не флуди";
                ClipboardUtil.copyToClipboard(textToCopyF);
            } else if (!isRage) {
                notificationSystem.showNotification(playerName, message);
            } else {
                WindowsNotification.showWindowsNotification("RAGE", "2.10", ERROR);

                String textToCopy = "/mute " + playerName + " 2.10+";
                ClipboardUtil.copyToClipboard(textToCopy);
            }
        }
    }

    public static class MessageRecord {
        private String playerName;
        private long timestamp;
        private String message;

        public MessageRecord(String playerName, long timestamp, String message) {
            this.playerName = playerName;
            this.timestamp = timestamp;
            this.message = message;
        }

        public String getPlayerName() {
            return playerName;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public String getMessage() {
            return message;
        }
    }

    public static void toggleRageMode () {
        isRage = !isRage;
    }
    public static void togglLoyalMode () {
        isLoyal = !isLoyal;
    }
}