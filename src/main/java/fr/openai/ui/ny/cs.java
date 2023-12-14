package fr.openai.ui.ny;

import fr.openai.n.NN;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class cs extends JButton {
    private final NN m;
    private final JDialog a;

    public cs(String a, JDialog b, NN m) {
        super(a);
        this.a = b;
        this.m = m;

        a();
        addActionListener(new b());
    }

    private void a() {
        setBackground(Color.RED);
        setForeground(Color.WHITE);
    }

    private class b implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (a != null) {
                a.dispose();
                m.xcz(-20);
            }
        }
    }
}
