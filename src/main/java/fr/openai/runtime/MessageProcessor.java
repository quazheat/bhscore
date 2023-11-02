package fr.openai.runtime;

import fr.openai.exec.ClipboardUtil;
import fr.openai.exec.PasteUtil;
import fr.openai.filter.FilteringModeManager;
import fr.openai.notify.NotificationSystem;
import fr.openai.notify.WindowsNotification;

import java.util.ArrayList;
import java.util.List;

import static java.awt.TrayIcon.MessageType.ERROR;
import static java.awt.TrayIcon.MessageType.INFO;

public class MessageProcessor {
    private final NotificationSystem notificationSystem;
    private final List<MessageRecord> messageRecords;

    public MessageProcessor(NotificationSystem notificationSystem) {
        this.messageRecords = new ArrayList<>();
        this.notificationSystem = notificationSystem;
    }

    public void processMessage(String playerName, String message) {
        long currentTime = System.currentTimeMillis() / 1000;
        removeOldRecords(currentTime);

        int duplicateCount = countDuplicates(playerName, message);
        addNewRecord(playerName, currentTime, message);

        if (duplicateCount >= 2) {
            handleDuplicateMessages(playerName, message);
        }
    }

    private void removeOldRecords(long currentTime) {
        // Remove records older than 60 seconds
        messageRecords.removeIf(record -> currentTime - record.timestamp() >= 60);
    }

    private int countDuplicates(String playerName, String message) {
        // Check for duplicate messages from the same player within a minute
        int duplicateCount = 0;
        for (MessageRecord record : messageRecords) {
            if (record.playerName().equals(playerName) && record.message().equals(message)) {
                duplicateCount++;
            }
        }
        return duplicateCount;
    }

    private void addNewRecord(String playerName, long currentTime, String message) {
        // Add a new message record
        MessageRecord newRecord = new MessageRecord(playerName, currentTime, message);
        messageRecords.add(newRecord);
    }

    private void handleDuplicateMessages(String playerName, String message) {
        if ("Unknown".equalsIgnoreCase(playerName)) {
            System.out.println("VIOLATION: unknown name: " + message);
            return;
        }

        if (FilteringModeManager.isLoyalModeEnabled()) {
            WindowsNotification.showWindowsNotification("LOYAL", "2.10", INFO);
            String textToCopyF = "/warn " + playerName + " Не флуди";
            ClipboardUtil.copyToClipboard(textToCopyF);
            PasteUtil.pasteFromClipboard(); // Paste the text from the clipboard
            return;
        }

        if (!FilteringModeManager.isRageModeEnabled()) {
            notificationSystem.showNotification(playerName, "[3 msg] " + message);
            return;
        }

        WindowsNotification.showWindowsNotification("RAGE", "2.10", ERROR);
        String textToCopy = "/mute " + playerName + " 2.10+";
        ClipboardUtil.copyToClipboard(textToCopy);
        PasteUtil.pasteFromClipboard(); // Paste the text from the clipboard
    }
}
