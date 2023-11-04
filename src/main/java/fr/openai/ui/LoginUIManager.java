package fr.openai.ui;

import fr.openai.exec.ClipboardUtil;
import fr.openai.starter.uuid.manager.UUIDManager;

import javax.swing.*;
import java.awt.*;
import java.util.UUID;

public class LoginUIManager {
    public static void loginPopup() {

        UUIDManager uuidManager = new UUIDManager();
        UUID systemUUID = uuidManager.getSystemUUID();
        String sysIdStr = systemUUID != null ? systemUUID.toString() : "UUID not found";

        JTextField uuid = new JTextField(sysIdStr);
        uuid.setEditable(false);
        uuid.setFont(new Font("Arial", Font.PLAIN, 14));
        uuid.setForeground(Color.BLACK);
        uuid.setMaximumSize(new Dimension(400, 40));

        JLabel wrongLabel = new JLabel("Wrong UUID:");
        wrongLabel.setForeground(Color.BLACK);
        JLabel supportLabel = new JLabel("Contact support.");
        supportLabel.setForeground(Color.BLACK);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(wrongLabel, gbc);

        gbc.gridy = 1;
        panel.add(uuid, gbc);

        gbc.gridy = 2;
        panel.add(supportLabel, gbc);

        Object[] message = {panel};

        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> {
            ClipboardUtil.copyToClipboard(sysIdStr);
            System.exit(1);
        });

        Object[] options = {okButton};

        JOptionPane.showOptionDialog(null, message, "Login Failed",
        JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE, null, options, options[0]);
    }
}
