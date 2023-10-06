package fr.openai.runtime;

import fr.openai.database.files.TicketSubmissionApp;
import fr.openai.database.files.TrayIconLoader;
import fr.openai.database.editor.Editor;
import fr.openai.filter.Filtering;
import fr.openai.notify.WindowsNotification;
import fr.openai.starter.logs.ConsoleLogger;

import java.awt.*;

public class SystemTrayManager {
    private volatile boolean stopRequested = false;
    private Editor editor;
    private TicketSubmissionApp ticketApp;
    private boolean rageModeEnabled = false;

    public void setupSystemTray() {
        TrayIconLoader iconLoader = new TrayIconLoader();
        Image appIcon = iconLoader.loadIcon();

        if (SystemTray.isSupported()) {
            SystemTray tray = SystemTray.getSystemTray();

            PopupMenu popupMenu = new PopupMenu();

            MenuItem showEditor = new MenuItem("Show Editor");
            popupMenu.add(showEditor);

            MenuItem showReports = new MenuItem("Report");
            popupMenu.add(showReports);

            CheckboxMenuItem toggleRageModeItem = new CheckboxMenuItem("Rage Mode");
            popupMenu.add(toggleRageModeItem);

            MenuItem closeMenu = new MenuItem("Hide menu");
            popupMenu.add(closeMenu);

            MenuItem exitItem = new MenuItem("Exit program");
            popupMenu.add(exitItem);

            exitItem.addActionListener(e -> {
                try {
                    if (this.ticketApp != null) {
                        this.ticketApp.closeDb();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                stopRequested = true;
                ConsoleLogger.consoleLog();
                System.exit(0);

            });

            Image rageIconEnabled = iconLoader.loadRageIcon();
            Image rageIconDisabled = iconLoader.loadRageIconDisabled();

            TrayIcon trayIcon = new TrayIcon(appIcon, "BHScore", popupMenu);
            try {
                tray.add(trayIcon);
            } catch (AWTException e) {
                e.printStackTrace();
            }

            showEditor.addActionListener(e -> {
                if (editor == null) {
                    editor = new Editor();
                } else {
                    editor.setVisible(true);
                }
            });

            showReports.addActionListener(e -> {
                if (ticketApp == null) {
                    ticketApp = new TicketSubmissionApp();
                }
                ticketApp.showAppWindow();
            });

            toggleRageModeItem.addItemListener(e -> {
                boolean newState = toggleRageModeItem.getState();

                if (rageModeEnabled && !newState) {
                    WindowsNotification.removeTrayIcon();
                    trayIcon.setImage(rageIconDisabled);
                }

                rageModeEnabled = newState;
                if (rageModeEnabled) {
                    WindowsNotification.initTrayIcon();
                    trayIcon.setImage(rageIconEnabled);
                }

                Filtering.toggleRageMode();
            });
        }
        iconLoader.imageAutoSize(true);
    }
}
