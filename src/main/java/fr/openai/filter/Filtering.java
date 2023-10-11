package fr.openai.filter;

import fr.openai.exec.Messages;
import fr.openai.filter.fixer.SbFix;
import fr.openai.exec.ClipboardUtil;
import fr.openai.notify.NotificationSystem;
import fr.openai.notify.WindowsNotification;

import static java.awt.TrayIcon.MessageType.ERROR;
import static java.awt.TrayIcon.MessageType.INFO;

public class Filtering {
    private final NotificationSystem notificationSystem;
    private final Filters filters;
    private static boolean rageModeEnabled;
    private static boolean loyalModeEnabled;

    public Filtering(NotificationSystem notificationSystem) {
        this.notificationSystem = notificationSystem;
        this.filters = new Filters();
        rageModeEnabled = false;
    }

    public void onFilter(String name, String line) {
        if ("Unknown".equalsIgnoreCase(name)) {
            return;
        }

        String message = Messages.getMessage(line);
        String playerName = NameFix.sbFix(name);

        if (message != null) {
            message = SbFix.fixMessage(message);

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
        if (loyalModeEnabled) {
            WindowsNotification.showWindowsNotification(getActionName(), loyalMessage, INFO);
            String textToCopy = "/warn " + playerName + " " + loyalAction;
            ClipboardUtil.copyToClipboard(textToCopy);
        } else if (!rageModeEnabled) {
            notificationSystem.showNotification(playerName, loyalMessage);
        } else {
            WindowsNotification.showWindowsNotification(getActionName(), message, ERROR);
            String textToCopy = "/mute " + playerName + " " + rageAction;
            ClipboardUtil.copyToClipboard(textToCopy);
        }
    }

    private String getActionName() {
        return loyalModeEnabled ? "LOYAL" : (rageModeEnabled ? "RAGE" : "");
    }

    public static void toggleRageMode() {
        rageModeEnabled = !rageModeEnabled;
    }

    public static boolean isRage() {
        return rageModeEnabled;
    }

    public static void toggleLoyalMode() {
        loyalModeEnabled = !loyalModeEnabled;
    }
}
