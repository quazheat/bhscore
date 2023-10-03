package fr.openai.notify;

public class NotificationInfo {
    private final int number;
    private final int height;
    private final int totalNotifications;

    public NotificationInfo(int number, int height, int totalNotifications) {
        this.number = number;
        this.height = height;
        this.totalNotifications = totalNotifications;
    }

    public int getNumber() {
        return number;
    }

    public int getHeight() {
        return height;
    }

    public int getTotalNotifications() {
        return totalNotifications;
    }
}
