package fr.openai.database;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class IpAddressUtil {
    private static String userPublicIpAddress;

    public static String getUserPublicIpAddress() {
        if (userPublicIpAddress == null) {
            userPublicIpAddress = fetchUserPublicIpAddress();
        }
        return userPublicIpAddress;
    }

    private static String fetchUserPublicIpAddress() {
        try {
            URI uri = new URI("https://httpbin.org/ip");
            URL url = uri.toURL();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    String line;
                    StringBuilder response = new StringBuilder();
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }

                    // Parse the JSON response to extract the IP address
                    String jsonResponse = response.toString();
                    int startIndex = jsonResponse.indexOf(":") + 2;
                    int endIndex = jsonResponse.indexOf("}");
                    return jsonResponse.substring(startIndex, endIndex);
                }
            }
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return "UNKNOWN"; // Return "UNKNOWN" if unable to determine the IP address
    }
}
