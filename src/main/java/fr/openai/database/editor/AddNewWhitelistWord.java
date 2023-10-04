package fr.openai.database.editor;

import com.google.gson.JsonElement;
import fr.openai.database.JsonManager;
import fr.openai.database.editor.Editor;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.FileWriter;
import java.io.IOException;

public class AddNewWhitelistWord {
    private static final String WORDS_JSON_PATH = "words.json";
    private final Editor editor; // поле для доступа к Editor

    public AddNewWhitelistWord(Editor editor) {
        this.editor = editor;
    }

    public void addNewWhitelistWord(String newWord) {
        // Prune the new word to lowercase and remove extra characters
        newWord = newWord.toLowerCase().replaceAll("[^a-zа-яё]", "");

        // Read the current JSON from the file
        JsonManager jsonManager = new JsonManager();
        JsonObject jsonObject = jsonManager.parseJsonFile(WORDS_JSON_PATH);

        if (jsonObject != null) {
            // Get the whitelist array
            JsonArray whitelistArray = jsonObject.getAsJsonArray("whitelist");

            // Check if the word already exists in the whitelist
            boolean wordExists = false;
            for (JsonElement element : whitelistArray) {
                if (element.isJsonPrimitive() && element.getAsString().equals(newWord)) {
                    wordExists = true;
                    break;
                }
            }

            if (!wordExists) {
                // Add the new word to the whitelist array
                whitelistArray.add(newWord);

                // Update the JSON object
                jsonObject.add("whitelist", whitelistArray);

                // Rewrite the file
                try (FileWriter fileWriter = new FileWriter(WORDS_JSON_PATH)) {
                    fileWriter.write(JsonManager.gson.toJson(jsonObject));
                    editor.setOutputText(newWord + " добавлено в whitelist.");
                } catch (IOException e) {
                    System.err.println("Failed to update JSON file: " + WORDS_JSON_PATH);
                    e.printStackTrace();
                }
            } else {
                editor.setOutputText(newWord + " уже существует в whitelist.");
            }
        } else {
            editor.setOutputText("Файла words.json не существует.");
        }
    }
}