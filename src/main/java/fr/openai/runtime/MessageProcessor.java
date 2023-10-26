package fr.openai.runtime;

import fr.openai.exec.ClipboardUtil;
import fr.openai.filter.FilteringModeManager;
import fr.openai.notify.NotificationSystem;
import fr.openai.notify.WindowsNotification;

import java.util.ArrayList;
import java.util.List;

import static java.awt.TrayIcon.MessageType.ERROR;
import static java.awt.TrayIcon.MessageType.INFO;

public class MessageProcessor {
    private final NotificationSystem notificationSystem;
    private List<MessageRecord> messageRecords;

    public MessageProcessor(NotificationSystem notificationSystem) {
        this.messageRecords = new ArrayList<>();
        this.notificationSystem = notificationSystem;
    }

    public void processMessage(String playerName, String message) {
        long currentTime = System.currentTimeMillis() / 1000;
        // Удаляем записи старше 60 секунд
        messageRecords.removeIf(record -> currentTime - record.timestamp() >= 60);

        // Проверяем наличие одинаковых сообщений от игрока в течение минуты
        int duplicateCount = 0;
        for (MessageRecord record : messageRecords) {
            if (record.playerName().equals(playerName) && record.message().equals(message)) {
                duplicateCount++;
            }
        }

        // Добавляем новую запись
        MessageRecord newRecord = new MessageRecord(playerName, currentTime, message);
        messageRecords.add(newRecord);

        if (duplicateCount >= 2) {
            if ("Unknown".equalsIgnoreCase(playerName)) {
                System.out.println("VIOLATION: unknown name: " + message);
                return;
            }
            if (FilteringModeManager.isLoyalModeEnabled()) {
                WindowsNotification.showWindowsNotification("LOYAL", "2.10", INFO);
                String textToCopyF = "/warn " + playerName + " Не флуди";
                ClipboardUtil.copyToClipboard(textToCopyF);
            } else if (!FilteringModeManager.isRageModeEnabled()) {
                notificationSystem.showNotification(playerName, message);
            } else {
                WindowsNotification.showWindowsNotification("RAGE", "2.10", ERROR);
                String textToCopy = "/mute " + playerName + " 2.10+";
                ClipboardUtil.copyToClipboard(textToCopy);
            }
        }
    }
}
