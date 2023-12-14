package fr.openai.n;

import fr.openai.ui.ny.cs;
import fr.openai.ui.ny.cd;
import fr.openai.ui.ny.cvf;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ae {
    private final List<MessagerNotification> notifications = new java.util.ArrayList<>();
    protected static final int MAX_NOTIFICATIONS = 10;
    private final NN heightManager = new NN();

    public void e(String senderName, String message) {
        if (notifications.size() < MAX_NOTIFICATIONS) {
            MessagerNotification notification = new MessagerNotification(senderName, message);
            notifications.add(notification);
            SwingUtilities.invokeLater(() -> displayMessagerNotification(notification));
        }
    }

    private void displayMessagerNotification(MessagerNotification notification) {
        int currentYBeforeNotification = heightManager.zxcq();

        cd notificationDialog = new cd();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        int x = (int) (screenSize.getWidth() / 2 - notificationDialog.getWidth() / 2);
        notificationDialog.setLocation(x, currentYBeforeNotification);

        JPanel notificationPanel = new JPanel();
        notificationPanel.setLayout(new BorderLayout());
        notificationPanel.setBackground(Color.DARK_GRAY);

        JLabel titleLabel = new JLabel(notification.title(), SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 18));
        titleLabel.setForeground(Color.BLACK);

        cvf messageField = new cvf(notification.message());
        cs closeButton = new cs("x", notificationDialog, heightManager);

        closeButton.addActionListener(e -> {
            notificationDialog.dispose();
            notifications.remove(notification);
        });

        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setBackground(Color.DARK_GRAY);
        closeButton.setPreferredSize(new Dimension((int) (0.14 * notificationDialog.getWidth()), 18));

        buttonPanel.add(closeButton, BorderLayout.EAST);
        notificationPanel.add(titleLabel, BorderLayout.NORTH);
        notificationPanel.add(messageField, BorderLayout.CENTER);
        notificationPanel.add(buttonPanel, BorderLayout.SOUTH);
        notificationDialog.add(notificationPanel);
        notificationDialog.setVisible(true);

        heightManager.xcz(20);
    }

    private record MessagerNotification(String senderName, String message) {
        public String title() {
            return senderName;
        }
    }

}
