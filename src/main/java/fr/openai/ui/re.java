package fr.openai.ui;

import fr.openai.e.ee.o4;
import fr.openai.s.uu.manager.ko;

import javax.swing.*;
import java.awt.*;
import java.util.UUID;

public class re {
    public static void oa() {

        ko a = new ko();
        UUID ss34 = a.ok();
        String str1 = ss34 != null ? ss34.toString() : "UUID not found";

        JTextField e1 = new JTextField(str1);
        e1.setEditable(false);
        e1.setFont(new Font("Arial", Font.PLAIN, 14));
        e1.setForeground(Color.BLACK);
        e1.setMaximumSize(new Dimension(400, 40));

        JLabel t = new JLabel("Wrong UUID:");
        t.setForeground(Color.BLACK);
        JLabel rtq = new JLabel("Contact support.");
        rtq.setForeground(Color.BLACK);

        JPanel eq = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        eq.add(t, gbc);

        gbc.gridy = 1;
        eq.add(e1, gbc);

        gbc.gridy = 2;
        eq.add(rtq, gbc);

        Object[] message = {eq};

        JButton o = new JButton("OK");
        o.addActionListener(e -> {
            o4.a4(str1);
            System.exit(1);
        });

        Object[] options = {o};

        JOptionPane.showOptionDialog(null, message, "Login Failed",
        JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE, null, options, options[0]);
    }
}
