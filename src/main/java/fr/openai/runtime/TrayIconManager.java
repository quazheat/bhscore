package fr.openai.runtime;

import fr.openai.filter.FilteringModeManager;
import fr.openai.notify.WindowsNotification;

import java.awt.*;

public class TrayIconManager {
    private final TrayIcon trayIcon;
    private final Image rageIconEnabled;
    private final Image rageIconDisabled;
    private final Image loyalModeDisabled;

    public TrayIconManager(TrayIcon trayIcon, Image rageIconEnabled, Image rageIconDisabled, Image loyalModeDisabled) {
        this.trayIcon = trayIcon;
        this.rageIconEnabled = rageIconEnabled;
        this.rageIconDisabled = rageIconDisabled;
        this.loyalModeDisabled = loyalModeDisabled;
    }

    public void setupIconListeners(CheckboxMenuItem toggleRageModeItem, CheckboxMenuItem toggleLoyalModeItem) {
        toggleRageModeItem.addItemListener(e -> {
            boolean newState = toggleRageModeItem.getState();
            FilteringModeManager.setRageModeEnabled(newState);

            if (newState) {
                WindowsNotification.initTrayIcon();
                trayIcon.setImage(rageIconEnabled);
                toggleLoyalModeItem.setEnabled(false);

                return;
            }
            WindowsNotification.removeTrayIcon();
            trayIcon.setImage(loyalModeDisabled);
            toggleLoyalModeItem.setEnabled(true);

        });

        toggleLoyalModeItem.addItemListener(e -> {
            boolean newState = toggleLoyalModeItem.getState();
            FilteringModeManager.setLoyalModeEnabled(newState);

            if (newState) {
                WindowsNotification.initTrayIcon();
                trayIcon.setImage(rageIconDisabled);
                toggleRageModeItem.setEnabled(false);

                return;
            }
            WindowsNotification.removeTrayIcon();
            if (FilteringModeManager.isRageModeEnabled()) {
                trayIcon.setImage(rageIconEnabled);

                return;
            }
            trayIcon.setImage(loyalModeDisabled);
            toggleRageModeItem.setEnabled(true);
        });
    }
}
