package fr.openai.database.customui;

import fr.openai.database.files.TrayIconLoader;
import fr.openai.filter.FilteringModeManager;
import fr.openai.notify.WindowsNotification;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ModesPanel extends JPanel {
    TrayIconLoader iconLoader = new TrayIconLoader();
    Image appIcon = iconLoader.loadIcon();
    private TrayIcon trayIcon;
    private JRadioButton rageRadioButton;
    private JRadioButton loyalRadioButton;
    private ButtonGroup radioButtonGroup;

    public ModesPanel(TrayIcon trayIcon) {
        this.trayIcon = trayIcon;

        setLayout(new BorderLayout());

        rageRadioButton = new JRadioButton("Rage Mode");
        loyalRadioButton = new JRadioButton("Loyal Mode");

        // Create a button group to ensure only one can be selected
        radioButtonGroup = new ButtonGroup();
        radioButtonGroup.add(rageRadioButton);
        radioButtonGroup.add(loyalRadioButton);

        rageRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FilteringModeManager.setRageModeEnabled(true);
                FilteringModeManager.setLoyalModeEnabled(false);
                updateTrayIcon();
            }
        });

        loyalRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FilteringModeManager.setLoyalModeEnabled(true);
                FilteringModeManager.setRageModeEnabled(false);
                updateTrayIcon();
            }
        });

        JPanel radioButtonPanel = new JPanel(new GridLayout(2, 1));
        radioButtonPanel.add(rageRadioButton);
        radioButtonPanel.add(loyalRadioButton);
        add(radioButtonPanel, BorderLayout.NORTH);
    }

    private void updateTrayIcon() {
        if (FilteringModeManager.isRageModeEnabled()) {
            trayIcon.setImage(iconLoader.loadRageIcon());
        } else if (FilteringModeManager.isLoyalModeEnabled()) {
            trayIcon.setImage(iconLoader.loadRageIconDisabled());
        } else {
            // Load the default icon
            trayIcon.setImage(iconLoader.loadIcon());
        }
    }
}
