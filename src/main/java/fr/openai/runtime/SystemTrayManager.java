package fr.openai.runtime;

import fr.openai.database.files.ConnectDb;
import fr.openai.database.files.TrayIconLoader;
import fr.openai.ui.panels.Menu;

import java.awt.*;

public class SystemTrayManager {
    private Menu menu;

    public void setupSystemTray() {
        TrayIconLoader iconLoader = new TrayIconLoader();
        Image appIcon = iconLoader.loadIcon();
        if (SystemTray.isSupported()) {
            MenuItem showEditor = new MenuItem("menu");
            MenuItem closeMenu = new MenuItem("hide");
            MenuItem exitItem = new MenuItem("exit program");

            // Создание подменю Modes
            java.awt.Menu modesMenu = new java.awt.Menu("Modes");
            CheckboxMenuItem toggleRageModeItem = new CheckboxMenuItem("Rage Mode");
            CheckboxMenuItem toggleLoyalModeItem = new CheckboxMenuItem("Loyal Mode");
            modesMenu.add(toggleRageModeItem);
            modesMenu.add(toggleLoyalModeItem);

            // Добавление пунктов меню к подменю Modes
            modesMenu.add(toggleRageModeItem);
            modesMenu.add(toggleLoyalModeItem);

            // Добавление подменю к главному меню
            PopupMenu popupMenu = new PopupMenu();
            popupMenu.add(modesMenu); // Добавляем подменю Modes
            popupMenu.add(showEditor); //
            popupMenu.addSeparator(); // Разделитель между подменю и другими пунктами меню
            popupMenu.add(closeMenu);
            popupMenu.add(exitItem);

            // Добавление главного меню в TrayIcon
            TrayIcon trayIcon = new TrayIcon(appIcon, "BHScore", popupMenu);
            SystemTray tray = SystemTray.getSystemTray();

            exitItem.addActionListener(e -> {
                System.exit(0);
            });

            Image rageIconEnabled = iconLoader.loadRageIcon();
            Image rageIconDisabled = iconLoader.loadRageIconDisabled();
            Image loyalModeDisabled = iconLoader.loadIcon();

            try {
                tray.add(trayIcon);
            } catch (AWTException e) {
                e.printStackTrace();
            }
            showEditor.addActionListener(e -> {
                if (menu == null) {
                    menu = new Menu(trayIcon);
                    return;
                }

                menu.setVisible(true);
            });

            // Create a TrayIconManager instance and set up icon listeners
            TrayIconManager iconManager = new TrayIconManager(trayIcon, rageIconEnabled, rageIconDisabled, loyalModeDisabled);
            iconManager.setupIconListeners(toggleRageModeItem, toggleLoyalModeItem);
        }
        iconLoader.setImageAutoSize(true);
    }
}
