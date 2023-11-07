package fr.openai.ui.customui;

import fr.openai.database.ConfigManager;
import fr.openai.discordfeatures.DiscordRPCDiag;
import fr.openai.filter.Filtering;

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
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        String username = (configManager.getUsername());
        int mutes = Filtering.getMutes();
        int warns = Filtering.getWarns();

        String statsText = "<html><b>Статистика за сессию</b> " + username +
                "<br><br>Mutes: " + mutes +
                "<br>Warns: " + warns +
                "<br><b>Total:</b> " + (mutes + warns) + "</html>";
        if (username == null || username.length() <=3) {
            DiscordRPCDiag discordRPCDiag = new DiscordRPCDiag();
            discordRPCDiag.setModal(true);
            discordRPCDiag.setVisible(true);
            statsText = "<html>Статистика <b>недоступна</b>.<br>Вы не указали имя пользователя.<br><br>";
        }
        JLabel titleLabel = new JLabel(statsText);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 13));

        gbc.insets = new Insets(10, 10, 0, 10);
        panel.add(titleLabel, gbc);

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(10, 10, 10, 10);
        panel.add(buttonPanel, gbc);

        JButton closeButton = new JButton("Закрыть");
        closeButton.addActionListener(e -> dispose());

        Dimension buttonSize = new Dimension(160, 40);
        closeButton.setPreferredSize(buttonSize);
        CustomButtonUI.setCustomStyle(closeButton);

        gbc.gridx = 1;
        gbc.insets = new Insets(10, 30, 10, 10);
        buttonPanel.add(closeButton, gbc);

        add(panel);
    }
}
