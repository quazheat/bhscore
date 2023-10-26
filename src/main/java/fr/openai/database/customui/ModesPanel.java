package fr.openai.database.customui;

import fr.openai.database.files.TrayIconLoader;
import fr.openai.filter.FilteringModeManager;

import javax.swing.*;
import java.awt.*;

public class ModesPanel extends JPanel {
    TrayIconLoader iconLoader = new TrayIconLoader();
    private final TrayIcon trayIcon;

    public ModesPanel(TrayIcon trayIcon) {
        this.trayIcon = trayIcon;

        setLayout(new BorderLayout());

        JRadioButton rageRadioButton = new JRadioButton("Rage Mode");
        JRadioButton loyalRadioButton = new JRadioButton("Loyal Mode");

        // Create a button group to ensure only one can be selected
        ButtonGroup radioButtonGroup = new ButtonGroup();
        radioButtonGroup.add(rageRadioButton);
        radioButtonGroup.add(loyalRadioButton);

        rageRadioButton.addActionListener(e -> {
            FilteringModeManager.setRageModeEnabled(true);
            FilteringModeManager.setLoyalModeEnabled(false);
            updateTrayIcon();
        });

        loyalRadioButton.addActionListener(e -> {
            FilteringModeManager.setLoyalModeEnabled(true);
            FilteringModeManager.setRageModeEnabled(false);
            updateTrayIcon();
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
