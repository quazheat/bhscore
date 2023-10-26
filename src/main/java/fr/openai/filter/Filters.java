package fr.openai.filter;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;

public class Filters {
    public boolean hasManySymbols(String message) {
        char[] chars = message.toCharArray();
        char prevChar = '\0';
        int count = 0;

        for (char c : chars) {
            if (c == prevChar) {
                count++;
                if (count >= 6) return true;
            } else {
                prevChar = c;
                count = 1;
            }
        }
        return false;
    }

    public boolean hasLaugh(String message) {
        String[] words = message.split("\\s+");

        for (String word : words) {
            if (word.length() > 9) {
                int uniqueChars = (int) word.chars().distinct().count();
                if (uniqueChars <= 3) return true;
            }
        }
        return false;
    }

    public boolean hasWFlood(String message) {
        String[] words = message.split("\\s+");
        for (String word : words) {
            int count = 0;
            for (String w : words) {
                if (word.equals(w)) {
                    count++;
                        if (count >= 5) return true;
                }
            }
        }
        return false;
    }

    public boolean hasCaps(String message) {
        int upperCaseCount = 0;
        String cleanedMessage = removeSpecialCharacters(message);

        for (char c : cleanedMessage.toCharArray()) {
            if (Character.isUpperCase(c)) {
                upperCaseCount++;
            }
        }

        // Проверяем, что букв в верхнем регистре составляют более 55% и букв более 6
        return upperCaseCount > 5 && (double) upperCaseCount / cleanedMessage.length() > 0.55;
    }

    public boolean hasSwearing(String message) {
        try {
            JsonObject json = JsonParser.parseReader(new FileReader("words.json")).getAsJsonObject();
            JsonArray whitelistArray = json.getAsJsonArray("whitelist");

            // Разбиваем сообщение на отдельные слова
            String[] words = message.split("\\s+");

            for (String word : words) {
                // Если слово есть в whitelist, то пропускаем его
                boolean isWhitelisted = false;
                for (int i = 0; i < whitelistArray.size(); i++) {
                    String whitelistWord = whitelistArray.get(i).getAsString();
                    if (word.equals(whitelistWord)) {
                        isWhitelisted = true;
                        break;
                    }
                }

                if (!isWhitelisted) {
                    // Если слово не находится в whitelist, проверяем на запрещенные слова
                    Matcher matcher = PatternBuilder.buildPatternFromFile().matcher(word);
                    if (matcher.find()) {
                        return true; // Найдено запрещенное слово в сообщении
                    }
                }
            }

            return false; // Не найдено запрещенных слов в сообщении
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error reading JSON file: " + "words.json");
        }
    }

    private String removeSpecialCharacters(String input) {
        // Оставляем только латиницу (a-zA-Z) и кириллицу (а-яА-Я)
        return input.replaceAll("[^a-zA-Zа-яА-Я]", "");
    }
}
