package fr.openai.ui.panels;

import fr.openai.exec.Messages;
import fr.openai.filter.fixer.Names;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class PlayPanel extends JPanel {
    private final JTextField inputField;
    private final JLabel nameLabel;
    private final JLabel messageLabel;
    private final Names nameFormatter;
    private final Messages messageExtractor;

    public PlayPanel() {
        setLayout(new BorderLayout());

        String watermark = "В это поле можно вставить строку из логов";
        String message = "Message: ";
        String name = "Name: ";

        nameFormatter = new Names();
        messageExtractor = new Messages();
        nameLabel = new JLabel(name);
        add(nameLabel, BorderLayout.CENTER);

        messageLabel = new JLabel(message);
        add(messageLabel, BorderLayout.SOUTH);

        inputField = new JTextField(watermark);
        inputField.setForeground(Color.GRAY);
        inputField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (inputField.getText().equals(watermark)) {
                    inputField.setText("");
                    inputField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (inputField.getText().isEmpty()) {
                    inputField.setText(watermark);
                    inputField.setForeground(Color.GRAY);
                }
            }
        });
        add(inputField, BorderLayout.NORTH);

        inputField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String input = inputField.getText();
                String playerName = nameFormatter.getFinalName(input);
                nameLabel.setText(name + playerName);

                String extractedMessage = messageExtractor.TestMessage(input);
                messageLabel.setText(message + extractedMessage); // Исправлено здесь
            }
        });
    }
}
