package fr.openai.database.editor;

import fr.openai.database.JsonManager;
import fr.openai.database.editor.Editor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

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
        JSONObject jsonObject = jsonManager.parseJsonFile(WORDS_JSON_PATH);

        if (jsonObject != null) {
            // Получаем массив forbidden_words
            JSONArray forbiddenWordsArray = (JSONArray) jsonObject.get("forbidden_words");

            // Проверяем, не существует ли уже такого слова в массиве
            boolean wordExists = false;
            for (Object word : forbiddenWordsArray) {
                if (word.toString().toLowerCase().equals(newWord)) {
                    wordExists = true;
                    break;
                }
            }

            if (!wordExists) {
                // Добавляем новое слово в массив
                forbiddenWordsArray.add(newWord);

                // Обновляем JSON объект
                jsonObject.put("forbidden_words", forbiddenWordsArray);

                // Перезаписываем файл
                try (FileWriter fileWriter = new FileWriter(WORDS_JSON_PATH)) {
                    jsonObject.writeJSONString(fileWriter);
                    editor.setOutputText(newWord +" добавлено.");
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
