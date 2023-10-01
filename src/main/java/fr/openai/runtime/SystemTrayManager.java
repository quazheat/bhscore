package fr.openai.runtime;

import fr.openai.database.files.TrayIconLoader;
import fr.openai.database.editor.Editor; // Импортируйте ваш класс Editor

import java.awt.*;

public class SystemTrayManager {
    private volatile boolean stopRequested = false;
    private Editor editor; // Создайте поле для хранения экземпляра Editor

    public void setupSystemTray() {
        TrayIconLoader iconLoader = new TrayIconLoader();
        Image icon = iconLoader.loadIcon();

        if (SystemTray.isSupported()) {
            SystemTray tray = SystemTray.getSystemTray();

            PopupMenu popupMenu = new PopupMenu();

            MenuItem showEditorItem = new MenuItem("Show Editor"); // Добавьте пункт меню для отображения Editor
            popupMenu.add(showEditorItem);

            // Создаем кастомный MenuItem с символом "x" внутри
            MenuItem closeMenu = new MenuItem("Hide menu");

            // Устанавливаем ActionListener для закрытия меню
            closeMenu.addActionListener(e -> {
                // Закрыть поп-ап меню без завершения программы
            });

            MenuItem exitItem = new MenuItem("Exit program");
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

            showEditorItem.addActionListener(e -> {
                // Создайте и отобразите экземпляр Editor при выборе "Show Editor"
                if (editor == null) {
                    editor = new Editor();
                } else {
                    editor.setVisible(true); //
                }
            });
        }
        if (icon != null) {
            iconLoader.configureImageAutoSize(true);
        }
    }

    public static void main(String[] args) {
        SystemTrayManager trayManager = new SystemTrayManager();
        trayManager.setupSystemTray();
    }
}
