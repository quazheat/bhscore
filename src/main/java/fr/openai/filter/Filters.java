package fr.openai.filter;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import fr.openai.database.ConfigManager;
import fr.openai.database.JsonFileReader;
import fr.openai.discordfeatures.DiscordRPCDiag;
import fr.openai.filter.fixer.LevenshteinDistance;

import java.util.HashMap;
import java.util.Map;

public class Filters {

    private final ConfigManager configManager = new ConfigManager();

    public boolean hasManySymbols(String message) {
        char[] chars = message.toCharArray();
        int maxConsecutiveCount = 6;

        for (int i = 1, count = 1; i < chars.length; i++) {
            if (chars[i] == chars[i - 1]) {
                count++;
            } else {
                count = 1;
            }

            if (count >= maxConsecutiveCount) {
                return true;
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
        int targetCount = 5;
        // Create a HashMap to count word occurrences
        Map<String, Integer> wordCounts = new HashMap<>();

        for (String word : words) {
            wordCounts.put(word, wordCounts.getOrDefault(word, 0) + 1);
            if (wordCounts.get(word) >= targetCount) {
                return true;
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

        return upperCaseCount > 5 && (double) upperCaseCount / cleanedMessage.length() > 0.55;
    }

    public boolean hasSwearing(String message) {
        JsonObject json = JsonFileReader.readJsonFile("words.json");
        JsonArray whitelistArray = json.getAsJsonArray("whitelist");
        JsonArray forbiddenWordsArray = json.getAsJsonArray("forbidden_words");

        LevenshteinDistance levenshteinDistance = new LevenshteinDistance();

        message = message.toLowerCase();
        String[] tokens = message.split("[\\s,.;!?]+"); // Разделение на токены

        for (String token : tokens) {
            boolean shouldRemove = false;
            for (int i = 0; i < whitelistArray.size(); i++) {
                String word = whitelistArray.get(i).getAsString();
                double similarity = levenshteinDistance.apply(token, word);
                if (similarity >= 0.8) {
                    shouldRemove = true;
                    break;
                }
            }
            if (shouldRemove) {
                message = message.replace(token, "");
            }
            for (String word : tokens) {
                for (int i = 0; i < forbiddenWordsArray.size(); i++) {
                    String forbiddenWord = forbiddenWordsArray.get(i).getAsString();
                    double similarity = levenshteinDistance.apply(word, forbiddenWord);
                    if (similarity >= 0.8) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public boolean hasMuteCounter(String message) {
        String username = (configManager.getUsername());
        if (username == null) {
            username = DiscordRPCDiag.getUsername();
        }
        return message.contains(username + " замутил");
    }

    public boolean hasWarnCounter(String message) {
        String username = (configManager.getUsername());
        if (username == null) {
            username = DiscordRPCDiag.getUsername();
        }
        return message.contains(username + " предупредил");
    }
    
    private String removeSpecialCharacters(String input) {
        return input.replaceAll("[^a-zA-Zа-яА-Я]", "");
    }
}
