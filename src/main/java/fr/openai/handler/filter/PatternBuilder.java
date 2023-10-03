package fr.openai.handler.filter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class PatternBuilder {
    public static Pattern buildPatternFromFile() {
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
        } catch (IOException | ParseException | PatternSyntaxException e) {
            e.printStackTrace();
            throw new RuntimeException("Error reading JSON file: " + "words.json");
        }
    }
}
