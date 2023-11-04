package fr.openai.starter.uuid;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ProviderCheck {
    private final UuidProvider uuidProvider = new UuidProvider();
    private static final String PASTE_URL = "https://pastebin.com/raw/SnZw6TtD";

    public List<String> getUuuidList() throws IOException, URISyntaxException {
        HttpURLConnection connection = null;
        try {
            URI uri = new URI(PASTE_URL);
            URL url = uri.toURL();
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                throw new IOException("Failed to fetch allowed UUIDs");
            }

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                List<String> allowedUuids = reader.lines().collect(Collectors.toList());
                printSystemUUID();
                return allowedUuids;
            }
        } finally {
            assert connection != null;
            connection.disconnect();
        }
    }

    private void printSystemUUID() {    
        UUID systemUUID = uuidProvider.getUUID();
        System.out.println(systemUUID != null ? "System UUID: " + systemUUID : "System UUID not found.");
    }
}
