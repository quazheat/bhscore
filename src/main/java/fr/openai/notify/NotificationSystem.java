package fr.openai.notify;

import fr.openai.database.customui.CustomClose;
import fr.openai.database.customui.CustomDialog;
import fr.openai.database.customui.CustomField;
import fr.openai.filter.NameFix;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.List; // Добавляем импорт для java.util.List
import java.util.concurrent.Semaphore; // Импортируем Semaphore

public class NotificationSystem {
    private final Semaphore semaphore = new Semaphore(1); // Создаем семафор с одним разрешением

    private final Queue<Notification> notifications = new LinkedList<>();
    private final List<CustomDialog> activeNotifications = new ArrayList<>(); // Создаем список для отслеживания активных уведомлений

    private static final int MAX_NOTIFICATIONS = 5;
    private final NotificationHeightManager heightManager = new NotificationHeightManager();

    public void showNotification(String playerName, String violation) {
        Notification notification = new Notification(playerName, violation);
        notifications.offer(notification);

        if (notifications.size() > MAX_NOTIFICATIONS) {
            notifications.clear(); // Удаляем все уведомления при достижении лимита
            heightManager.setCurrentY(50); // Устанавливаем высоту на базовую (50)
            closeAllNotifications(); // Закрываем все активные уведомления
            System.out.println("closeAllNotifications");
        }

        SwingUtilities.invokeLater(() -> displayNotification(notification));
    }

    private void displayNotification(Notification notification) {
        System.out.println("NotificationSystem");
        int currentYBeforeNotification = heightManager.getNotificationY();

        CustomDialog notificationDialog = new CustomDialog();

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) (screenSize.getWidth() - notificationDialog.getWidth());
        int y = (int) (screenSize.getHeight() - notificationDialog.getHeight() - currentYBeforeNotification);
        notificationDialog.setLocation(x, y);

        JPanel notificationPanel = new JPanel();
        notificationPanel.setLayout(new BorderLayout());
        notificationPanel.setBackground(Color.DARK_GRAY);

        JLabel playerNameLabel = new JLabel(notification.playerName(), SwingConstants.CENTER);
        playerNameLabel.setFont(new Font("Serif", Font.BOLD, 18));
        playerNameLabel.setForeground(Color.BLACK);

        CustomField violationField = new CustomField(notification.violation());
        CustomClose closeButton = new CustomClose("x", notificationDialog, this, heightManager);

        closeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                notificationDialog.dispose();
                // Удаляем уведомление из списка активных уведомлений
                activeNotifications.remove(notificationDialog);
            }
        });

        JButton muteButton = new JButton("Mute");
        JButton warnButton = new JButton("Warn");
        muteButton.setBackground(new Color(29, 29, 29));
        muteButton.setForeground(Color.WHITE);
        muteButton.setFocusPainted(false);

        warnButton.setBackground(new Color(29, 29, 29));
        warnButton.setForeground(Color.WHITE);
        warnButton.setFocusPainted(false);

        muteButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    String playerName = NameFix.sbFix(playerNameLabel.getText());
                    String command = "/mute " + playerName + "  ";

                    try {
                        semaphore.acquire(); // Захватываем разрешение семафора
                        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                        clipboard.setContents(new StringSelection(command), null);
                        semaphore.release(); // Освобождаем разрешение семафора
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }

                    heightManager.updateCurrentY(-20);

                    // Закрыть уведомление
                    notificationDialog.dispose();
                    activeNotifications.remove(notificationDialog);
                }
            }
        });

        warnButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    String playerName = NameFix.sbFix(playerNameLabel.getText());
                    String command = "/warn " + playerName + "  ";

                    try {
                        semaphore.acquire(); // Захватываем разрешение семафора
                        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                        clipboard.setContents(new StringSelection(command), null);
                        semaphore.release(); // Освобождаем разрешение семафора
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }

                    heightManager.updateCurrentY(-20);

                    // Закрыть уведомление
                    notificationDialog.dispose();
                    activeNotifications.remove(notificationDialog);
                }
            }
        });


        JPanel buttonPanel = new JPanel(new BorderLayout());
        muteButton.setPreferredSize(new Dimension((int) (0.43 * notificationDialog.getWidth()), 18));
        warnButton.setPreferredSize(new Dimension((int) (0.43 * notificationDialog.getWidth()), 18));
        closeButton.setPreferredSize(new Dimension((int) (0.14 * notificationDialog.getWidth()), 18));

        buttonPanel.add(muteButton, BorderLayout.WEST);
        buttonPanel.add(warnButton, BorderLayout.CENTER);
        buttonPanel.add(closeButton, BorderLayout.EAST);
        notificationPanel.add(playerNameLabel, BorderLayout.NORTH);
        notificationPanel.add(violationField, BorderLayout.CENTER);
        notificationPanel.add(buttonPanel, BorderLayout.SOUTH);
        notificationDialog.add(notificationPanel);
        notificationDialog.setVisible(true);

        // Добавляем отображенное уведомление в список активных уведомлений
        activeNotifications.add(notificationDialog);

        // Проверяем, если количество активных уведомлений больше максимального, то закрываем первое
        if (activeNotifications.size() > MAX_NOTIFICATIONS) {
            CustomDialog firstNotification = activeNotifications.get(0);
            firstNotification.dispose();
            activeNotifications.remove(firstNotification);
        }

        heightManager.updateCurrentY(20);
    }


    // Метод для закрытия всех активных уведомлений
    private void closeAllNotifications() {
        for (CustomDialog notificationDialog : activeNotifications) {
            notificationDialog.dispose();
        }
        activeNotifications.clear();
    }
}
