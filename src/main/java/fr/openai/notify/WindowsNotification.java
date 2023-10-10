package fr.openai.notify;

import fr.openai.database.files.TrayIconLoader;

import java.awt.*;
import java.awt.event.*;


public class WindowsNotification {
    private static TrayIcon trayIcon;

    public static void initTrayIcon() {
        if (SystemTray.isSupported()) {
            SystemTray tray = SystemTray.getSystemTray();
            TrayIconLoader iconLoader = new TrayIconLoader();
            Image icon = iconLoader.loadRageIcon();

            trayIcon = new TrayIcon(icon, "DO NOT LOOK AT ME");

            // Устанавливаем пустой обработчик событий мыши, чтобы предотвратить клики
            trayIcon.addMouseListener(new MouseAdapter() {});

            // Устанавливаем пустой обработчик событий мыши для попап-меню
            PopupMenu popupMenu = new PopupMenu();
            trayIcon.setPopupMenu(popupMenu);

            // Убираем изображение иконки


            try {
                tray.add(trayIcon);
            } catch (AWTException e) {
                System.err.println("Failed to add TrayIcon.");
            }
        } else {
            System.err.println("System tray is not supported.");
        }
    }


    public static void showWindowsNotification(String title, String message, TrayIcon.MessageType messageType) {
        if (trayIcon != null) {
            trayIcon.displayMessage(title, message, messageType);
        } else {
            System.err.println("TrayIcon is not initialized.");
        }
    }

    public static void removeTrayIcon() {
        if (trayIcon != null) {
            SystemTray tray = SystemTray.getSystemTray();
            tray.remove(trayIcon);
            trayIcon = null;
        }
    }
}
