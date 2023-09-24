package fr.openai.runtime;

import fr.openai.database.TrayIconLoader;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SystemTrayManager {
    private volatile boolean stopRequested = false;

    public void setupSystemTray() {
        TrayIconLoader iconLoader = new TrayIconLoader();
        Image icon = iconLoader.loadIcon();

        if (SystemTray.isSupported()) {
            SystemTray tray = SystemTray.getSystemTray();

            PopupMenu popupMenu = new PopupMenu();

            // Создаем кастомный MenuItem с символом "x" внутри
            MenuItem closeMenu = new MenuItem("Hide");

            // Устанавливаем ActionListener для закрытия меню
            closeMenu.addActionListener(e -> {
                // Закрыть поп-ап меню без завершения программы
            });


            MenuItem exitItem = new MenuItem("Exit");
            popupMenu.add(exitItem);
            popupMenu.add(closeMenu);

            exitItem.addActionListener(e -> {
                stopRequested = true;
                System.exit(1);
            });

            TrayIcon trayIcon = new TrayIcon(icon, "BHScore", popupMenu);

            try {
                tray.add(trayIcon);
            } catch (AWTException e) {
                e.printStackTrace();
            }
        }


        if (icon != null) {
            iconLoader.configureImageAutoSize(true);
        }
    }
}