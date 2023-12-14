package fr.openai.discordfeatures;

import fr.openai.b.dzz;
import fr.openai.ui.ny.cui;

import javax.swing.*;
import java.awt.*;

public class de extends JDialog {

    private final JTextField f;
    private static String ef;

    public de() {
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

        JLabel df = new JLabel("Введите свой ник:");
        df.setHorizontalAlignment(JLabel.CENTER);

        gbc.insets = new Insets(10, 10, 0, 10);
        panel.add(df, gbc);

        gbc.gridy = 1;
        gbc.insets = new Insets(0, 10, 10, 10);

        this.f = new JTextField(20);
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        dzz dzz = new dzz();
        this.f.setText(dzz.us());

        panel.add(this.f, gbc);

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(10, 10, 10, 10);
        panel.add(buttonPanel, gbc);

        JButton cvb = new JButton("это я");
        cvb.addActionListener(e -> {
            ef = this.f.getText();
            af(ef);
            dispose();
        });

        Dimension bs = new Dimension(100, 40);
        cvb.setPreferredSize(bs);

        cui.cios(cvb);

        gbc.gridx = 1;
        gbc.insets = new Insets(10, 15, 10, 10);
        buttonPanel.add(cvb, gbc);

        add(panel);
    }

    private void af(String is) {
        dzz dzz = new dzz();
        dzz.is(is);
    }

    public static String gIs() {
        return ef;
    }
}
