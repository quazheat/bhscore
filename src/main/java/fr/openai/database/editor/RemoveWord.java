package fr.openai.database.editor;

import fr.openai.database.JsonManager;
import fr.openai.database.editor.Editor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;

public class RemoveWord {
    private static final String WORDS_JSON_PATH = "words.json";
    private final Editor editor; // Добавьте поле для доступа к Editor

    public RemoveWord(Editor editor) {
        this.editor = editor;
    }

    public void removeWord(String wordToRemove) {
        // Приводим входное слово к нижнему регистру
        wordToRemove = wordToRemove.toLowerCase();

        // Считываем текущий JSON из файла
        JsonManager jsonManager = new JsonManager();
        JSONObject jsonObject = jsonManager.parseJsonFile(WORDS_JSON_PATH);

        if (jsonObject != null) {
            // Получаем массив forbidden_words
            JSONArray forbiddenWordsArray = (JSONArray) jsonObject.get("forbidden_words");

            // Приводим слова в массиве к нижнему регистру и очищаем их
            for (int i = 0; i < forbiddenWordsArray.size(); i++) {
                String existingWord = forbiddenWordsArray.get(i).toString();
                existingWord = existingWord.toLowerCase();
                existingWord = existingWord.replaceAll("[^a-zа-яё]", "");
                forbiddenWordsArray.set(i, existingWord);
            }

            // Проверяем, существует ли слово в массиве
            if (forbiddenWordsArray.contains(wordToRemove)) {
                // Удаляем определенное слово из массива
                forbiddenWordsArray.remove(wordToRemove);

                // Обновляем JSON объект
                jsonObject.put("forbidden_words", forbiddenWordsArray);

                // Перезаписываем файл
                try (FileWriter fileWriter = new FileWriter(WORDS_JSON_PATH)) {
                    jsonObject.writeJSONString(fileWriter);
                    editor.setOutputText("Слово " + wordToRemove + " удалено из списка. ");
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
