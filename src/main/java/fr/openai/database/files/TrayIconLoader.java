package fr.openai.database.files;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class TrayIconLoader {
    public Image loadIcon() {
        // Проверяем наличие файла tray_icon.png в папке программы
        File iconFile = new File("tray_icon.png");

        if (iconFile.exists()) {
            return loadIconFromFile(iconFile);
        } else {
            return loadTrayIconFromURL();
        }
    }

    public Image loadRageIcon() {
        return loadEnabledIcon();
    }

    public Image loadRageIconDisabled() {
        try {
            URL rageURL = new URL("https://cdn-icons-png.flaticon.com/128/2164/2164313.png");
            InputStream rageIn = rageURL.openStream();
            Path ragePath = Path.of("rage_mode_disabled.png");
            Files.copy(rageIn, ragePath, StandardCopyOption.REPLACE_EXISTING);
            return Toolkit.getDefaultToolkit().getImage(ragePath.toString());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Image loadEnabledIcon() {
        try {
            URL rageURL = new URL("https://cdn-icons-png.flaticon.com/128/982/982989.png");
            InputStream rageIn = rageURL.openStream();
            Path ragePath = Path.of("rage_mode.png");
            Files.copy(rageIn, ragePath, StandardCopyOption.REPLACE_EXISTING);
            return Toolkit.getDefaultToolkit().getImage(ragePath.toString());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Image loadIconFromFile(File iconFile) {
        try {
            return ImageIO.read(iconFile);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Image loadTrayIconFromURL() {
        try {
            URL iconURL = new URL("https://cdn-icons-png.flaticon.com/128/2839/2839162.png");
            InputStream iconIn = iconURL.openStream();
            Path iconPath = Path.of("tray_icon.png");
            Files.copy(iconIn, iconPath, StandardCopyOption.REPLACE_EXISTING);
            return Toolkit.getDefaultToolkit().getImage(iconPath.toString());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void imageAutoSize(boolean imageAutoSize) {
        TrayIcon trayIcon = SystemTray.getSystemTray().getTrayIcons()[0];
        trayIcon.setImageAutoSize(imageAutoSize);
    }
}
