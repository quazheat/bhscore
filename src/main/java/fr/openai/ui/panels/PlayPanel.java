package fr.openai.ui.panels;

import fr.openai.discordfeatures.DiscordRPC;
import fr.openai.exec.Messages;
import fr.openai.exec.Names;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class PlayPanel extends JPanel {
    DiscordRPC discordRPC = new DiscordRPC();
    private final JTextField inputField;
    private final JTextField statusField;
    private final JLabel nameLabel;
    private final JLabel messageLabel;
    private final Names nameFormatter;
    private final Messages messageExtractor;

    public PlayPanel() {
        setLayout(new BorderLayout());

        String watermark = "В это поле можно вставить строку из логов";
        String watermarkStatus = "Свой текст в статус";
        String message = "Сообщение: ";
        String name = "Ник: ";

        nameFormatter = new Names();
        messageExtractor = new Messages();

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JCheckBox rpcCheckBox = new JCheckBox("Активность Discord  ", true);
        topPanel.add(rpcCheckBox);
        rpcCheckBox.setFocusPainted(false);
        rpcCheckBox.addActionListener(e -> {
            boolean isEnabled = rpcCheckBox.isSelected();
            discordRPC.setRPCEnabled(isEnabled);
        });

        statusField = new JTextField(watermarkStatus, 20);
        statusField.setForeground(Color.GRAY);
        topPanel.add(statusField);

        add(topPanel, BorderLayout.NORTH);

        nameLabel = new JLabel(name);
        messageLabel = new JLabel(message);

        JPanel labelPanel = new JPanel(new GridLayout(1, 2));
        labelPanel.add(nameLabel);
        labelPanel.add(messageLabel);
        add(labelPanel, BorderLayout.CENTER);

        inputField = new JTextField(watermark);
        inputField.setForeground(Color.GRAY);
        statusField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (statusField.getText().equals(watermarkStatus)) {
                    statusField.setText("");
                    statusField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (statusField.getText().isEmpty()) {
                    statusField.setText(watermarkStatus);
                    statusField.setForeground(Color.GRAY);
                }
            }
        });
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
        add(inputField, BorderLayout.SOUTH);

        inputField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String input = inputField.getText();
                String playerName = nameFormatter.getFinalName(input);
                nameLabel.setText(name + playerName);

                String extractedMessage = messageExtractor.TestMessage(input);
                messageLabel.setText(message + extractedMessage);
            }
        });
        statusField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                updateDiscordRPCDetails();
            }
        });

    }

    private void updateDiscordRPCDetails() {
        String newDetails = statusField.getText();
        if (newDetails == null || newDetails.length() < 1) {
            newDetails = "Big brother watching you";
        }
        discordRPC.updateRPCDetails(newDetails);
        discordRPC.updateRPC();

    }
}
