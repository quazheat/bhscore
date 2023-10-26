package fr.openai.runtime;

import fr.openai.database.files.TrayIconLoader;
import fr.openai.database.editor.Editor;
import fr.openai.filter.FilteringModeManager;
import fr.openai.notify.WindowsNotification;
import fr.openai.starter.logs.ConsoleLogger;

import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

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
        toggleRageModeItem.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                boolean newState = toggleRageModeItem.getState();
                FilteringModeManager.setRageModeEnabled(newState);

                if (newState) {
                    WindowsNotification.initTrayIcon();
                    trayIcon.setImage(rageIconEnabled);
                    toggleLoyalModeItem.setEnabled(false);
                } else {
                    WindowsNotification.removeTrayIcon();
                    trayIcon.setImage(loyalModeDisabled);
                    toggleLoyalModeItem.setEnabled(true);
                }
            }
        });

        toggleLoyalModeItem.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                boolean newState = toggleLoyalModeItem.getState();
                FilteringModeManager.setLoyalModeEnabled(newState);

                if (newState) {
                    WindowsNotification.initTrayIcon();
                    trayIcon.setImage(rageIconDisabled);
                    toggleRageModeItem.setEnabled(false);
                } else {
                    WindowsNotification.removeTrayIcon();
                    if (FilteringModeManager.isRageModeEnabled()) {
                        trayIcon.setImage(rageIconEnabled);
                    } else {
                        trayIcon.setImage(loyalModeDisabled);
                        toggleRageModeItem.setEnabled(true);
                    }
                }
            }
        });
    }
}
