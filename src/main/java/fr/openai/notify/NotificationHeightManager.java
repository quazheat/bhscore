package fr.openai.notify;

import java.util.ArrayList;
import java.util.List;

public class NotificationHeightManager {
    private static final int MAX_NOTIFICATIONS = 12;
    private int currentY = 50;
    private final List<Integer> notificationHeights = new ArrayList<>(MAX_NOTIFICATIONS);

    public int getNotificationY() {
        int currentYBeforeNotification = currentY;

        for (int i = notificationHeights.size() - 1; i >= 0; i--) {
            if (notificationHeights.get(i) > 0) {
                currentYBeforeNotification -= notificationHeights.get(i);
                break;
            }
        }

        return currentYBeforeNotification;
    }

    public void updateCurrentY(int value) {
        currentY += value;
    }

    public void setNotificationHeight(int index, int height) {
        if (index >= 0 && index < MAX_NOTIFICATIONS) {
            if (index >= notificationHeights.size()) {
                notificationHeights.add(height);
            } else {
                notificationHeights.set(index, height);
            }
        }
    }

    public void setCurrentY(int y) {
        currentY = y;
    }
}
