package fr.openai.ui.pp;

import fr.openai.ui.ny.cui;
import fr.openai.ui.ny.ata;
import fr.openai.b.menu.i;
import fr.openai.b.menu.aNw;
import fr.openai.b.menu.ds;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class b extends JPanel {
    private final JTextField b;

    public b(aNw aNw, ds ds) {
        setLayout(new BorderLayout());
        String b10 = "Слово во множественном числе";
        setBackground(Color.lightGray);

        JLabel e1 = new JLabel();
        e1.setHorizontalAlignment(JLabel.CENTER);
        e1.setForeground(Color.BLACK);
        add(e1, BorderLayout.NORTH);

        JPanel we1 = new JPanel();
        we1.setLayout(new BorderLayout());

        JPanel sd = new JPanel();
        sd.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel sdfq = new JLabel("Введите слово:");
        sdfq.setForeground(Color.BLACK);
        sd.setBackground(Color.LIGHT_GRAY);

        b = new JTextField(b10,20);
        b.setForeground(Color.GRAY);
        sd.add(sdfq);
        sd.add(b);

        JButton a = new JButton("Добавить");
        JButton b = new JButton("Удалить");
        cui.cios(a);
        cui.cios(b);
        a.setEnabled(false);
        b.setEnabled(false);

        JPanel c = new JPanel();
        c.setLayout(new FlowLayout(FlowLayout.CENTER));
        c.add(a);
        c.add(b);
        c.setBackground(Color.LIGHT_GRAY);

        we1.add(sd, BorderLayout.CENTER);
        we1.add(c, BorderLayout.SOUTH);

        add(we1, BorderLayout.CENTER);

        this.b.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (b.this.b.getText().equals(b10)) {
                    b.this.b.setText("");
                    b.this.b.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (b.this.b.getText().isEmpty()) {
                    b.this.b.setText(b10);
                    b.this.b.setForeground(Color.GRAY);
                }
            }
        });

        a.addActionListener(e -> {
            String ss1 = this.b.getText();
            if (!ss1.isEmpty()) {
                aNw.sss(ss1);
            }
            this.b.setText("");
        });

        b.addActionListener(e -> {
            String ss2 = this.b.getText();
            if (!ss2.isEmpty()) {
                ds.dda(ss2);
            }
            this.b.setText("");
        });

        i.sI(this.b, a, b);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setUI(new ata());
        add(tabbedPane, BorderLayout.SOUTH);
    }
}
