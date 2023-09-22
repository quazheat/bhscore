package fr.openai.runtime;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.*;
import java.util.List;

public class DbSaver {
    private static final String DB_FILE_PATH = "floodDB.json";

    public static void saveDatabase(List<JSONObject> database) {
        JSONArray jsonArray = new JSONArray();
        jsonArray.addAll(database);

        try (FileWriter fileWriter = new FileWriter(DB_FILE_PATH)) {
            String newline = System.getProperty("line.separator");

            fileWriter.write("[" + newline);

            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject jsonMessage = (JSONObject) jsonArray.get(i);
                fileWriter.write("  " + jsonMessage.toJSONString());

                if (i < jsonArray.size() - 1) {
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
