package fr.openai.database.files;

import java.io.File;

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
        if (wordsJsonFile.exists()) {
            wordsJsonFile.delete();
        }
    }

    public void createReadmeFile() {
        ReadmeFileCreator readmeFileCreator = new ReadmeFileCreator();
        readmeFileCreator.createReadmeFile();
    }

    public void readme () {
        FileManager fileManager = new FileManager();

        if (!fileManager.isExist() || fileManager.isFileEmpty()) {
            fileManager.createReadmeFile();
            System.out.println("README файл успешно создан.");
        }
    }
}
