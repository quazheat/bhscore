package fr.openai.ui;

import fr.openai.b.dzz;
import fr.openai.ui.ny.cui;
import fr.openai.b.sss;

import javax.swing.*;
import java.awt.*;

public class ik extends JDialog {
    dzz dzz = new dzz();

    public ik() {
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

        String e1 = dzz.us();

        SSS(e1);

        sss sss = new sss();
        int m = sss.sS(e1);
        int w = sss.s(e1);

        String t = "<html><div align='center'><b>Статистика за сессию:</b> <br>" + e1 +
                "<br><br>Mutes: " + m +
                "<br>Warns: " + w +
                "<br><b>Total:</b> " + (m + w) + "</div></html>";

        if (e1 == null || e1.length() <= 3) {
            System.exit(0);
        }
        JLabel a1 = new JLabel(t);
        a1.setHorizontalAlignment(JLabel.CENTER);
        a1.setFont(new Font("Arial", Font.PLAIN, 13));

        Dimension s1 = new Dimension(300, 100);
        a1.setMinimumSize(s1);
        a1.setPreferredSize(s1);
        gbc.insets = new Insets(15, 10, 0, 10);
        panel.add(a1, gbc);

        JPanel b1 = new JPanel(new GridBagLayout());
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        gbc.insets = new Insets(10, 10, 10, 10);
        panel.add(b1, gbc);

        JButton b = new JButton("Очистить");
        b.addActionListener(e -> ssSa(e1));

        JButton eqs = new JButton("Закрыть");
        eqs.addActionListener(e -> dispose());

        Dimension buttonSize = new Dimension(140, 40);
        b.setPreferredSize(buttonSize);
        eqs.setPreferredSize(buttonSize);
        cui.cios(b);
        cui.cios(eqs);

        gbc.gridx = 0;
        gbc.insets = new Insets(10, 10, 10, 135);
        b1.add(b, gbc);

        gbc.gridx = 2;
        gbc.insets = new Insets(10, 135, 10, 10);
        b1.add(eqs, gbc);

        add(panel);
    }

    private void SSS(String a) {
        sss sss = new sss();
        sss.ap(a);
    }

    private void ssSa(String main) {
        sss sss = new sss();
        sss.deleteDocumentIfMatch(main);

        dispose();
    }
}
