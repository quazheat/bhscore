package fr.openai.filter;

import fr.openai.notify.NotificationSystem;

import java.util.ArrayList;
import java.util.List;


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
        MessageRecord newRecord = new MessageRecord(playerName, currentTime, message);
        messageRecords.add(newRecord);
    }

    private void handleDuplicateMessages(String playerName, String message) {
        if ("Unknown".equalsIgnoreCase(playerName)) {
            return;
        }
        handleViolation(playerName, message, "/warn " + playerName + " Не флуди", "[3msg] " + message, "2.10+");
    }

    private void removeDuplicateMessages(String playerName, String message) {
        messageRecords.removeIf(record -> record.playerName().equals(playerName) && record.message().equals(message));
    }

}
