package fr.openai.database.editor;

import fr.openai.database.JsonManager;
import fr.openai.database.editor.Editor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;

public class RemoveWhitelistWord {
    private static final String WORDS_JSON_PATH = "words.json";
    private final Editor editor; // поле для доступа к Editor

    public RemoveWhitelistWord(Editor editor) {
        this.editor = editor;
    }

    public void removeWhitelistWord(String wordToRemove) {
        // Приводим входное слово к нижнему регистру и очищаем от лишних символов
        wordToRemove = wordToRemove.toLowerCase().replaceAll("[^a-zа-яё]", "");

        // Считываем текущий JSON из файла
        JsonManager jsonManager = new JsonManager();
        JSONObject jsonObject = jsonManager.parseJsonFile(WORDS_JSON_PATH);

        if (jsonObject != null) {
            // Получаем массив whitelist
            JSONArray whitelistArray = (JSONArray) jsonObject.get("whitelist");

            // Проверяем, существует ли слово в массиве
            if (whitelistArray.contains(wordToRemove)) {
                // Удаляем определенное слово из массива
                whitelistArray.remove(wordToRemove);

                // Обновляем JSON объект
                jsonObject.put("whitelist", whitelistArray);

                // Перезаписываем файл
                try (FileWriter fileWriter = new FileWriter(WORDS_JSON_PATH)) {
                    jsonObject.writeJSONString(fileWriter);
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