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

            // Set an empty mouse event handler to prevent clicks
            trayIcon.addMouseListener(new MouseAdapter() {});

            // Set an empty mouse event handler for the popup menu
            PopupMenu popupMenu = new PopupMenu();
            trayIcon.setPopupMenu(popupMenu);

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
