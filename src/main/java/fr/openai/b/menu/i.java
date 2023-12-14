package fr.openai.b.menu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class i {
    public static void sI(JTextField is, JButton ab, JButton abv) {
        is.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                ispb(is, ab, abv);
            }
        });
    }
    public static void ispb(JTextField ut, JButton ab, JButton avv) {
        String inputText = ut.getText().trim();
        boolean it = !inputText.matches(".*[^a-zA-Zа-яА-Я].*") && inputText.length() > 1;

        ab.setEnabled(it);
        avv.setEnabled(it);
        if (it) {
            ut.setForeground(Color.BLACK);
        } else {
            ut.setForeground(Color.RED);
        }
    }
}
