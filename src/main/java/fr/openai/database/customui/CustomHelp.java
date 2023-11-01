package fr.openai.database.customui;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class CustomHelp extends JPanel {

    public CustomHelp(String clickMessage) {
        setLayout(new BorderLayout());
        JButton infoButton = new JButton("Инфо");
        CustomButtonUI.setCustomStyle(infoButton);
        infoButton.setFocusPainted(false);

        infoButton.addActionListener(e -> {
            // Create a custom dialog with your custom button
            JDialog customDialog = new JDialog();
            customDialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL); // Prevent interaction with other windows

            // Load the icon image from a file (tray_icon.png)
            try {
                BufferedImage iconImage = ImageIO.read(new File("tray_icon.png"));
                customDialog.setIconImage(iconImage);
            } catch (IOException ex) {
                // Handle the exception if the image can't be loaded
                ex.printStackTrace();
            }

            // Create a custom button to replace the default "OK" button
            JButton closeButton = new JButton("Close");
            CustomButtonUI.setCustomStyle(closeButton);
            closeButton.addActionListener(closeEvt -> customDialog.dispose());

            // Add the click message to the dialog
            JTextArea messageTextArea = new JTextArea(clickMessage);
            messageTextArea.setWrapStyleWord(true);
            messageTextArea.setLineWrap(true);
            messageTextArea.setOpaque(false);
            messageTextArea.setEditable(false);
            messageTextArea.setFocusable(false);
            JScrollPane scrollPane = new JScrollPane(messageTextArea);

            JPanel buttonPanel = new JPanel(new FlowLayout());
            buttonPanel.add(closeButton);

            customDialog.setLayout(new BorderLayout());
            customDialog.add(scrollPane, BorderLayout.CENTER);
            customDialog.add(buttonPanel, BorderLayout.SOUTH);

            customDialog.setTitle("Инфо");
            customDialog.setSize(450, 735);
            customDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            customDialog.setLocationRelativeTo(null); // Center on the screen
            customDialog.setVisible(true);
        });

        add(infoButton, BorderLayout.CENTER);
    }
}
