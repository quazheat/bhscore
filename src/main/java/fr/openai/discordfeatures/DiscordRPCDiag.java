package fr.openai.discordfeatures;

import fr.openai.database.ConfigManager;
import fr.openai.ui.customui.CustomButtonUI;

import javax.swing.*;
import java.awt.*;

public class DiscordRPCDiag extends JDialog {

    private final JTextField usernameField;
    private static String enteredUsername; // To store the entered username

    public DiscordRPCDiag() {
        setTitle("BHScore");
        setSize(300, 150);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        setAlwaysOnTop(true);
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel usernameField = new JLabel("Введите свой ник:");
        usernameField.setHorizontalAlignment(JLabel.CENTER);

        gbc.insets = new Insets(10, 10, 0, 10);
        panel.add(usernameField, gbc);

        gbc.gridy = 1;
        gbc.insets = new Insets(0, 10, 10, 10);

        // Create a text field for the user to enter their username
        this.usernameField = new JTextField(20);
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Load the username from config.properties and set it in the text field
        ConfigManager configManager = new ConfigManager();
        this.usernameField.setText(configManager.getUsername());

        panel.add(this.usernameField, gbc);

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(10, 10, 10, 10);
        panel.add(buttonPanel, gbc);

        JButton closeButton = new JButton("это я");
        closeButton.addActionListener(e -> {
            enteredUsername = this.usernameField.getText(); // Set the entered username
            saveUsernameToConfig(enteredUsername); // Save the username to config.properties
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

    private void saveUsernameToConfig(String username) {
        ConfigManager configManager = new ConfigManager();
        configManager.setUsername(username);
    }

    public static String getUsername() {
        return enteredUsername;
    }
}
