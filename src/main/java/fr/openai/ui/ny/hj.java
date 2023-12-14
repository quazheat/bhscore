package fr.openai.ui.ny;

import fr.openai.b.menu.uu;
import fr.openai.ui.pp.uU;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class hj extends JPanel {

    public hj(String a) {
        setLayout(new BorderLayout());
        JButton b = new JButton("Инфо");
        cui.cios(b);
        b.setFocusPainted(false);

        b.addActionListener(e -> {
            if (uu.a) {
                SwingUtilities.invokeLater(uU::new);
                return;
            }
            JDialog csz = new JDialog();
            csz.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);

            try {
                BufferedImage i = ImageIO.read(new File("tray_icon.png"));
                csz.setIconImage(i);
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            JButton cb = new JButton("Close");
            cui.cios(cb);
            cb.addActionListener(closeEvt -> csz.dispose());

            JTextArea a1 = new JTextArea(a);
            a1.setWrapStyleWord(true);
            a1.setLineWrap(true);
            a1.setOpaque(false);
            a1.setEditable(false);
            a1.setFocusable(false);
            JScrollPane a12 = new JScrollPane(a1);

            JPanel b1 = new JPanel(new FlowLayout());
            b1.add(cb);

            csz.setLayout(new BorderLayout());
            csz.add(a12, BorderLayout.CENTER);
            csz.add(b1, BorderLayout.SOUTH);

            csz.setTitle("Инфо");
            csz.setSize(450, 735);
            csz.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            csz.setLocationRelativeTo(null);
            csz.setVisible(true);
        });

        add(b, BorderLayout.CENTER);
    }
}
