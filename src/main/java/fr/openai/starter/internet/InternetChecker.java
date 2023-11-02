package fr.openai.starter.internet;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;

public class InternetChecker {
    public static boolean isReachable(String url) {
        HttpURLConnection connection = null;
        try {
            URI siteURI = new URI(url);
            connection = (HttpURLConnection) siteURI.toURL().openConnection();
            connection.setRequestMethod("HEAD");
            connection.setConnectTimeout(10000); // Set the connection timeout
            int responseCode = connection.getResponseCode();
            return responseCode == HttpURLConnection.HTTP_OK;
        } catch (IOException | URISyntaxException e) {
            return false;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
