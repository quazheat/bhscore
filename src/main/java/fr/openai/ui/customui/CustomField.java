package fr.openai.ui.customui;

import javax.swing.*;
import java.awt.*;

public class CustomField extends JTextField {
    public CustomField(String text) {
        super(text);
        setupCustomField();
    }

    private void setupCustomField() {
        setEditable(false);
        setForeground(Color.BLACK);
        setFont(new Font("Serif", Font.PLAIN, 16));
        setBackground(Color.GRAY);
        setHighlighter(null);
        setTransferHandler(null);
        setEditable(false);
        setFocusable(false);
    }
}