package fr.openai.ui;

import fr.openai.database.ConfigManager;
import fr.openai.database.b;
import fr.openai.ui.customui.CustomButtonUI;
import fr.openai.ui.panels.StatsDatabaseManager;

import javax.swing.*;
import java.awt.*;

public class MutesWarnsGUI extends JDialog {
    ConfigManager configManager = new ConfigManager();

    public MutesWarnsGUI() {
        setTitle("BHScore");
        setSize(300, 200);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setAlwaysOnTop(true);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.BOTH;

        String username = configManager.getUsername();

        saveMutesAndWarns(username);

        StatsDatabaseManager statsDatabaseManager = new StatsDatabaseManager();
        int mutes = statsDatabaseManager.getMutes(username);
        int warns = statsDatabaseManager.getWarns(username);

        String statsText = "<html><div align='center'><b>Статистика за сессию:</b> <br>" + username +
                "<br><br>Mutes: " + mutes +
                "<br>Warns: " + warns +
                "<br><b>Total:</b> " + (mutes + warns) + "</div></html>";

        if (username == null || username.length() <= 3) {
            System.exit(0);
        }
        JLabel titleLabel = new JLabel(statsText);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 13));

        Dimension minSize = new Dimension(300, 100);
        titleLabel.setMinimumSize(minSize);
        titleLabel.setPreferredSize(minSize);
        gbc.insets = new Insets(15, 10, 0, 10);
        panel.add(titleLabel, gbc);

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        gbc.insets = new Insets(10, 10, 10, 10);
        panel.add(buttonPanel, gbc);

        JButton clearButton = new JButton("Очистить");
        clearButton.addActionListener(e -> clearStats(username));

        JButton closeButton = new JButton("Закрыть");
        closeButton.addActionListener(e -> {
            dispose();
        });

        Dimension buttonSize = new Dimension(140, 40);
        clearButton.setPreferredSize(buttonSize);
        closeButton.setPreferredSize(buttonSize);
        CustomButtonUI.setCustomStyle(clearButton);
        CustomButtonUI.setCustomStyle(closeButton);

        gbc.gridx = 0;
        gbc.insets = new Insets(10, 10, 10, 135);
        buttonPanel.add(clearButton, gbc);

        gbc.gridx = 2;
        gbc.insets = new Insets(10, 135, 10, 10);
        buttonPanel.add(closeButton, gbc);

        add(panel);
    }

    private void saveMutesAndWarns(String username) {
        StatsDatabaseManager statsDatabaseManager = new StatsDatabaseManager();
        statsDatabaseManager.saveMutesAndWarns(username);
    }

    private void clearStats(String username) {
        StatsDatabaseManager statsDatabaseManager = new StatsDatabaseManager();
        statsDatabaseManager.deleteDocumentIfMatch(username);

        dispose();
    }
}
