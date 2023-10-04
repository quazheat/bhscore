package fr.openai.database;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class DatabaseManager {
    private static final String JSON_FILE_PATH = "livechat.json";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void saveMessages(JsonArray jsonArray) {
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(JSON_FILE_PATH), StandardCharsets.UTF_8))) {
            gson.toJson(jsonArray, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static JsonArray loadMessages() {
        JsonArray jsonArray = new JsonArray();

        try {
            File file = new File(JSON_FILE_PATH);
            if (file.exists() && file.length() > 0) {
                BufferedReader reader = new BufferedReader(new FileReader(JSON_FILE_PATH));
                jsonArray = JsonParser.parseReader(reader).getAsJsonArray();
                reader.close();
            }
        } catch (IOException | JsonIOException | JsonSyntaxException e) {
            e.printStackTrace();
        }

        return jsonArray;
    }
}
