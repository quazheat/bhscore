package fr.openai.database.editor;

import fr.openai.database.JsonManager;
import fr.openai.database.editor.Editor;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.FileWriter;
import java.io.IOException;

public class RemoveWhitelistWord {
    private static final String WORDS_JSON_PATH = "words.json";
    private final Editor editor; // поле для доступа к Editor

    public RemoveWhitelistWord(Editor editor) {
        this.editor = editor;
    }

    public void removeWhitelistWord(String wordToRemove) {
        // Convert the input word to lowercase and remove extra characters
        wordToRemove = wordToRemove.toLowerCase().replaceAll("[^a-zа-яё]", "");

        // Read the current JSON from the file
        JsonManager jsonManager = new JsonManager();
        JsonObject jsonObject = jsonManager.parseJsonFile(WORDS_JSON_PATH);

        if (jsonObject != null) {
            // Get the whitelist array
            JsonArray whitelistArray = jsonObject.getAsJsonArray("whitelist");

            // Iterate over the elements to find the word
            boolean found = false;
            int indexToRemove = -1;
            for (int i = 0; i < whitelistArray.size(); i++) {
                String existingWord = whitelistArray.get(i).getAsString();
                if (existingWord.equals(wordToRemove)) {
                    found = true;
                    indexToRemove = i;
                    break;
                }
            }

            if (found) {
                // Remove the specified word from the array
                whitelistArray.remove(indexToRemove);

                // Update the JSON object
                jsonObject.add("whitelist", whitelistArray);

                // Rewrite the file
                try (FileWriter fileWriter = new FileWriter(WORDS_JSON_PATH)) {
                    fileWriter.write(jsonManager.gson.toJson(jsonObject));
                    editor.setOutputText(wordToRemove + " удалено из whitelist.");
                } catch (IOException e) {
                    System.err.println("Failed to update JSON file: " + WORDS_JSON_PATH);
                    e.printStackTrace();
                }
            } else {
                editor.setOutputText(wordToRemove + " не найдено в whitelist.");
            }
        } else {
            editor.setOutputText("Файла words.json не существует.");
        }
    }

}
