package fr.openai.runtime;

import fr.openai.database.WordsFileManager;

import java.io.*;
import java.util.Properties;

public class ConfigManager {
    private static final String CONFIG_FILE_PATH = "config.properties";
    private static final String DEFAULT_LOG_RNT_PATH = System.getProperty("user.home") + File.separator + ".cristalix" +
            File.separator + "updates" + File.separator + "Minigames" + File.separator + "logs" + File.separator + "latest.log";
    private static final int DEFAULT_UPFQ = 100; // Значение по умолчанию для upFQ

    private final Properties properties;
    private final WordsFileManager wordsFileManager;

    public ConfigManager() {
        this.properties = new Properties();
        this.wordsFileManager = new WordsFileManager();

        loadConfig();
        createWords();
    }

    private void loadConfig() {
        try (InputStream input = new FileInputStream(CONFIG_FILE_PATH)) {
            properties.load(input);
        } catch (IOException e) {
            properties.setProperty("log_rnt_path", DEFAULT_LOG_RNT_PATH);
            properties.setProperty("upFQ", String.valueOf(DEFAULT_UPFQ)); // Устанавливаем значение upFQ по умолчанию
            saveConfig();
        }
    }

    private void saveConfig() {
        try (OutputStream output = new FileOutputStream(CONFIG_FILE_PATH)) {
            properties.store(output, "BHScore Configuration");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createWords() {
        wordsFileManager.createWordsFile();
    }

    public String getLogRntPath() {
        String path = properties.getProperty("log_rnt_path");
        if (path == null) {
            return DEFAULT_LOG_RNT_PATH;
        }
        return path;
    }

    public int getUpFQ() {
        String upFQString = properties.getProperty("upFQ");
        if (upFQString == null) {
            return DEFAULT_UPFQ;
        }
        try {
            return Integer.parseInt(upFQString);
        } catch (NumberFormatException e) {
            return DEFAULT_UPFQ;
        }
    }

    public void setLogRntPath(String path) {
        properties.setProperty("log_rnt_path", path);
        saveConfig();
    }

    public void setUpFQ(int upFQ) {
        properties.setProperty("upFQ", String.valueOf(upFQ));
        saveConfig();
    }
}