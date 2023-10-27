package fr.openai.database;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileReader;
import java.io.IOException;

public class JsonFileReader {
    public static JsonObject readJsonFile(String filename) {
        try {
            return JsonParser.parseReader(new FileReader(filename)).getAsJsonObject();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error reading JSON file: " + filename);
        }
    }
}
