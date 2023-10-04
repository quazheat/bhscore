package fr.openai.database.editor;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import fr.openai.database.JsonManager;
import fr.openai.database.editor.Editor;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.FileWriter;
import java.io.IOException;

public class RemoveWord {
    private static final String WORDS_JSON_PATH = "words.json";
    private final Editor editor; // Добавьте поле для доступа к Editor

    public RemoveWord(Editor editor) {
        this.editor = editor;
    }

    public void removeWord(String wordToRemove) {
        // Convert the input word to lowercase
        wordToRemove = wordToRemove.toLowerCase();

        // Read the current JSON from the file
        JsonManager jsonManager = new JsonManager();
        JsonObject jsonObject = jsonManager.parseJsonFile(WORDS_JSON_PATH);

        if (jsonObject != null) {
            // Get the forbidden_words array
            JsonArray forbiddenWordsArray = jsonObject.getAsJsonArray("forbidden_words");

            // Convert all words in the array to lowercase and remove extra characters
            JsonArray updatedForbiddenWordsArray = new JsonArray();
            for (JsonElement element : forbiddenWordsArray) {
                String existingWord = element.getAsString();
                existingWord = existingWord.toLowerCase().replaceAll("[^a-zа-яё]", "");
                updatedForbiddenWordsArray.add(new JsonPrimitive(existingWord));
            }

            // Check if the word exists in the array
            if (updatedForbiddenWordsArray.contains(new JsonPrimitive(wordToRemove))) {
                // Remove the specified word from the array
                updatedForbiddenWordsArray.remove(new JsonPrimitive(wordToRemove));

                // Update the JSON object
                jsonObject.add("forbidden_words", updatedForbiddenWordsArray);

                // Rewrite the file
                try (FileWriter fileWriter = new FileWriter(WORDS_JSON_PATH)) {
                    fileWriter.write(jsonManager.gson.toJson(jsonObject));
                    editor.setOutputText("Слово " + wordToRemove + " удалено из списка.");
                } catch (IOException e) {
                    System.err.println("Failed to update JSON file: " + WORDS_JSON_PATH);
                    e.printStackTrace();
                }
            } else {
                editor.setOutputText("Слово " + wordToRemove + " не найдено.");
            }
        } else {
            editor.setOutputText("Файла words.json не существует.");
        }
    }
}