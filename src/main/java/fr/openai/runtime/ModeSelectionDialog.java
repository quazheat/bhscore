package fr.openai.runtime;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ModeSelectionDialog extends JDialog {
    private JCheckBox rageModeCheckbox;
    private JCheckBox loyalModeCheckbox;
    private JButton okButton;

    public ModeSelectionDialog(Frame parent) {
        super(parent, "Выберите режим", true);

        rageModeCheckbox = new JCheckBox("Rage Mode");
        loyalModeCheckbox = new JCheckBox("Loyal Mode");
        okButton = new JButton("OK");

        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose(); // Закрываем диалог при нажатии на OK
            }
        });

        JPanel panel = new JPanel(new GridLayout(3, 1));
        panel.add(rageModeCheckbox);
        panel.add(loyalModeCheckbox);
        panel.add(okButton);

        getContentPane().add(panel, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(parent);
    }

    public boolean isRageModeSelected() {
        return rageModeCheckbox.isSelected();
    }

    public boolean isLoyalModeSelected() {
        return loyalModeCheckbox.isSelected();
    }
}
