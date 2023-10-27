package fr.openai.filter;

import fr.openai.exec.Messages;
import fr.openai.exec.ClipboardUtil;
import fr.openai.notify.NotificationSystem;
import fr.openai.notify.WindowsNotification;

import static java.awt.TrayIcon.MessageType.ERROR;
import static java.awt.TrayIcon.MessageType.INFO;
import fr.openai.filter.fixer.Names;

public class Filtering {
    private final NotificationSystem notificationSystem;
    private final Filters filters;

    public Filtering(NotificationSystem notificationSystem) {
        this.notificationSystem = notificationSystem;
        this.filters = new Filters();
    }
    public void onFilter(String name, String line) {
        String message = Messages.getMessage(line);
        String playerName = Names.formatPlayerName(name);

        if (message == null) {
            return;
        }

        if ("Unknown".equalsIgnoreCase(name)) {
            handleUnknownNameViolation(line);
            return;
        }

        if (filters.hasSwearing(message)) {
            handleViolation(playerName, message, "Не матерись", message, "2.");
            return;
        }

        if (filters.hasManySymbols(message) || filters.hasLaugh(message) || filters.hasWFlood(message)) {
            handleViolation(playerName, message, "Не флуди", message, "2.10+");
            return;
        }

        if (filters.hasCaps(message)) {
            handleViolation(playerName, message, "Не капси", message, "2.12+");
        }
    }

    private void handleUnknownNameViolation(String line) {
        System.out.println("VIOLATION: unknown name: " + line);
    }

    private void handleViolation(String playerName, String message, String loyalAction, String loyalMessage, String rageAction) {
        if (FilteringModeManager.isLoyalModeEnabled()) {
            showLoyalNotification(loyalMessage);
            copyToClipboard("/warn " + playerName + " " + loyalAction);
            return;
        }

        if (!FilteringModeManager.isRageModeEnabled()) {
            notificationSystem.showNotification(playerName, loyalMessage);
            return;
        }
        showRageNotification(message);
        copyToClipboard("/mute " + playerName + " " + rageAction);
    }


    private void showLoyalNotification(String message) {
        WindowsNotification.showWindowsNotification(getActionName(), message, INFO);
    }

    private void showRageNotification(String message) {
        WindowsNotification.showWindowsNotification(getActionName(), message, ERROR);
    }

    private void copyToClipboard(String text) {
        ClipboardUtil.copyToClipboard(text);
    }

    private String getActionName() {
        if (FilteringModeManager.isLoyalModeEnabled()) {
            return "LOYAL";
        } else if (FilteringModeManager.isRageModeEnabled()) {
            return "RAGE";
        } else {
            return "";
        }
    }
}
