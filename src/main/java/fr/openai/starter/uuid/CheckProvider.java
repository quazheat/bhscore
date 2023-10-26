package fr.openai.starter.uuid;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URI; // Import URI
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class CheckProvider {
    private final UuidProvider uuidProvider = new UuidProvider();
    private static final String PASTE_URL = "https://pastebin.com/raw/SnZw6TtD";

    public List<String> getUuuidList() throws IOException, URISyntaxException {
        HttpURLConnection connection = null;
        try {
            URI uri = new URI(PASTE_URL); // Create a URI from the URL string
            URL url = uri.toURL(); // Convert URI to URL
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    List<String> allowedUuids = reader.lines().collect(Collectors.toList());

                    // Выводим информацию в консоль

                    // Получаем и выводим UUID компьютера
                    UUID systemUUID = uuidProvider.getUUID();
                    if (systemUUID != null) {
                        System.out.println("System UUID: " + systemUUID);
                    } else {
                        System.out.println("System UUID not found.");
                    }

                    return allowedUuids;
                }
            } else {
                throw new IOException("Failed to fetch allowed UUIDs");
            }
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
