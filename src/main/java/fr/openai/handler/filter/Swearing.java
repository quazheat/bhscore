package fr.openai.handler.filter;

import fr.openai.exec.Messages;

import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Swearing {
    private static final Pattern forbiddenPattern;

    static {
        // Чтение запрещенных слов из JSON-файла и создание регулярного выражения
        forbiddenPattern = buildPattern();
    }

    public void onFilter(String name, String line) {
        // Проверяем, что ник игрока не равен "Unknown"
        if ("Unknown".equalsIgnoreCase(name)) {
            return; // Пропускаем сообщение
        }

        String message = Messages.getMessage(line);

        if (message != null) {
            System.out.println("Filtering: " + name + " » " + message);
            if (hasWords(message)) {
                System.out.println("DETECTED");

                // Создаем сообщение о нарушении
                String violationMessage = name + " нарушил что-то";

                // Выводим сообщение о нарушении
                System.out.println("Filtering: " + violationMessage);

                // Здесь можно выполнить действия в случае обнаружения
            }
        } else {
            System.out.println("Filtering: No message received");
        }
    }

    private boolean hasWords(String message) {
        Matcher matcher = forbiddenPattern.matcher(message);
        return matcher.find();
    }

    private static Pattern buildPattern() {
        try {
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(new FileReader("words.json"));
            JSONArray forbiddenWordsArray = (JSONArray) json.get("forbidden_words");

            StringBuilder patternBuilder = new StringBuilder("\\b(");

            for (int i = 0; i < forbiddenWordsArray.size(); i++) {
                String word = (String) forbiddenWordsArray.get(i);
                patternBuilder.append(".*");
                patternBuilder.append(Pattern.quote(word));
                patternBuilder.append(".*");

                if (i < forbiddenWordsArray.size() - 1) {
                    patternBuilder.append("|");
                }
            }

            patternBuilder.append(")\\b");

            // флаг UNICODE_CHARACTER_CLASS для корректной работы с кириллицей
            return Pattern.compile(patternBuilder.toString(), Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CHARACTER_CLASS);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            throw new RuntimeException("Error reading JSON file: " + "words.json");
        }
    }


}
