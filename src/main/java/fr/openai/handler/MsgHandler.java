package fr.openai.handler;

import fr.openai.runtime.Cleaner;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.util.*;
import java.util.Timer;
import java.util.TimerTask;

public class MsgHandler {
    private static final String DB_FILE_PATH = "floodDB.json";
    private static final double SIMILARITY_THRESHOLD = 0.6;
    private List<JSONObject> database;
    private Cleaner cleaner;

    public MsgHandler(Cleaner cleaner) {
        database = new ArrayList<>();
        this.cleaner = cleaner;
        startCheckingTask();
    }

    private void loadDatabase() {
        try {
            File file = new File(DB_FILE_PATH);
            if (!file.exists() || file.length() == 0) {
                // File is empty or doesn't exist; handle this situation accordingly.
                return;
            }

            BufferedReader reader = new BufferedReader(new FileReader(DB_FILE_PATH));
            StringBuilder jsonContent = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonContent.append(line);
            }
            reader.close();

            JSONParser parser = new JSONParser();
            JSONArray jsonArray = (JSONArray) parser.parse(jsonContent.toString());
            database.clear(); // Clear the database before loading new data
            for (int i = 0; i < jsonArray.size(); i++) {
                database.add((JSONObject) jsonArray.get(i));
            }
        } catch (IOException | org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }
    }

    private void startCheckingTask() {
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                loadDatabase(); // Periodically load data from the JSON file
                checkForFlood();
            }
        }, 0, 300); // Check every 0.3 seconds
    }

    public void checkForFlood() {
        // Flood checking implementation
        Map<String, List<JSONObject>> playerMessagesMap = new HashMap<>();
        List<JSONObject> messagesToRemove = new ArrayList<>(); // List of messages to remove
        List<JSONObject> messagesToSave = new ArrayList<>(); // List of messages to save

        for (JSONObject message : database) {
            String playerName = message.get("player_name").toString();

            if (!playerMessagesMap.containsKey(playerName)) {
                playerMessagesMap.put(playerName, new ArrayList<>());
            }

            playerMessagesMap.get(playerName).add(message);
        }

        for (List<JSONObject> messages : playerMessagesMap.values()) {
            if (messages.size() >= 3 && areMessagesSimilar(messages)) {
                boolean hasRemovedText = false;

                for (JSONObject message : messages) {
                    String messageText = message.get("message").toString();

                    // Check if the message is not empty
                    if (!messageText.isEmpty()) {
                        // Set the timestamp to 0 for all similar messages
                        message.put("timestamp", 0);
                        messagesToSave.add(message); // Add the message to the list to save
                        messagesToRemove.add(message); // Add the message to the list to remove
                    } else {
                        hasRemovedText = true;
                    }
                }

                // If at least one message contains text, don't display the flood warning
                if (!hasRemovedText) {
                    // Display the flood warning here
                    displayFloodWarning(messages);
                }
            }
        }

        // Save the database three times with a 1-millisecond delay between saves
        for (int i = 0; i < 3; i++) {
            try {
                Thread.sleep(1); // 1 millisecond delay
                saveDatabase(messagesToSave);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Remove messages from the database
        database.removeAll(messagesToRemove);

        // Call the saveDatabase method to save the database after removal
        saveDatabase(database);
    }


    public List<JSONObject> getDatabase() {
        return database;
    }

    private boolean areMessagesSimilar(List<JSONObject> messages) {
        // Message similarity comparison implementation
        String sampleMessage = messages.get(0).get("message").toString();

        for (JSONObject message : messages) {
            String currentMessage = message.get("message").toString();

            if (!areMessagesSimilar(sampleMessage, currentMessage)) {
                return false;
            }
        }

        return true;
    }

    private boolean areMessagesSimilar(String message1, String message2) {
        // Message similarity comparison implementation
        int maxLength = Math.max(message1.length(), message2.length());
        int minLength = Math.min(message1.length(), message2.length());
        int commonLength = 0;
        for (int i = 0; i < minLength; i++) {
            if (Character.toLowerCase(message1.charAt(i)) == Character.toLowerCase(message2.charAt(i))) {
                commonLength++;
            }
        }
        double similarityPercentage = (double) commonLength / maxLength;
        return similarityPercentage >= SIMILARITY_THRESHOLD;
    }

    private void displayFloodWarning(List<JSONObject> messages) {
        for (JSONObject message : messages) {
            System.out.println("FLOOD WARNING: " + message.get("player_name") + " - " + message.get("message"));
        }
    }

    private void saveDatabase(List<JSONObject> messagesToSave) {
        try (FileWriter fileWriter = new FileWriter(DB_FILE_PATH)) {
            String newline = System.getProperty("line.separator");

            fileWriter.write("[" + newline);

            for (int i = 0; i < messagesToSave.size(); i++) {
                JSONObject jsonMessage = messagesToSave.get(i);
                fileWriter.write("  " + jsonMessage.toJSONString());

                if (i < messagesToSave.size() - 1) {
                    fileWriter.write(",");
                }

                fileWriter.write(newline);
            }

            fileWriter.write("]" + newline);

            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
