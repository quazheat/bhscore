package fr.openai.b;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileReader;
import java.io.IOException;

public class j {
    public static JsonObject jj(String lm) {
        try {
            return JsonParser.parseReader(new FileReader(lm)).getAsJsonObject();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(" file: " + lm);
        }
    }
}
