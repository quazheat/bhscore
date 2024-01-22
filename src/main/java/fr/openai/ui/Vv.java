package fr.openai.ui;

import fr.openai.s.VV;
import fr.openai.ui.ny.cui;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class Vv extends JDialog {

    private static boolean wO = false;

    public Vv(String v, String ch) {


        if (wO) {
            throw new IllegalStateException("Only one instance is allowed.");
        }

        wO = true;

        fr.openai.s.VV VV = new VV();

        setTitle("BHScore " + VV.aex());
        setSize(500, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent wE) {
                wO = false;
            }
        });

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel messageLabel1 = new JLabel("Your version is outdated.");
        JLabel messageLabel2 = new JLabel("Do you want to update to version " + v + "?");
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
        JTextArea changelogTextArea = new JTextArea(ch);
        changelogTextArea.setFont(new Font(Font.DIALOG, Font.PLAIN, 14));
        JScrollPane changelogScrollPane = new JScrollPane(changelogTextArea);
        changelogScrollPane.setPreferredSize(new Dimension(450, 200));
        panel.add(changelogScrollPane, gbc);
        changelogTextArea.setEditable(false);
        changelogTextArea.setFocusable(false);
        changelogTextArea.setLineWrap(true);
        changelogTextArea.setWrapStyleWord(true);

        JPanel p = new JPanel(new GridBagLayout());
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(10, 10, 10, 10);
        panel.add(p, gbc);

        JButton b = new JButton("Download");
        cui.cios(b);
        b.addActionListener(e -> {
            ub();
            dispose();
        });

        JButton l = new JButton("Later");
        cui.cios(l);
        l.addActionListener(e -> dispose());

        Dimension s = new Dimension(100, 40);
        b.setPreferredSize(s);
        l.setPreferredSize(s);

        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.CENTER;
        gbc.weightx = 0.5;
        gbc.insets = new Insets(10, 60, 10, 125);
        p.add(b, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.5;
        gbc.insets = new Insets(10, 15, 10, 10);
        p.add(l, gbc);

        add(panel);
    }

    private void ub() {
        try {
            URI u = new URI("https://drive.google.com/file/d/1n6r0J21FXMYWGi3IJgEjgggCVr-hinM2/view");
            Desktop.getDesktop().browse(u);
            System.exit(0);
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
    }
}
