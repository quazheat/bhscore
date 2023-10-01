package fr.openai.starter.uuid;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class CheckProvider {
    private static final String PASTE_URL = "https://pastebin.com/raw/SnZw6TtD";

    public List<String> getUuuidList() throws IOException {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(PASTE_URL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    List<String> allowedUuids = reader.lines().collect(Collectors.toList());

                    // Выводим информацию в консоль
                    System.out.println("Allowed UUIDs:");
                    allowedUuids.forEach(System.out::println);

                    // Получаем и выводим UUID компьютера
                    UUID systemUUID = UuidProvider.getUUID();
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
