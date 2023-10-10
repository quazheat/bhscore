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
    private boolean loyalModeEnabled = false;

    public void setupSystemTray() {
        TrayIconLoader iconLoader = new TrayIconLoader();
        Image appIcon = iconLoader.loadIcon();
        if (SystemTray.isSupported()) {
            MenuItem showEditor = new MenuItem("Show Editor");
            MenuItem showReports = new MenuItem("Report");
            MenuItem closeMenu = new MenuItem("Hide menu");
            MenuItem exitItem = new MenuItem("Exit program");

            // Создание подменю Modes
            Menu modesMenu = new Menu("Modes");
            CheckboxMenuItem toggleRageModeItem = new CheckboxMenuItem("Rage Mode");
            CheckboxMenuItem toggleLoyalModeItem = new CheckboxMenuItem("Loyal Mode");
            modesMenu.add(toggleRageModeItem);
            modesMenu.add(toggleLoyalModeItem);

            // Создание подменю Features
            Menu featuresMenu = new Menu("Features");
            featuresMenu.add(showEditor);
            featuresMenu.add(showReports);

            // Добавление пунктов меню к подменю Modes
            modesMenu.add(toggleRageModeItem);
            modesMenu.add(toggleLoyalModeItem);

            // Добавление подменю к главному меню
            PopupMenu popupMenu = new PopupMenu();
            popupMenu.add(modesMenu); // Добавляем подменю Modes
            popupMenu.add(featuresMenu); // Добавляем подменю Features
            popupMenu.addSeparator(); // Разделитель между подменю и другими пунктами меню
            popupMenu.add(closeMenu);
            popupMenu.add(exitItem);

            // Добавление главного меню в TrayIcon
            TrayIcon trayIcon = new TrayIcon(appIcon, "BHScore", popupMenu);
            SystemTray tray = SystemTray.getSystemTray();

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
            Image loyalModeDisabled = iconLoader.loadIcon();

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
                    trayIcon.setImage(loyalModeDisabled);
                }

                rageModeEnabled = newState;
                loyalModeEnabled = false;
                toggleLoyalModeItem.setState(false);
                toggleLoyalModeItem.setEnabled(false);
                if (rageModeEnabled) {
                    WindowsNotification.initTrayIcon();
                    trayIcon.setImage(rageIconEnabled);
                } else {
                    toggleLoyalModeItem.setEnabled(true);
                    trayIcon.setImage(loyalModeDisabled);
                }

                Filtering.toggleRageMode();
                MessageProcessor.toggleRageMode();

            });

            toggleLoyalModeItem.addItemListener(e -> {
                boolean newState = toggleLoyalModeItem.getState();
                boolean rageMode = Filtering.isRage();

                if (loyalModeEnabled && !newState) {
                    WindowsNotification.removeTrayIcon();
                    if (rageMode) {
                        trayIcon.setImage(rageIconEnabled);
                    } else
                        trayIcon.setImage(loyalModeDisabled);
                }

                loyalModeEnabled = newState;
                if (loyalModeEnabled) {
                    WindowsNotification.initTrayIcon();
                    trayIcon.setImage(rageIconDisabled);

                    rageModeEnabled = false;
                    toggleRageModeItem.setState(false);
                    toggleRageModeItem.setEnabled(false);
                    if (rageModeEnabled) {
                        rageModeEnabled = false;
                    }
                } else {
                    // Вернуть доступность toggleRageModeItem
                    toggleRageModeItem.setEnabled(true);
                    trayIcon.setImage(loyalModeDisabled);
                }

                Filtering.togglLoyalMode();
                MessageProcessor.togglLoyalMode();
            });
        }
        iconLoader.imageAutoSize(true);
    }
}
