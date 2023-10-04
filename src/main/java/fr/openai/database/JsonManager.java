package fr.openai.database;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class JsonManager {
    public static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public void isExist(String filePath) {
        File file = new File(filePath);
    }

    public JsonObject parseJsonFile(String filePath) {
        try (FileReader reader = new FileReader(filePath)) {
            return gson.fromJson(reader, JsonObject.class);
        } catch (IOException e) {
            System.err.println("Failed to read JSON file: " + filePath);
            e.printStackTrace();
        } catch (JsonIOException | JsonSyntaxException e) {
            System.err.println("Failed to parse JSON file: " + filePath);
            e.printStackTrace();
        }
        return null; // Возвращаем null в случае ошибки
    }

    public void createDefaultJsonFile(String filePath, List<String> defaultWords, List<String> whitelistWords) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("forbidden_words", gson.toJsonTree(defaultWords));
        jsonObject.add("whitelist", gson.toJsonTree(whitelistWords)); // Добавляем новый массив whitelist
        try (FileWriter fileWriter = new FileWriter(filePath)) {
            gson.toJson(jsonObject, fileWriter);
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
