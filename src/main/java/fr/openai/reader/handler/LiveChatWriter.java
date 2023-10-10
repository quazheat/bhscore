package fr.openai.reader.handler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.openai.filter.ChatMessage;

import java.io.File;
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
    public static void writeTextToFile(String filePath, String text) {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(text);
            System.out.println("Текст успешно записан в файл: " + filePath);
        } catch (IOException e) {
            System.out.println("Не удалось записать текст в файл: " + filePath);
            e.printStackTrace();
        }
    }

    public static void deleteChat(String filePath) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        // Создаем объект файла
        File file = new File(filePath);

        // Проверяем, существует ли файл с указанным именем
        if (file.exists()) {
            // Попытка удалить файл
            if (file.delete()) {
                System.out.println("Удален файл: " + filePath);
            } else {
                System.out.println("Не удалось удалить файл: " + filePath);
            }
        }

        // Записываем нужный текст в файл
        String textToWrite = "";
        writeTextToFile(filePath, textToWrite);
    }
}
