package fr.openai.database.editor;

import fr.openai.database.JsonManager;
import fr.openai.database.editor.Editor;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.FileWriter;
import java.io.IOException;

public class AddNewWord {
    private static final String WORDS_JSON_PATH = "words.json";
    private final Editor editor; // Добавьте поле для доступа к Editor

    public AddNewWord(Editor editor) {
        this.editor = editor;
    }

    public void addNewWord(String newWord) {
        // Приводим новое слово к нижнему регистру
        newWord = newWord.toLowerCase();

        // Считываем текущий JSON из файла
        JsonManager jsonManager = new JsonManager();
        JsonObject jsonObject = jsonManager.parseJsonFile(WORDS_JSON_PATH);

        if (jsonObject != null) {
            // Получаем массив forbidden_words
            JsonArray forbiddenWordsArray = jsonObject.getAsJsonArray("forbidden_words");

            // Проверяем, не существует ли уже такого слова в массиве
            boolean wordExists = false;
            for (int i = 0; i < forbiddenWordsArray.size(); i++) {
                String word = forbiddenWordsArray.get(i).getAsString();
                if (word.toLowerCase().equals(newWord)) {
                    wordExists = true;
                    break;
                }
            }

            if (!wordExists) {
                // Добавляем новое слово в массив
                forbiddenWordsArray.add(newWord);

                // Обновляем JSON объект
                jsonObject.add("forbidden_words", forbiddenWordsArray);

                // Перезаписываем файл
                try (FileWriter fileWriter = new FileWriter(WORDS_JSON_PATH)) {
                    fileWriter.write(jsonManager.gson.toJson(jsonObject));
                    editor.setOutputText(newWord + " добавлено.");
                } catch (IOException e) {
                    System.err.println("Failed to update JSON file: " + WORDS_JSON_PATH);
                    e.printStackTrace();
                }
            } else {
                editor.setOutputText(newWord + " уже в списке");
            }
        } else {
            editor.setOutputText("Файла words.json не существует.");
        }
    }
}
