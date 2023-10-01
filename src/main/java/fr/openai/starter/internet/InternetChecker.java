package fr.openai.starter.internet;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class InternetChecker {
    public static boolean isReachable(String url) {
        HttpURLConnection connection = null;
        try {
            URL siteURL = new URL(url);
            connection = (HttpURLConnection) siteURL.openConnection();
            connection.setRequestMethod("HEAD");
            connection.setConnectTimeout(2000); // Установите таймаут соединения по вашему усмотрению
            int responseCode = connection.getResponseCode();
            return responseCode == HttpURLConnection.HTTP_OK;
        } catch (IOException e) {
            return false;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
