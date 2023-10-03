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
    private final Queue<NotificationDialogPair> notificationPairs = new LinkedList<>();
    private static final int MAX_NOTIFICATIONS = 12;
    private final NotificationHeightManager heightManager = new NotificationHeightManager();

    public void showNotification(String playerName, String violation) {
        int currentYBeforeNotification = heightManager.getNotificationY();

        while (notificationPairs.size() >= MAX_NOTIFICATIONS) {
            NotificationDialogPair removedPair = notificationPairs.poll();
            heightManager.setNotificationHeight(notificationPairs.size(), removedPair.getDialog().getHeight());
        }

        Notification notification = new Notification(playerName, violation);
        CustomDialog notificationDialog = new CustomDialog();

        // Create a pair to associate the notification with the dialog
        NotificationDialogPair pair = new NotificationDialogPair(notification, notificationDialog);
        notificationPairs.offer(pair);

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
        CustomClose closeButton = new CustomClose("x", notificationDialog, this, heightManager);

        closeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (notificationPairs.contains(pair)) {
                    notificationPairs.remove(pair);
                    notificationDialog.dispose();
                    heightManager.updateCurrentY(-20);
                }
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
                    ClipboardUtil.copyToClipboard(command);
                    if (notificationPairs.contains(pair)) {
                        notificationPairs.remove(pair);
                        notificationDialog.dispose();
                        heightManager.updateCurrentY(-20);
                    }
                }
            }
        });

        warnButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    String playerName = NameFix.sbFix(playerNameLabel.getText());
                    String command = "/warn " + playerName + "  ";
                    ClipboardUtil.copyToClipboard(command);
                    notificationPairs.remove(pair);
                    notificationDialog.dispose();
                    heightManager.updateCurrentY(-20);
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
        heightManager.updateCurrentY(20);
    }

    private static class NotificationDialogPair {
        private final Notification notification;
        private final CustomDialog dialog;

        public NotificationDialogPair(Notification notification, CustomDialog dialog) {
            this.notification = notification;
            this.dialog = dialog;
        }

        public Notification getNotification() {
            return notification;
        }

        public CustomDialog getDialog() {
            return dialog;
        }
    }
}
