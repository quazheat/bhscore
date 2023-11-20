package fr.openai.filter;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import fr.openai.database.JsonFileReader;
import fr.openai.database.UsernameProvider;
import fr.openai.filter.fixer.LevenshteinDistance;

public class Filters extends UsernameProvider {

    private final String username = getUsername();

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
                if (uniqueChars <= 3) {

                    return true;
                }
            }
        }
        return false;

    }


    public boolean hasCaps(String message) {
        String cleanedMessage = removeSpecialCharacters(message);

        if (cleanedMessage.length() < 10) {
            int uniqueChars = (int) cleanedMessage.chars().distinct().count();
            if (uniqueChars == 2) {
                return false;
            }
        }

        int upperCaseCount = 0;
        for (char c : cleanedMessage.toCharArray()) {
            if (Character.isUpperCase(c)) {
                upperCaseCount++;
            }
        }

        return upperCaseCount > 5 && (double) upperCaseCount / cleanedMessage.length() > 0.55;
    }

    public boolean hasWFlood(String message) {
        String[] words = message.split("\\s+");
        int targetCount = 5;
        int consecutiveCount = 1;

        for (int i = 1; i < words.length; i++) {
            if (words[i].equals(words[i - 1])) {
                consecutiveCount++;
                if (consecutiveCount >= targetCount) {
                    return true;
                }
            } else {
                consecutiveCount = 1; // Сбрасываем счетчик, если слова не идут подряд
            }
        }
        return false;
    }


    public boolean hasSwearing(String message) {
        JsonObject json = JsonFileReader.readJsonFile("words.json");
        JsonArray whitelistArray = json.getAsJsonArray("whitelist");
        JsonArray forbiddenWordsArray = json.getAsJsonArray("forbidden_words");

        LevenshteinDistance levenshteinDistance = new LevenshteinDistance();

        message = message.toLowerCase();
        String[] tokens = message.split("[\\s,.;!?]+"); // Разделение на токены

        for (String token : tokens) {
            boolean isWhitelisted = false;
            for (int i = 0; i < whitelistArray.size(); i++) {
                String word = whitelistArray.get(i).getAsString();
                double similarity = levenshteinDistance.apply(token, word);
                if (similarity >= 0.8) {
                    isWhitelisted = true;

                    break;
                }
            }
            if (!isWhitelisted) {
                for (int i = 0; i < forbiddenWordsArray.size(); i++) {
                    String forbiddenWord = forbiddenWordsArray.get(i).getAsString();
                    double similarity = levenshteinDistance.apply(token, forbiddenWord);

                    if (similarity >= 0.8) {
                        return true;
                    }
                }
            }
        }
        return false;

    }

    public boolean hasBanWord(String message) {
        JsonObject json = JsonFileReader.readJsonFile("words.json");
        JsonArray whitelistArray = json.getAsJsonArray("whitelist");
        JsonArray forbiddenWordsArray = json.getAsJsonArray("banwords");

        LevenshteinDistance levenshteinDistance = new LevenshteinDistance();

        message = message.toLowerCase();
        String[] tokens = message.split("[\\s,.;!?]+"); // Разделение на токены

        for (String token : tokens) {
            boolean isWhitelisted = false;
            for (int i = 0; i < whitelistArray.size(); i++) {
                String word = whitelistArray.get(i).getAsString();
                double similarity = levenshteinDistance.apply(token, word);
                if (similarity >= 0.8) {
                    isWhitelisted = true;

                    break;
                }
            }
            if (!isWhitelisted) {
                for (int i = 0; i < forbiddenWordsArray.size(); i++) {
                    String forbiddenWord = forbiddenWordsArray.get(i).getAsString();
                    double similarity = levenshteinDistance.apply(token, forbiddenWord);

                    if (similarity >= 0.8) {
                        return true;
                    }
                }
            }
        }
        return false;

    }

    public boolean hasMuteCounter(String message) {
        if (username == null || username.length() <= 3) {
            return false;
        }
        return message.contains(username + " замутил");

    }

    public boolean hasWarnCounter(String message) {
        if (username == null || username.length() <= 3) {
            return false;
        }
        return message.contains(username + " предупредил");

    }

    private String removeSpecialCharacters(String input) {
        return input.replaceAll("[^a-zA-Zа-яА-Я]", "");

    }
}
