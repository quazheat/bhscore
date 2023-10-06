package fr.openai.database;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.openai.filter.ChatMessage;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class DatabaseManager {
    private static final String FILE_NAME = "livechat.json";

    public static void saveMessages(List<ChatMessage> messages) {
        try (Writer writer = new OutputStreamWriter(new FileOutputStream(FILE_NAME), StandardCharsets.UTF_8)) {
            Gson gson = new GsonBuilder().create();
            gson.toJson(messages, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
