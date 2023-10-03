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
        return iconFile.exists() ? loadIconFromFile(iconFile) : loadTrayIconFromURL();
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
        // Загрузка и сохранение изображения
        try {
            URL imageURL = new URL("https://cdn-icons-png.flaticon.com/128/7505/7505050.png");
            InputStream in = imageURL.openStream();
            Path imagePath = Path.of("tray_icon.png"); // Имя файла в корневой папке программы

            Files.copy(in, imagePath, StandardCopyOption.REPLACE_EXISTING);

            return Toolkit.getDefaultToolkit().getImage(imagePath.toString());
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
