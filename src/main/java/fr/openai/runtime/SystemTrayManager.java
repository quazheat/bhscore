package fr.openai.runtime;

import fr.openai.database.files.TicketSubmissionApp;
import fr.openai.database.files.TrayIconLoader;
import fr.openai.database.editor.Editor;

import java.awt.*;

public class SystemTrayManager {
    private volatile boolean stopRequested = false;

    private Editor editor;
    private TicketSubmissionApp ticketApp;


    public void setupSystemTray() {
        TrayIconLoader iconLoader = new TrayIconLoader();
        Image icon = iconLoader.loadIcon();

        if (SystemTray.isSupported()) {
            SystemTray tray = SystemTray.getSystemTray();

            PopupMenu popupMenu = new PopupMenu();

            MenuItem showEditor = new MenuItem("Show Editor");
            popupMenu.add(showEditor);

            MenuItem showReports = new MenuItem("Report");
            popupMenu.add(showReports);

            MenuItem closeMenu = new MenuItem("Hide menu");

            closeMenu.addActionListener(e -> {
                // Закрыть поп-ап меню без завершения программы
            });

            MenuItem exitItem = new MenuItem("Exit program");
            popupMenu.add(exitItem);
            popupMenu.add(closeMenu);

            exitItem.addActionListener(e -> {
                try {
                    if (this.ticketApp != null) {
                        this.ticketApp.closeDb();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace(); // Здесь можно обработать исключение, вывести сообщение или выполнить другие действия
                }

                stopRequested = true;
                System.exit(1);
            });

            TrayIcon trayIcon = new TrayIcon(icon, "BHScore", popupMenu);
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
                    ticketApp = new TicketSubmissionApp(); // Создайте экземпляр TicketSubmissionApp
                }
                ticketApp.showAppWindow(); // Вызовите метод для отображения окна системы тикетов
            });

        }
        if (icon != null) {
            iconLoader.imageAutoSize(true);
        }
    }
}