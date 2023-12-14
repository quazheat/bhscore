package fr.openai.ui.pp;

import fr.openai.ui.ny.cui;
import fr.openai.ui.ny.ata;
import fr.openai.b.menu.i;
import fr.openai.b.menu.aW;
import fr.openai.b.menu.r;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class ae extends JPanel {
    private final JTextField a;

    public ae(aW aW, r r) {
        setLayout(new BorderLayout());
        String b = "Слово во множественном числе";
        setBackground(Color.lightGray);

        JLabel outputLabel = new JLabel();
        outputLabel.setHorizontalAlignment(JLabel.CENTER);
        outputLabel.setForeground(Color.BLACK);
        add(outputLabel, BorderLayout.NORTH);

        JPanel d = new JPanel();
        d.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel inputLabel = new JLabel("Введите слово:");
        inputLabel.setForeground(Color.black);
        inputPanel.setBackground(Color.LIGHT_GRAY);

        a = new JTextField(b, 20);
        a.setForeground(Color.GRAY);
        inputPanel.add(inputLabel);
        inputPanel.add(a);

        JButton d2 = new JButton("Добавить");
        JButton a1 = new JButton("Удалить");
        cui.cios(d2);
        cui.cios(a1);
        d2.setEnabled(false);
        a1.setEnabled(false);

        JPanel d1 = new JPanel();
        d1.setLayout(new FlowLayout(FlowLayout.CENTER));
        d1.add(d2);
        d1.add(a1);
        d1.setBackground(Color.LIGHT_GRAY);

        d.add(inputPanel, BorderLayout.CENTER);
        d.add(d1, BorderLayout.SOUTH);

        add(d, BorderLayout.CENTER);

        a.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (a.getText().equals(b)) {
                    a.setText("");
                    a.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (a.getText().isEmpty()) {
                    a.setText(b);
                    a.setForeground(Color.GRAY);
                }
            }
        });

        d2.addActionListener(e -> {
            String newWord = a.getText();
            if (!newWord.isEmpty()) {
                aW.wA(newWord);
            }
            a.setText("");
        });

        a1.addActionListener(e -> {
            String wordToRemove = a.getText();
            if (!wordToRemove.isEmpty()) {
                r.removeWhitelistWord(wordToRemove);
            }
            a.setText("");
        });

        i.sI(a, d2, a1);

        JTabbedPane n = new JTabbedPane();
        n.setUI(new ata());
        add(n, BorderLayout.SOUTH);
    }
}
