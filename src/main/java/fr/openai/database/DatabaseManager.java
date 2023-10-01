package fr.openai.database;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;

public class DatabaseManager {
    private static final String JSON_FILE_PATH = "livechat.json";

    public static void saveMessages(JSONArray jsonArray) {
        try (FileWriter fileWriter = new FileWriter(JSON_FILE_PATH)) {
            fileWriter.write(jsonArray.toJSONString());
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static JSONArray loadMessages() {
        JSONArray jsonArray = new JSONArray();

        try {
            File file = new File(JSON_FILE_PATH);
            if (file.exists() && file.length() > 0) {
                BufferedReader reader = new BufferedReader(new FileReader(JSON_FILE_PATH));
                StringBuilder jsonContent = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    jsonContent.append(line);
                }
                reader.close();

                JSONParser parser = new JSONParser();
                jsonArray = (JSONArray) parser.parse(jsonContent.toString());
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return jsonArray;
    }
}