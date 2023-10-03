package fr.openai.handler.filter;

import fr.openai.exec.Messages;
import fr.openai.handler.filter.fixer.SbFix;
import fr.openai.notify.NotificationHandler;
import fr.openai.notify.NotificationSystem;

public class Filtering {
    private final NotificationHandler notificationHandler;
    private final SwearingFilter swearingFilter;
    private final Filters filters;

    public Filtering() {
        this.notificationHandler = new NotificationHandler(new NotificationSystem());
        this.swearingFilter = new SwearingFilter();
        this.filters = new Filters();
    }

    public void onFilter(String name, String line) {
        if ("Unknown".equalsIgnoreCase(name)) return;
        String message = Messages.getMessage(line);

        if (message != null) {
            message = SbFix.fixMessage(message);

            boolean violationDetected = false;

            if (swearingFilter.hasSwearing(message)) {
                swearingFilter.showNotification(name);
                violationDetected = true;
            }

            if (!violationDetected && filters.hasManySymbols(message)) {
                notificationHandler.showNotification(name, "Symbol flood");
                violationDetected = true;
            }

            if (!violationDetected && filters.hasLaugh(message)) {
                notificationHandler.showNotification(name, "Laugh flood");
                violationDetected = true;
            }

            if (!violationDetected && filters.hasWFlood(message)) {
                System.out.println("DETECTED: 5 одинаковых слов");
                notificationHandler.showNotification(name, "Flood");
                violationDetected = true;
            }

            if (!violationDetected && filters.hasCaps(message)) {
                notificationHandler.showNotification(name, "CAPS");
            }
        } else {
            System.out.println("Filtering: No message received");
        }
    }
}
