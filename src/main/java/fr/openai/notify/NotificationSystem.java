package fr.openai.notify;

import fr.openai.database.customui.CustomClose;
import fr.openai.database.customui.CustomDialog;
import fr.openai.database.customui.CustomField;
import fr.openai.handler.filter.NameFix;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.Queue;

public class NotificationSystem {
    private final Queue<Notification> notifications = new LinkedList<>();
    private static final int MAX_NOTIFICATIONS = 25;
    private int currentY = 50;

    public void showNotification(String playerName, String violation) {
        int currentYBeforeNotification = getCurrentY();

        while (notifications.size() >= MAX_NOTIFICATIONS) {
            notifications.poll();
            currentYBeforeNotification -= 20;
        }

        Notification notification = new Notification(playerName, violation);
        notifications.offer(notification);

        CustomDialog notificationDialog = new CustomDialog();

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) (screenSize.getWidth() - notificationDialog.getWidth());
        int y = (int) (screenSize.getHeight() - notificationDialog.getHeight() - currentYBeforeNotification);
        notificationDialog.setLocation(x, y);

        JPanel notificationPanel = new JPanel();
        notificationPanel.setLayout(new BorderLayout());
        notificationPanel.setBackground(Color.DARK_GRAY);

        JLabel playerNameLabel = new JLabel(playerName, SwingConstants.CENTER);
        playerNameLabel.setFont(new Font("Serif", Font.BOLD, 18));
        playerNameLabel.setForeground(Color.BLACK);

        CustomField violationField = new CustomField(violation);
        CustomClose closeButton = new CustomClose("X", notificationDialog, this);

        closeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (notifications.contains(notificationDialog)) {
                    notifications.remove(notificationDialog);
                    notificationDialog.dispose();
                    updateCurrentY(-20);
                }
            }
        });

        JButton skyblockButton = new JButton("mute\\warn");
        skyblockButton.setBackground(new Color(29, 29, 29));
        skyblockButton.setForeground(Color.WHITE);
        skyblockButton.setFocusPainted(false);

        skyblockButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    String playerName = NameFix.sbFix(playerNameLabel.getText());
                    String command = "/mute " + playerName + "  ";
                    ClipboardUtil.copyToClipboard(command);
                    notifications.remove(notificationDialog);
                    notificationDialog.dispose();
                    updateCurrentY(-20);
                } else if (e.getButton() == MouseEvent.BUTTON3) {
                    String playerName = NameFix.sbFix(playerNameLabel.getText());
                    String command = "/warn " + playerName + "  ";
                    ClipboardUtil.copyToClipboard(command);
                    notifications.remove(notificationDialog);
                    notificationDialog.dispose();
                    updateCurrentY(-20);
                }
            }
        });

        JPanel buttonPanel = new JPanel(new BorderLayout());

        buttonPanel.add(skyblockButton, BorderLayout.CENTER);
        buttonPanel.add(closeButton, BorderLayout.EAST);
        notificationPanel.add(playerNameLabel, BorderLayout.NORTH);
        notificationPanel.add(violationField, BorderLayout.CENTER);
        notificationPanel.add(buttonPanel, BorderLayout.SOUTH);
        notificationDialog.add(notificationPanel);
        notificationDialog.setVisible(true);
        updateCurrentY(20);
    }

    public void updateCurrentY(int value) {
        currentY += value;
    }

    private int getCurrentY() {
        return currentY;
    }
}