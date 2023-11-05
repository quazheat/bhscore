package fr.openai.ui.customui;

import fr.openai.notify.NotificationHeightManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomClose extends JButton {
    private final NotificationHeightManager heightManager;
    private final JDialog notificationDialog;

    public CustomClose(String text, JDialog dialog, NotificationHeightManager heightManager) {
        super(text);
        this.notificationDialog = dialog;
        this.heightManager = heightManager;

        setupCustomButton();
        addActionListener(new CloseButtonListener());
    }

    private void setupCustomButton() {
        setBackground(Color.RED);
        setForeground(Color.WHITE);
    }

    private class CloseButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (notificationDialog != null) {
                notificationDialog.dispose();
                heightManager.updateCurrentY(-20); // Обновляем currentY в NotificationHeightManager
            }
        }
    }
}
