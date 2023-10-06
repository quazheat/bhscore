package fr.openai.filter;

import fr.openai.exec.Messages;
import fr.openai.filter.fixer.SbFix;
import fr.openai.notify.ClipboardUtil;
import fr.openai.notify.NotificationSystem;
import fr.openai.notify.WindowsNotification;

import java.awt.*;

import static java.awt.TrayIcon.MessageType.ERROR;

public class Filtering {
    private final NotificationSystem notificationSystem;
    private final Filters filters;
    static boolean isRage;

    public Filtering(NotificationSystem notificationSystem) {
        this.notificationSystem = notificationSystem;
        this.filters = new Filters();
        isRage = false;
    }

    public void onFilter(String name, String line) {
        if ("Unknown".equalsIgnoreCase(name)) return;
        String message = Messages.getMessage(line);
        String title = "RAGE";
        TrayIcon.MessageType type = ERROR;

        if (message != null) {
            message = SbFix.fixMessage(message);

            boolean violationDetected = false;

            if (filters.hasSwearing(message)) {
                if (!isRage) {
                    notificationSystem.showNotification(name, "Swearing");
                    violationDetected = true;
                } else {
                    WindowsNotification.showWindowsNotification(title, "Swearing", type);
                    String textToCopy = "/mute " + name + " 2.";
                    ClipboardUtil.copyToClipboard(textToCopy);
                }
            }

            if (!violationDetected && filters.hasManySymbols(message))
                if (!isRage) {
                    notificationSystem.showNotification(name, "Symbol flood");
                    violationDetected = true;
                } else {
                    WindowsNotification.showWindowsNotification(title, "2.10", type);
                    String textToCopy = "/mute " + name + " 2.10";
                    ClipboardUtil.copyToClipboard(textToCopy);
                }

            if (!violationDetected && filters.hasLaugh(message))
                if (!isRage) {
                    notificationSystem.showNotification(name, "Laugh flood");
                    violationDetected = true;
                } else {
                    WindowsNotification.showWindowsNotification(title, "2.10", type);
                    String textToCopy = "/mute " + name + " 2.10";
                    ClipboardUtil.copyToClipboard(textToCopy);
                }

            if (!violationDetected && filters.hasWFlood(message)) {
                if (!isRage) {
                    System.out.println("DETECTED: 5 одинаковых слов");
                    notificationSystem.showNotification(name, "Flood");
                    violationDetected = true;
                } else {
                    WindowsNotification.showWindowsNotification(title, "2.10", type);
                    String textToCopy = "/mute " + name + " 2.10";
                    ClipboardUtil.copyToClipboard(textToCopy);
                }
            }
            if (!violationDetected && filters.hasCaps(message))
                if (!isRage) {
                    notificationSystem.showNotification(name, "CAPS");
                } else {
                    WindowsNotification.showWindowsNotification(title, "2.12", type);
                    String textToCopy = "/mute " + name + " 2.12";
                    ClipboardUtil.copyToClipboard(textToCopy);
                }
        }
    }

    public static void toggleRageMode () {
        isRage = !isRage;
    }
}
