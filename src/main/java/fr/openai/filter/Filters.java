package fr.openai.filter;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import fr.openai.database.JsonFileReader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static fr.openai.filter.SimilarityCalculator.calculateSimilarity;

public class Filters {
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

    public boolean hasSwearing(String message, double similarityThreshold, List<String> whitelistWords) {
        JsonObject json = JsonFileReader.readJsonFile("words.json");
        JsonArray forbiddenWordsArray = json.getAsJsonArray("forbidden_words");

        String[] words = message.split("\\s+");

        boolean skipNextWord = false; // To keep track of whether we should skip the next word

        for (int i = 0; i < words.length; i++) {
            String word = words[i];

            if (skipNextWord) {
                skipNextWord = false;
                continue;
            }

            if (isWordForbidden(word, forbiddenWordsArray)) {
                if (!isWordSimilarToWhitelisted(word, similarityThreshold, whitelistWords)) {
                    return true;
                } else {
                    // If the word is similar to a whitelisted word, move to the next word
                    if (i < words.length - 1) {
                        skipNextWord = true;
                    }
                }
            }
        }

        return false;
    }



    private boolean isWordSimilarToWhitelisted(String word, double similarityThreshold, List<String> whitelistWords) {
        for (String whitelistWord : whitelistWords) {
            if (calculateSimilarity(word, whitelistWord) >= similarityThreshold) {
                return true;
            }
        }
        return false;
    }

    private boolean isWordForbidden(String word, JsonArray forbiddenWordsArray) {
        for (int i = 0; i < forbiddenWordsArray.size(); i++) {
            String forbiddenWord = forbiddenWordsArray.get(i).getAsString();
            if (word.equals(forbiddenWord)) {
                return true;
            }
        }
        return false;
    }

    private String removeSpecialCharacters(String input) {
        return input.replaceAll("[^a-zA-Zа-яА-Я]", "");
    }
}
