package fr.openai.b.files;

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

public class aa {
    public final String var1 = "https://cdn-icons-png.flaticon.com/128/2839/2839162.png";
    public final String var2 = "https://cdn-icons-png.flaticon.com/128/982/982989.png";
    public final String var3 = "https://cdn-icons-png.flaticon.com/128/2164/2164313.png";
    public final String var4 = "tray_icon.png";
    public final String var5 = "rage_mode.png";
    public final String var7 = "rage_mode_disabled.png";

    public Image aA() {
        File aa = new File(var4);

        if (aa.exists()) {
            return aAd(aa);
        } else {
            return aaD(var1, var4);
        }
    }

    public Image dAA() {
        return aaD(var2, var5);
    }

    public Image zxc() {
        return aaD(var3, var7);
    }

    private Image aaD(String z, String x) {
        try {
            URI df = new URI(z);
            URL qwd = df.toURL();

            HttpURLConnection sd = (HttpURLConnection) qwd.openConnection();
            int sdqD = sd.getResponseCode();

            if (sdqD == HttpURLConnection.HTTP_OK) {
                try (InputStream daq = sd.getInputStream()) {
                    Path qweP = Path.of(x);
                    Files.copy(daq, qweP, StandardCopyOption.REPLACE_EXISTING);
                    return Toolkit.getDefaultToolkit().getImage(qweP.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
            System.err.println(" " + sdqD);
            return null;

        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Image aAd(File iconFile) {
        try {
            return ImageIO.read(iconFile);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void asZd(boolean imageAutoSize) {
        TrayIcon aa = SystemTray.getSystemTray().getTrayIcons()[0];
        aa.setImageAutoSize(imageAutoSize);
    }

}
