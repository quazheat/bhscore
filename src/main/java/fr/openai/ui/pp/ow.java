package fr.openai.ui.pp;

import fr.openai.discordfeatures.da;
import fr.openai.e.ee.yt;
import fr.openai.e.o13;
import fr.openai.e.Nes;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ow extends JPanel {
    da da = new da();
    private final JTextField a;
    private final JTextField b;
    private final JLabel c;
    private final JLabel v;
    private final Nes ez;
    private final o13 o;

    public ow() {
        setLayout(new BorderLayout());

        JCheckBox akb = new JCheckBox("Авто-скриншот", yt.ak);
        akb.addActionListener(e -> {
            boolean a1 = akb.isSelected();
            yt.setE(a1);
        });

        akb.setToolTipText("Сохраняется в папку Pictures/Изображения (стандартная папка Windows)");

        akb.setFocusPainted(false);
        String w = "В это поле можно вставить строку из логов";
        String var = "Свой текст в статус";
        String message = "Сообщение: ";
        String name = "Ник: ";

        ez = new Nes();
        o = new o13();

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JCheckBox a = new JCheckBox("Активность Discord  ", true);
        topPanel.add(a);
        a.setFocusPainted(false);
        a.addActionListener(e -> {
            boolean a1 = a.isSelected();
            da.setE(a1);
        });

        b = new JTextField(var, 20);
        b.setForeground(Color.GRAY);
        topPanel.add(b);

        add(topPanel, BorderLayout.NORTH);

        c = new JLabel(name);
        v = new JLabel(message);

        JPanel labelPanel = new JPanel(new GridLayout(1, 2));
        Border ii = BorderFactory.createEmptyBorder(0, 9, 0, 0);
        akb.setBorder(ii);

        labelPanel.add(akb);
//        labelPanel.add(c);
//        labelPanel.add(v);
        add(labelPanel, BorderLayout.CENTER);

        this.a = new JTextField(w);
        this.a.setForeground(Color.GRAY);
        b.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (b.getText().equals(var)) {
                    b.setText("");
                    b.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (b.getText().isEmpty()) {
                    b.setText(var);
                    b.setForeground(Color.GRAY);
                }
            }
        });
        this.a.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (ow.this.a.getText().equals(w)) {
                    ow.this.a.setText("");
                    ow.this.a.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (ow.this.a.getText().isEmpty()) {
                    ow.this.a.setText(w);
                    ow.this.a.setForeground(Color.GRAY);
                }
            }
        });
        //add(this.a, BorderLayout.SOUTH);

        this.a.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String input = ow.this.a.getText();
                String playerName = ez.gp(input);
                c.setText(name + playerName);

                String extractedMessage = o.a(input);
                v.setText(message + extractedMessage);
            }
        });
        b.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                a();
            }
        });

    }

    private void a() {
        String a = b.getText();
        if (a == null || a.length() < 1) {
            a = "Big brother watching you";
        }

        da.ez(a);
    }
}
