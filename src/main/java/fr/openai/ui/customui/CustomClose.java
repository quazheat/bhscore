package fr.openai.ui.customui;

import fr.openai.notify.NotificationHeightManager;
import fr.openai.notify.NotificationSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomClose extends JButton {
    private final NotificationHeightManager heightManager;
    private final JDialog notificationDialog;
    private final NotificationSystem notificationSystem;

    public CustomClose(String text, JDialog dialog, NotificationSystem system, NotificationHeightManager heightManager) {
        super(text);
        this.notificationDialog = dialog;
        this.notificationSystem = system;
        this.heightManager = heightManager; // Передаем NotificationHeightManager в конструктор

        setupCustomButton();
        addActionListener(new CloseButtonListener());
    }

    private void setupCustomButton() {
        setBackground(Color.RED);
        setForeground(Color.WHITE);
    }

    private class CloseButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (notificationDialog != null) {
                notificationDialog.dispose();
                heightManager.updateCurrentY(-20); // Обновляем currentY в NotificationHeightManager
            }
        }
    }
}
