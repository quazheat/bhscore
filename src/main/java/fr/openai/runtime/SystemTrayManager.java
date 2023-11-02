package fr.openai.runtime;

import fr.openai.database.files.TrayIconLoader;
import fr.openai.database.editor.Editor;
import fr.openai.starter.logs.ConsoleLogger;

import java.awt.*;

public class SystemTrayManager {
    private Editor editor;

    public void setupSystemTray() {
        TrayIconLoader iconLoader = new TrayIconLoader();
        Image appIcon = iconLoader.loadIcon();
        if (SystemTray.isSupported()) {
            MenuItem showEditor = new MenuItem("menu");
            MenuItem closeMenu = new MenuItem("hide");
            MenuItem exitItem = new MenuItem("exit program");

            // Создание подменю Modes
            Menu modesMenu = new Menu("Modes");
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
                //ConsoleLogger.consoleLog();
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
                if (editor == null) {
                    editor = new Editor(trayIcon);
                    return;
                }

                editor.setVisible(true);
            });

            // Create a TrayIconManager instance and set up icon listeners
            TrayIconManager iconManager = new TrayIconManager(trayIcon, rageIconEnabled, rageIconDisabled, loyalModeDisabled);
            iconManager.setupIconListeners(toggleRageModeItem, toggleLoyalModeItem);
        }
        iconLoader.setImageAutoSize(true);
    }
}
