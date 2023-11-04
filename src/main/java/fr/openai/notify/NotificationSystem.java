package fr.openai.notify;

import fr.openai.ui.customui.CustomClose;
import fr.openai.ui.customui.CustomDialog;
import fr.openai.ui.customui.CustomField;
import fr.openai.filter.NameFix;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.*;
import java.util.List;

public class NotificationSystem {
    NameFix nameFixer = new NameFix();
    private final List<Notification> notifications = new ArrayList<>();
    static final int MAX_NOTIFICATIONS = 10;
    private final NotificationHeightManager heightManager = new NotificationHeightManager();

    public void showNotification(String playerName, String violation) {
        if (notifications.size() < MAX_NOTIFICATIONS) {
            Notification notification = new Notification(playerName, violation);
            notifications.add(notification);
            System.out.println("Trying to showNotification");
            SwingUtilities.invokeLater(() -> displayNotification(notification));
        }
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

        closeButton.addActionListener(e -> {
            notificationDialog.dispose();
            notifications.remove(notification);
        });

        JButton muteButton = new JButton("Mute");
        JButton warnButton = new JButton("Warn");
        muteButton.setBackground(new Color(29, 29, 29));
        muteButton.setForeground(Color.WHITE);
        muteButton.setFocusPainted(false);

        warnButton.setBackground(new Color(29, 29, 29));
        warnButton.setForeground(Color.WHITE);
        warnButton.setFocusPainted(false);

        muteButton.addActionListener(e -> {
            String playerName = nameFixer.cscFix(playerNameLabel.getText());
            String command = "/mute " + playerName + "  ";
            heightManager.updateCurrentY(-20);
            notificationDialog.dispose();
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(new StringSelection(command), null);
            notifications.remove(notification);
        });

        warnButton.addActionListener(e -> {
            String playerName = nameFixer.cscFix(playerNameLabel.getText());
            String command = "/warn " + playerName + "  ";
            heightManager.updateCurrentY(-20);
            notificationDialog.dispose();
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(new StringSelection(command), null);
            notifications.remove(notification);
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
}
