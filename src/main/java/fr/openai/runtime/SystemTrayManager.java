package fr.openai.runtime;

import com.mongodb.client.MongoCollection;
import fr.openai.database.ConfigManager;
import fr.openai.database.ConnectDb;
import fr.openai.database.files.TrayIconLoader;
import fr.openai.database.files.TrayIconManager;
import fr.openai.discordfeatures.DiscordRPCDiag;
import fr.openai.online.DatabaseUtils;
import fr.openai.ui.MutesWarnsGUI;
import fr.openai.ui.panels.Menu;
import org.bson.Document;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SystemTrayManager {
    private Menu menu;
    private final ConfigManager configManager = new ConfigManager();
    public final String COLLECTION_NAME = "online";
    private final ConnectDb connectDb = new ConnectDb();

    public void setupSystemTray() {
        String username = configManager.getUsername();
        if (username == null || username.length() <= 3) {
            username = DiscordRPCDiag.getUsername();
        }
        String finalUsername = username;


        TrayIconLoader iconLoader = new TrayIconLoader();
        Image appIcon = iconLoader.loadIcon();
        if (SystemTray.isSupported()) {
            MenuItem showEditor = new MenuItem("menu");
            MenuItem stats = new MenuItem("my stats");
            MenuItem closeMenu = new MenuItem("hide");
            MenuItem exitItem = new MenuItem("exit program");

            java.awt.Menu modesMenu = new java.awt.Menu("Modes");
            CheckboxMenuItem toggleRageModeItem = new CheckboxMenuItem("Rage Mode");
            CheckboxMenuItem toggleLoyalModeItem = new CheckboxMenuItem("Loyal Mode");
            modesMenu.add(toggleRageModeItem);
            modesMenu.add(toggleLoyalModeItem);

            modesMenu.add(toggleRageModeItem);
            modesMenu.add(toggleLoyalModeItem);

            PopupMenu popupMenu = new PopupMenu();
            popupMenu.add(modesMenu);
            popupMenu.add(showEditor);
            popupMenu.add(stats);
            popupMenu.addSeparator(); // Разделитель между подменю и другими пунктами меню
            popupMenu.add(closeMenu);
            popupMenu.add(exitItem);

            TrayIcon trayIcon = new TrayIcon(appIcon, "BHScore", popupMenu);
            SystemTray tray = SystemTray.getSystemTray();

            Image rageIconEnabled = iconLoader.loadRageIcon();
            Image rageIconDisabled = iconLoader.loadRageIconDisabled();
            Image loyalModeDisabled = iconLoader.loadIcon();

            try {
                tray.add(trayIcon);
            } catch (AWTException e) {
                e.printStackTrace();
            }

            if (finalUsername == null || finalUsername.length() <= 3) {
                stats.setEnabled(false);
            }
            stats.addActionListener(e -> {
                MutesWarnsGUI mutesWarnsGUI = new MutesWarnsGUI();
                mutesWarnsGUI.setModal(true);
                mutesWarnsGUI.setVisible(true);

            });

            showEditor.addActionListener(e -> {
                if (menu == null) {
                    menu = new Menu(trayIcon);
                    return;
                }

                menu.setVisible(true);
            });

            trayIcon.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() == 2) { // Проверка на двойной клик
                        if (menu == null) {
                            menu = new Menu(trayIcon);
                        }
                        menu.setVisible(true);
                    }
                }
            });


            exitItem.addActionListener(e -> {


                MongoCollection<Document> collection = ConnectDb.getMongoCollection(COLLECTION_NAME);

                DatabaseUtils.deleteDocuments(collection, new Document("username", finalUsername));
                connectDb.closeMongoClient();
                System.exit(0);
            });

            TrayIconManager iconManager = new TrayIconManager(trayIcon, rageIconEnabled, rageIconDisabled, loyalModeDisabled);
            iconManager.setupIconListeners(toggleRageModeItem, toggleLoyalModeItem);
        }
        iconLoader.setImageAutoSize(true);
    }
}
