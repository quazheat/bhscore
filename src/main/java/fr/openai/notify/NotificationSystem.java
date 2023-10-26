package fr.openai.notify;

import fr.openai.database.customui.CustomClose;
import fr.openai.database.customui.CustomDialog;
import fr.openai.database.customui.CustomField;
import fr.openai.filter.NameFix;
import fr.openai.filter.fixer.Names;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class NotificationSystem {

    private final Queue<Notification> notifications = new ArrayDeque<>();
    private final List<CustomDialog> activeNotifications = new ArrayList<>(); // Create a list to track active notifications

    private static final int MAX_NOTIFICATIONS = 5;
    private final NotificationHeightManager heightManager = new NotificationHeightManager();

    public void showNotification(String playerName, String violation) {
        Notification notification = new Notification(playerName, violation);
        notifications.offer(notification);

        if (notifications.size() > MAX_NOTIFICATIONS) {
            notifications.clear(); // Clear all notifications when the limit is reached
            heightManager.setCurrentY(50); // Reset the height to the base (50)
            closeAllNotifications(); // Close all active notifications
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

        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                notificationDialog.dispose();
                // Remove the notification from the list of active notifications
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

        muteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String playerName = NameFix.sbFix(playerNameLabel.getText());
                String command = "/mute " + playerName + "  ";
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(new StringSelection(command), null);
                heightManager.updateCurrentY(-20);
                activeNotifications.remove(notificationDialog);
                notificationDialog.dispose();
            }
        });

        warnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String playerName = NameFix.sbFix(playerNameLabel.getText());
                String command = "/warn " + playerName + "  ";
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(new StringSelection(command), null);
                heightManager.updateCurrentY(-20);
                activeNotifications.remove(notificationDialog);
                notificationDialog.dispose();
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

        // Add the displayed notification to the list of active notifications
        activeNotifications.add(notificationDialog);

        // Check if the number of active notifications exceeds the maximum, close the first one
        if (activeNotifications.size() > MAX_NOTIFICATIONS) {
            CustomDialog firstNotification = activeNotifications.get(0);
            firstNotification.dispose();
            activeNotifications.remove(firstNotification);
        }

        heightManager.updateCurrentY(20);
    }

    // Method to close all active notifications
    private void closeAllNotifications() {
        for (CustomDialog notificationDialog : activeNotifications) {
            notificationDialog.dispose();
        }
        activeNotifications.clear();
    }
}
