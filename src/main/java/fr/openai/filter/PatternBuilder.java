package fr.openai.filter;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class PatternBuilder {
    public static Pattern buildPatternFromFile() {
        try {
            JsonObject json = JsonParser.parseReader(new FileReader("words.json")).getAsJsonObject();
            JsonArray forbiddenWordsArray = json.getAsJsonArray("forbidden_words");

            StringBuilder patternBuilder = new StringBuilder("\\b(");

            for (int i = 0; i < forbiddenWordsArray.size(); i++) {
                String word = forbiddenWordsArray.get(i).getAsString();
                patternBuilder.append(".*");
                patternBuilder.append(Pattern.quote(word));
                patternBuilder.append(".*");

                if (i < forbiddenWordsArray.size() - 1) {
                    patternBuilder.append("|");
                }
            }

            patternBuilder.append(")\\b");

            // Using UNICODE_CHARACTER_CLASS flag for correct processing of non-Latin characters
            return Pattern.compile(patternBuilder.toString(), Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CHARACTER_CLASS);
        } catch (IOException | PatternSyntaxException e) {
            e.printStackTrace();
            throw new RuntimeException("Error reading JSON file: words.json");
        }
    }
}
