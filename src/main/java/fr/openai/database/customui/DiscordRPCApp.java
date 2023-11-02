package fr.openai.database.customui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DiscordRPCApp extends JDialog {

    private final JTextField usernameField;
    private static String enteredUsername; // To store the entered username

    public DiscordRPCApp() {
        setTitle("BHScore");
        setSize(300, 150);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel messageLabel1 = new JLabel("Введите свой ник:");
        messageLabel1.setHorizontalAlignment(JLabel.CENTER);

        gbc.insets = new Insets(10, 10, 0, 10);
        panel.add(messageLabel1, gbc);

        gbc.gridy = 1;
        gbc.insets = new Insets(0, 10, 10, 10);

        // Create a text field for the user to enter their username
        usernameField = new JTextField(20);
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(usernameField, gbc);

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(10, 10, 10, 10);
        panel.add(buttonPanel, gbc);

        JButton closeButton = new JButton("Закрыть");
        closeButton.addActionListener(e -> {
            enteredUsername = usernameField.getText(); // Set the entered username
            dispose();
        });

        Dimension buttonSize = new Dimension(100, 40);
        closeButton.setPreferredSize(buttonSize);

        // Apply the CustomButtonUI to the Close button
        CustomButtonUI.setCustomStyle(closeButton);

        gbc.gridx = 1;
        gbc.insets = new Insets(10, 15, 10, 10);
        buttonPanel.add(closeButton, gbc);

        add(panel);
    }

    public static String getUsername() {
        return enteredUsername;
    }
}