package fr.openai.database.customui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ModesPanel extends JPanel {
    private final JCheckBox checkBox1;
    private final JCheckBox checkBox2;
    private final Timer buttonTimer;

    public ModesPanel() {
        setLayout(new GridLayout(2, 1)); // Две строки, один столбец

        checkBox1 = new JCheckBox("test");
        checkBox2 = new JCheckBox("test 2");

        checkBox1.addActionListener(e -> {
            if (checkBox1.isSelected()) {
                checkBox2.setSelected(false);
                disableButtonsForTwoSeconds();
            } else {
                enableButtons();
            }
        });

        checkBox2.addActionListener(e -> {
            if (checkBox2.isSelected()) {
                checkBox1.setSelected(false);
                disableButtonsForTwoSeconds();
            } else {
                enableButtons();
            }
        });

        add(checkBox1);
        add(checkBox2);

        buttonTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enableButtons();
            }
        });
    }

    private void disableButtonsForTwoSeconds() {
        checkBox1.setEnabled(false);
        checkBox2.setEnabled(false);
        buttonTimer.start();
    }

    private void enableButtons() {
        checkBox1.setEnabled(true);
        checkBox2.setEnabled(true);
        buttonTimer.stop();
    }

    public boolean isMode1Selected() {
        return checkBox1.isSelected();
    }

    public boolean isMode2Selected() {
        return checkBox2.isSelected();
    }
}
