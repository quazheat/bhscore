package fr.openai.reader.handler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.openai.filter.ChatMessage;

import java.io.FileWriter;
import java.io.IOException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;

public class LiveChatWriter {
    public static void writeLiveChat(List<ChatMessage> chatMessages, String filePath) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(chatMessages, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<ChatMessage> readLiveChat(String filePath) {
        Gson gson = new Gson();

        try (FileReader reader = new FileReader(filePath)) {
            ChatMessage[] chatMessages = gson.fromJson(reader, ChatMessage[].class);
            return Arrays.asList(chatMessages);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
