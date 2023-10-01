package fr.openai.handler.filter;

import fr.openai.runtime.TimeUtil;
import fr.openai.exec.Messages;
import fr.openai.handler.filter.fixer.SbFix;
import fr.openai.notify.NotificationSystem;

import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Swearing {
    private final NotificationSystem notificationSystem;
    public Swearing() {
        this.notificationSystem = new NotificationSystem();
    }
    private static final Pattern forbiddenPattern;

    static {forbiddenPattern = buildPattern();}

    public void onFilter(String name, String line) {
        // Проверяем, что ник игрока не равен "Unknown"
        if ("Unknown".equalsIgnoreCase(name)) return; // Пропускаем сообщение
        String message = Messages.getMessage(line);

        if (message != null) {
            message = SbFix.fixMessage(message); // Используем SbFix для обработки сообщения
            /*System.out.println("Filtering: " + name + " » " + message);*/
            if (hasWords(message)) {
                System.out.println("DETECTED");
                // Создаем сообщение о нарушении
                String violationMessage = name + " нарушил что-то";
                String currentTime = TimeUtil.getCurrentTime();
                showNotification(name);
                // Выводим сообщение о нарушении
                System.out.println("Filtering: " + violationMessage);
                // Здесь можно выполнить действия в случае обнаружения
            }
        } else {
            System.out.println("Filtering: No message received");
        }
    }

    private boolean hasWords(String message) {
        try {
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(new FileReader("words.json"));
            JSONArray whitelistArray = (JSONArray) json.get("whitelist");

            // Разбиваем сообщение на отдельные слова
            String[] words = message.split("\\s+");

            for (String word : words) {
                // Если слово есть в whitelist, то пропускаем его
                boolean isWhitelisted = false;
                for (Object wordObj : whitelistArray) {
                    String whitelistWord = (String) wordObj;
                    if (word.equals(whitelistWord)) {
                        isWhitelisted = true;
                        break;
                    }
                }

                if (!isWhitelisted) {
                    // Если слово не находится в whitelist, проверяем на запрещенные слова
                    Matcher matcher = forbiddenPattern.matcher(word);
                    if (matcher.find()) {
                        return true; // Найдено запрещенное слово в сообщении
                    }
                }
            }

            return false; // Не найдено запрещенных слов в сообщении
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            throw new RuntimeException("Error reading JSON file: " + "words.json");
        }
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
    private void showNotification(String playerName) {
        notificationSystem.showNotification(playerName, "swearing");
    }


}
