package fr.openai.database;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class JsonManager {
    public boolean isExist(String filePath) {
        File file = new File(filePath);
        return file.exists();
    }

    public JSONObject parseJsonFile(String filePath) {
        try (FileReader reader = new FileReader(filePath)) {
            JSONParser parser = new JSONParser();
            return (JSONObject) parser.parse(reader);
        } catch (IOException e) {
            System.err.println("Failed to read JSON file: " + filePath);
            e.printStackTrace();
        } catch (org.json.simple.parser.ParseException e) {
            System.err.println("Failed to parse JSON file: " + filePath);
            e.printStackTrace();
        }
        return null; // Возвращаем null в случае ошибки
    }

    public void createDefaultJsonFile(String filePath, List<String> defaultWords) {
        JSONArray defaultWordsArray = new JSONArray();
        defaultWordsArray.addAll(defaultWords);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("forbidden_words", defaultWordsArray);

        try (FileWriter fileWriter = new FileWriter(filePath)) {
            jsonObject.writeJSONString(fileWriter);
        } catch (IOException e) {
            System.err.println("Failed to create JSON file: " + filePath);
            e.printStackTrace();
        }
    }
    public long getFileSize(String filePath) {
        File file = new File(filePath);
        return file.length();
    }




}
