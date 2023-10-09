package fr.openai.filter;

import fr.openai.exec.Messages;
import fr.openai.filter.fixer.SbFix;
import fr.openai.notify.ClipboardUtil;
import fr.openai.notify.NotificationSystem;
import fr.openai.notify.WindowsNotification;


import static java.awt.TrayIcon.MessageType.ERROR;
import static java.awt.TrayIcon.MessageType.INFO;

public class Filtering {
    private final NotificationSystem notificationSystem;
    private final Filters filters;
    static boolean isRage;
    static boolean isLoyal;

    public Filtering(NotificationSystem notificationSystem) {
        this.notificationSystem = notificationSystem;
        this.filters = new Filters();
        isRage = false;
    }

    public void onFilter(String name, String line) {
        if ("Unknown".equalsIgnoreCase(name)) return;
        String message = Messages.getMessage(line);
        String messageFlood = "Symbol flood";
        String textToCopyF = "/warn " + name + " Не флуди";


        if (message != null) {
            message = SbFix.fixMessage(message);

            boolean violationDetected = false;

            if (filters.hasSwearing(message)) {
                if (isLoyal){
                    WindowsNotification.showWindowsNotification("LOYAL", message, INFO);
                    String textToCopy = "/warn " + name + " Не матерись";
                    ClipboardUtil.copyToClipboard(textToCopy);
                } else if (!isRage) {
                    notificationSystem.showNotification(name, "Swearing");
                    violationDetected = true;
                } else {
                    WindowsNotification.showWindowsNotification("RAGE", message, ERROR);
                    String textToCopy = "/mute " + name + " 2.";
                    ClipboardUtil.copyToClipboard(textToCopy);
                }
            }

            if (!violationDetected && filters.hasManySymbols(message))
                if (isLoyal){
                    WindowsNotification.showWindowsNotification("LOYAL", "2.10", INFO);
                    ClipboardUtil.copyToClipboard(textToCopyF);
                } else if (!isRage) {
                    notificationSystem.showNotification(name, message);
                    violationDetected = true;
                } else {
                    WindowsNotification.showWindowsNotification("RAGE", "2.10", ERROR);
                    ClipboardUtil.copyToClipboard(textToCopyF);
                }

            if (!violationDetected && filters.hasLaugh(message))
                if (isLoyal){
                    WindowsNotification.showWindowsNotification("LOYAL", "2.10", INFO);
                    ClipboardUtil.copyToClipboard(textToCopyF);
                } else if (!isRage) {
                    notificationSystem.showNotification(name, "Laugh flood");
                    violationDetected = true;
                } else {
                    WindowsNotification.showWindowsNotification("RAGE", "2.10", ERROR);
                    String textToCopy = "/mute " + name + " 2.10";
                    ClipboardUtil.copyToClipboard(textToCopy);
                }

            if (!violationDetected && filters.hasWFlood(message)) {
                if (isLoyal){
                    WindowsNotification.showWindowsNotification("LOYAL", "2.10", INFO);
                    ClipboardUtil.copyToClipboard(textToCopyF);
                } else if (!isRage) {
                    notificationSystem.showNotification(name, "Flood");
                    violationDetected = true;
                } else {
                    WindowsNotification.showWindowsNotification("RAGE", "2.10", ERROR);
                    ClipboardUtil.copyToClipboard(textToCopyF);
                }
            }
            if (!violationDetected && filters.hasCaps(message))
                if (isLoyal){
                    WindowsNotification.showWindowsNotification("LOYAL", "2.10", INFO);
                    String textToCopy = "/warn " + name + " Не капси";
                    ClipboardUtil.copyToClipboard(textToCopy);
                } else if (!isRage) {
                    notificationSystem.showNotification(name, "CAPS");
                } else {
                    WindowsNotification.showWindowsNotification("RAGE", "2.12", ERROR);
                    String textToCopy = "/mute " + name + " 2.12";
                    ClipboardUtil.copyToClipboard(textToCopy);
                }
        }
    }

    public static void toggleRageMode () {
        isRage = !isRage;
    }
    public static boolean isRage(){
        return isRage;
    }
    public static void togglLoyalMode () {
        isLoyal = !isLoyal;
    }
}
