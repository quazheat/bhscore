package fr.openai.notify;

import java.util.ArrayList;
import java.util.List;

import static fr.openai.notify.NotificationSystem.MAX_NOTIFICATIONS;

public class NotificationHeightManager {
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

                return;
            }

            notificationHeights.set(index, height);
        }
    }

    public void setCurrentY(int y) {
        currentY = y;
    }
}
