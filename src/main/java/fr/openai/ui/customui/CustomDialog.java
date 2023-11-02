package fr.openai.ui.customui;

import javax.swing.*;
import java.awt.*;

public class CustomDialog extends JDialog {
    public CustomDialog() {
        setupCustomDialog();
    }

    private void setupCustomDialog() {
        setSize(300, 100);
        setLayout(new BorderLayout());
        setUndecorated(true);
        setAlwaysOnTop(true);
        setFocusableWindowState(false);
        setLocationRelativeTo(null); // Устанавливаем окно по центру экрана
        setVisible(true);
    }
}
