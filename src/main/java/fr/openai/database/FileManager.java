package fr.openai.database;

import java.io.*;

public class FileManager {
    private static final String WORDS_JSON_PATH = "words.json";

    public boolean isExist() {
        File wordsJsonFile = new File(WORDS_JSON_PATH);
        return wordsJsonFile.exists();
    }

    public boolean isFileEmpty() {
        File wordsJsonFile = new File(WORDS_JSON_PATH);
        return wordsJsonFile.exists() && wordsJsonFile.length() == 0;
    }

    public boolean deleteFile() {
        File wordsJsonFile = new File(WORDS_JSON_PATH);
        return wordsJsonFile.delete();
    }

    public void createFileWithContent(String content) {
        try (FileWriter fileWriter = new FileWriter(WORDS_JSON_PATH)) {
            fileWriter.write(content);
        } catch (IOException e) {
            System.err.println("Failed to create JSON file: " + WORDS_JSON_PATH);
            e.printStackTrace();
        }
    }
}
