package fr.openai.database.customui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class VersionGUI extends JDialog {

    public VersionGUI() {
        setTitle("BHScore version");
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

        JLabel messageLabel1 = new JLabel("Доступна новая версия программы.");
        JLabel messageLabel2 = new JLabel("Желаете обновить?");
        messageLabel1.setHorizontalAlignment(JLabel.CENTER);
        messageLabel2.setHorizontalAlignment(JLabel.CENTER);

        gbc.insets = new Insets(10, 10, 0, 10); // Отступ между компонентами
        panel.add(messageLabel1, gbc);

        gbc.gridy = 1;
        gbc.insets = new Insets(0, 10, 10, 10); // Отступ между компонентами
        panel.add(messageLabel2, gbc);

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(10, 10, 10, 10);
        panel.add(buttonPanel, gbc);

        JButton okButton = new JButton("Скачать");
        CustomButtonUI.setCustomStyle(okButton);
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openBrowserToUpdate();
                dispose();
            }
        });

        JButton laterButton = new JButton("Позже");
        CustomButtonUI.setCustomStyle(laterButton);
        laterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });


        Dimension buttonSize = new Dimension(100, 40); // Размер кнопок
        okButton.setPreferredSize(buttonSize);
        laterButton.setPreferredSize(buttonSize);

        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.CENTER; //
        gbc.weightx = 0.5;
        gbc.insets = new Insets(10, 10, 10, 75);
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
