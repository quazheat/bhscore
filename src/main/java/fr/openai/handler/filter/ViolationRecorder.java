package fr.openai.handler.filter;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;

public class ViolationRecorder {
    private static final String VIOLATIONS_PATH = "violations.json";

    public boolean isRecorded(String playerName, String message) {
        try {
            FileReader reader = new FileReader(VIOLATIONS_PATH);
            JsonArray jsonArray = JsonParser.parseReader(reader).getAsJsonArray();

            for (int i = 0; i < jsonArray.size(); i++) {
                JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
                String rNick = jsonObject.get("player_name").getAsString();
                String rMessage = jsonObject.get("message").getAsString();

                if (rNick.equals(playerName) && rMessage.equals(message)) {
                    return true;
                }
            }
        } catch (IOException e) {
            // Если это FileNotFoundException, ждем 0.1 секунды
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }

        return false;
    }


    public void recordViolation(String playerName, String message) {
        long currentTimestamp = System.currentTimeMillis() / 1000L;

        JsonArray jsonArray;

        File violationsFile = new File(VIOLATIONS_PATH);
        if (!violationsFile.exists()) {
            jsonArray = new JsonArray();
        } else {
            try (FileReader reader = new FileReader(VIOLATIONS_PATH)) {
                jsonArray = JsonParser.parseReader(reader).getAsJsonArray();
            } catch (IOException e) {
                e.printStackTrace();
                jsonArray = new JsonArray();
            }
        }

        JsonObject newViolation = new JsonObject();
        newViolation.addProperty("player_name", playerName);
        newViolation.addProperty("message", message);
        newViolation.addProperty("timestamp", currentTimestamp);

        jsonArray.add(newViolation);

        try (FileWriter writer = new FileWriter(VIOLATIONS_PATH)) {
            writer.write(jsonArray.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
