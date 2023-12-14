package fr.openai.n;

import fr.openai.b.files.aa;
import fr.openai.ff.Bst;

import java.awt.*;
import java.awt.event.*;

public class W {
    private static TrayIcon trayIcon;

    public static void dqps() {
        if (SystemTray.isSupported()) {
            SystemTray tray = SystemTray.getSystemTray();
            aa iconLoader = new aa();
            Image icon = iconLoader.dAA();
            if (Bst.efz()) {
                trayIcon = new TrayIcon(icon, "BHScore mode: RAGE");
            }
            if (Bst.ao()) {
                trayIcon = new TrayIcon(icon, "BHScore mode: LOYAL");
            }
            trayIcon.addMouseListener(new MouseAdapter() {
            });

            PopupMenu a = new PopupMenu();
            trayIcon.setPopupMenu(a);

            try {
                tray.add(trayIcon);
            } catch (AWTException e) {
                System.err.println("Failed");
            }
        }
    }

    public static void sW(String t, String k, TrayIcon.MessageType ty) {
        if (trayIcon != null) {
            trayIcon.displayMessage(t, k, ty);
        }
    }

    public static void sdq() {
        if (trayIcon != null) {
            SystemTray tray = SystemTray.getSystemTray();
            tray.remove(trayIcon);
            trayIcon = null;
        }
    }
}
