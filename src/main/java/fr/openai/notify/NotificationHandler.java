package fr.openai.notify;


public class NotificationHandler {
    private final NotificationSystem notificationSystem;

    public NotificationHandler(NotificationSystem notificationSystem) {
        this.notificationSystem = notificationSystem;
    }

    public void showNotification(String playerName, String violation) {
        notificationSystem.showNotification(playerName, violation);
    }
}
