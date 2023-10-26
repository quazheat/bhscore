package fr.openai.filter;

import fr.openai.exec.Messages;
import fr.openai.exec.ClipboardUtil;
import fr.openai.notify.NotificationSystem;
import fr.openai.notify.WindowsNotification;

import static java.awt.TrayIcon.MessageType.ERROR;
import static java.awt.TrayIcon.MessageType.INFO;
import fr.openai.filter.fixer.Names; // Import the PlayerNameFormatter class


public class Filtering {
    private final NotificationSystem notificationSystem;
    private final Filters filters;

    public Filtering(NotificationSystem notificationSystem) {
        this.notificationSystem = notificationSystem;
        this.filters = new Filters();
    }

    public void onFilter(String name, String line) {

        String message = Messages.getMessage(line);
        String playerName = Names.formatPlayerName(name); //

        if ("Unknown".equalsIgnoreCase(name)) {
            System.out.println("VIOLATION: unknown name: " + line);
            return;
        }
        if (message != null) {
            if ("Unknown".equalsIgnoreCase(name)) {
                System.out.println("VIOLATION: unknown name: " + line);
                return;
            }

            if (filters.hasSwearing(message)) {
                handleViolation(playerName, message, "Не матерись", message, "2.");
            } else if (filters.hasManySymbols(message) || filters.hasLaugh(message) || filters.hasWFlood(message)) {
                handleViolation(playerName, message, "Не флуди", message, "2.10+");
            } else if (filters.hasCaps(message)) {
                handleViolation(playerName, message, "Не капси", message, "2.12+");
            }
        }
    }

    private void handleViolation(String playerName, String message, String loyalAction, String loyalMessage, String rageAction) {
        if (FilteringModeManager.isLoyalModeEnabled()) {
            WindowsNotification.showWindowsNotification(getActionName(), loyalMessage, INFO);
            String textToCopy = "/warn " + playerName + " " + loyalAction;
            ClipboardUtil.copyToClipboard(textToCopy);
        } else if (!FilteringModeManager.isRageModeEnabled()) {
            notificationSystem.showNotification(playerName, loyalMessage);
        } else {
            WindowsNotification.showWindowsNotification(getActionName(), message, ERROR);
            String textToCopy = "/mute " + playerName + " " + rageAction;
            ClipboardUtil.copyToClipboard(textToCopy);
        }
    }

    private String getActionName() {
        return FilteringModeManager.isLoyalModeEnabled() ? "LOYAL" : (FilteringModeManager.isRageModeEnabled() ? "RAGE" : "");
    }
}
