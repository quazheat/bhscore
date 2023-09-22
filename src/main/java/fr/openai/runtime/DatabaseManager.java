package fr.openai.runtime;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;

public class DatabaseManager {
    private static final String JSON_FILE_PATH = "floodDB.json";

    public static void saveMessages(JSONArray jsonArray) {
        try (FileWriter fileWriter = new FileWriter(JSON_FILE_PATH)) {
            String newline = System.getProperty("line.separator");

            fileWriter.write("[" + newline);

            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject jsonMessage = (JSONObject) jsonArray.get(i);
                fileWriter.write(jsonMessage.toJSONString());

                if (i < jsonArray.size() - 1) {
                    fileWriter.write("," + newline);
                }
            }

            fileWriter.write("]" + newline);

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