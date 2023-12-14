package fr.openai.n;

import fr.openai.b.files.aa;
import fr.openai.ff.Bst;

import java.awt.*;
import java.awt.event.*;

public class W {
    private static TrayIcon ti;

    public static void dqps() {
        if (SystemTray.isSupported()) {
            SystemTray t = SystemTray.getSystemTray();
            aa ia = new aa();
            Image zx = ia.dAA();
            if (Bst.efz()) {
                ti = new TrayIcon(zx, "BHScore mode: RAGE");
            }
            if (Bst.ao()) {
                ti = new TrayIcon(zx, "BHScore mode: LOYAL");
            }
            ti.addMouseListener(new MouseAdapter() {
            });

            PopupMenu a = new PopupMenu();
            ti.setPopupMenu(a);

            try {
                t.add(ti);
            } catch (AWTException e) {
                System.err.println("Failed");
            }
        }
    }

    public static void sW(String t, String k, TrayIcon.MessageType ty) {
        if (ti != null) {
            ti.displayMessage(t, k, ty);
        }
    }

    public static void sdq() {
        if (ti != null) {
            SystemTray y = SystemTray.getSystemTray();
            y.remove(ti);
            ti = null;
        }
    }
}
