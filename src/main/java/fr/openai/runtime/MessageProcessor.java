package fr.openai.runtime;

import fr.openai.exec.ClipboardUtil;
import fr.openai.filter.FilteringModeManager;
import fr.openai.filter.JustAnotherFilter;
import fr.openai.filter.ViolationHandler;
import fr.openai.notify.NotificationSystem;
import fr.openai.notify.WindowsNotification;

import java.util.ArrayList;
import java.util.List;

import static java.awt.TrayIcon.MessageType.ERROR;
import static java.awt.TrayIcon.MessageType.INFO;

public class MessageProcessor extends ViolationHandler {
    private final List<MessageRecord> messageRecords;

    public MessageProcessor(NotificationSystem notificationSystem) {
        super(notificationSystem);
        this.messageRecords = new ArrayList<>();
    }

    public void processMessage(String name, String message) {
        JustAnotherFilter justAnotherFilter = new JustAnotherFilter(name, message);
        if (justAnotherFilter.shouldSkip()) {
            return;
        }
        long currentTime = System.currentTimeMillis() / 1000;
        removeOldRecords(currentTime);

        int duplicateCount = countDuplicates(name, message);
        addNewRecord(name, currentTime, message);

        if (duplicateCount == 2) {
            handleDuplicateMessages(name, message);
            removeDuplicateMessages(name, message);
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
        System.out.println(newRecord + " FLLODDD RECORDED");
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
            pasteUtil.pasteFromClipboard(); // Paste the text from the clipboard
            System.out.println(textToCopyF);
            return;
        }

        if (!FilteringModeManager.isRageModeEnabled()) {
            notificationSystem.showNotification(playerName, "[3 msg] " + message);
            return;
        }

        WindowsNotification.showWindowsNotification("RAGE", "2.10", ERROR);
        String textToCopy = "/mute " + playerName + " 2.10+";
        ClipboardUtil.copyToClipboard(textToCopy);
        pasteUtil.pasteFromClipboard(); // Paste the text from the clipboard
        System.out.println(textToCopy);
    }

    private void removeDuplicateMessages(String playerName, String message) {
        messageRecords.removeIf(record -> record.playerName().equals(playerName) && record.message().equals(message));
    }

}
