package fr.openai.handler.filter;

import fr.openai.exec.Messages;
import fr.openai.handler.filter.fixer.SbFix;
import fr.openai.notify.NotificationSystem;

public class Filtering {
    private final NotificationSystem notificationSystem;
    private final Filters filters;

    public Filtering(NotificationSystem notificationSystem) {
        this.notificationSystem = notificationSystem;
        this.filters = new Filters();
    }

    public void onFilter(String name, String line) {
        if ("Unknown".equalsIgnoreCase(name)) return;
        String message = Messages.getMessage(line);

        if (message != null) {
            message = SbFix.fixMessage(message);

            boolean violationDetected = false;

            if (filters.hasSwearing(message)) {
                notificationSystem.showNotification(name, "Swearing");
                violationDetected = true;
            }

            if (!violationDetected && filters.hasManySymbols(message)) {
                notificationSystem.showNotification(name, "Symbol flood");
                violationDetected = true;
            }

            if (!violationDetected && filters.hasLaugh(message)) {
                notificationSystem.showNotification(name, "Laugh flood");
                violationDetected = true;
            }

            if (!violationDetected && filters.hasWFlood(message)) {
                System.out.println("DETECTED: 5 одинаковых слов");
                notificationSystem.showNotification(name, "Flood");
                violationDetected = true;
            }

            if (!violationDetected && filters.hasCaps(message)) {
                notificationSystem.showNotification(name, "CAPS");
            }
        } else {
            System.out.println("Filtering: No message received");
        }

    }
}

