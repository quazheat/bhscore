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

    public void deleteFile() {
        File wordsJsonFile = new File(WORDS_JSON_PATH);
    }/*
    public long getFileSize(String filePath) {
        File file = new File(filePath);
        return file.length();
    }*/
}
