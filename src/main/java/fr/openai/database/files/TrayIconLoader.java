package fr.openai.database.files;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class TrayIconLoader {
    public final String TRAY_ICON_URL = "https://cdn-icons-png.flaticon.com/128/2839/2839162.png";
    public final String RAGE_ICON_ENABLED_URL = "https://cdn-icons-png.flaticon.com/128/982/982989.png";
    public final String RAGE_ICON_DISABLED_URL = "https://cdn-icons-png.flaticon.com/128/2164/2164313.png";
    public final String ICON_FILE_NAME = "tray_icon.png";
    public final String RAGE_ICON_NAME = "rage_mode.png";
    public final String RAGE_ICON_DISABLED_NAME = "rage_mode_disabled.png";

    public Image loadIcon() {
        File iconFile = new File(ICON_FILE_NAME);

        if (iconFile.exists()) {
            return loadImageFromFile(iconFile);
        } else {
            return loadImageFromURL(TRAY_ICON_URL, ICON_FILE_NAME);
        }
    }

    public Image loadRageIcon() {
        return loadImageFromURL(RAGE_ICON_ENABLED_URL, RAGE_ICON_NAME);
    }

    public Image loadRageIconDisabled() {
        return loadImageFromURL(RAGE_ICON_DISABLED_URL, RAGE_ICON_DISABLED_NAME);
    }

    private Image loadImageFromURL(String url, String iconName) {
        try {
            URI iconURI = new URI(url);
            URL iconURL = iconURI.toURL();

            HttpURLConnection connection = (HttpURLConnection) iconURL.openConnection();
            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (InputStream iconIn = connection.getInputStream()) {
                    Path iconPath = Path.of(iconName);
                    Files.copy(iconIn, iconPath, StandardCopyOption.REPLACE_EXISTING);
                    return Toolkit.getDefaultToolkit().getImage(iconPath.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
            System.err.println("Failed to fetch image: HTTP error code " + responseCode);
            return null;

        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Image loadImageFromFile(File iconFile) {
        try {
            return ImageIO.read(iconFile);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setImageAutoSize(boolean imageAutoSize) {
        TrayIcon trayIcon = SystemTray.getSystemTray().getTrayIcons()[0];
        trayIcon.setImageAutoSize(imageAutoSize);
    }
}
