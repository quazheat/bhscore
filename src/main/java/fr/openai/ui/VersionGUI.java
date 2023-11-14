package fr.openai.ui;

import fr.openai.starter.VersionChecker;
import fr.openai.ui.customui.CustomButtonUI;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class VersionGUI extends JDialog {
    VersionChecker versionChecker = new VersionChecker();

    public VersionGUI(String dbVersion, String changelog) {

        setTitle("BHScore " + versionChecker.getCurrentVersion());
        setSize(500, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel messageLabel1 = new JLabel("Your version is outdated.");
        JLabel messageLabel2 = new JLabel("Do you want to update to version " + dbVersion + "?");
        messageLabel1.setHorizontalAlignment(JLabel.CENTER);
        messageLabel2.setHorizontalAlignment(JLabel.CENTER);

        gbc.insets = new Insets(10, 10, 0, 10);
        panel.add(messageLabel1, gbc);

        gbc.gridy = 1;
        gbc.insets = new Insets(0, 10, 10, 10);
        panel.add(messageLabel2, gbc);

        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 10, 10, 10);
        JTextArea changelogTextArea = new JTextArea(changelog);
        changelogTextArea.setFont(new Font(Font.DIALOG, Font.PLAIN, 14));
        JScrollPane changelogScrollPane = new JScrollPane(changelogTextArea);
        changelogScrollPane.setPreferredSize(new Dimension(450, 200));
        panel.add(changelogScrollPane, gbc);
        changelogTextArea.setEditable(false);
        changelogTextArea.setFocusable(false);
        changelogTextArea.setLineWrap(true);
        changelogTextArea.setWrapStyleWord(true);

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(10, 10, 10, 10);
        panel.add(buttonPanel, gbc);

        JButton okButton = new JButton("Download");
        CustomButtonUI.setCustomStyle(okButton);
        okButton.addActionListener(e -> {
            openBrowserToUpdate();
            dispose();
        });

        JButton laterButton = new JButton("Later");
        CustomButtonUI.setCustomStyle(laterButton);
        laterButton.addActionListener(e -> dispose());

        Dimension buttonSize = new Dimension(100, 40);
        okButton.setPreferredSize(buttonSize);
        laterButton.setPreferredSize(buttonSize);

        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.CENTER;
        gbc.weightx = 0.5;
        gbc.insets = new Insets(10, 60, 10, 125);
        buttonPanel.add(okButton, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.5;
        gbc.insets = new Insets(10, 15, 10, 10);
        buttonPanel.add(laterButton, gbc);

        add(panel);
    }

    private void openBrowserToUpdate() {
        try {
            URI uri = new URI("https://drive.google.com/file/d/1n6r0J21FXMYWGi3IJgEjgggCVr-hinM2/view");
            Desktop.getDesktop().browse(uri);
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
    }
}
