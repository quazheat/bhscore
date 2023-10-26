package fr.openai.database;

import java.io.*;
import java.util.Properties;

public class ConfigManager {
    private static final String CONFIG_FILE_PATH = "config.properties";
    private static final String LOG_RNT_PATH_KEY = "log_rnt_path";
    private static final String UPFQ_KEY = "upFQ";
    private static final String DEFAULT_LOG_RNT_PATH = System.getProperty("user.home") + File.separator + ".cristalix" +
            File.separator + "updates" + File.separator + "Minigames" + File.separator + "logs" + File.separator + "latest.log";
    private static final int DEFAULT_UPFQ = 100;

    private final Properties properties;

    public ConfigManager() {
        this.properties = new Properties();
        loadConfig();
    }

    private void loadConfig() {
        try (InputStream input = new FileInputStream(CONFIG_FILE_PATH)) {
            properties.load(input);
        } catch (IOException e) {
            properties.setProperty(LOG_RNT_PATH_KEY, DEFAULT_LOG_RNT_PATH);
            properties.setProperty(UPFQ_KEY, String.valueOf(DEFAULT_UPFQ));
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

    private String getPropertyOrDefault(String key, String defaultValue) {
        String value = properties.getProperty(key);
        return (value != null) ? value : defaultValue;
    }

    private int getIntPropertyOrDefault(String key, int defaultValue) {
        String value = properties.getProperty(key);
        try {
            return (value != null) ? Integer.parseInt(value) : defaultValue;
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public String getLogRntPath() {
        return getPropertyOrDefault(LOG_RNT_PATH_KEY, DEFAULT_LOG_RNT_PATH);
    }

    public int getUpFQ() {
        return getIntPropertyOrDefault(UPFQ_KEY, DEFAULT_UPFQ);
    }

    public void setLogRntPath(String path) {
        properties.setProperty(LOG_RNT_PATH_KEY, path);
        saveConfig();
    }

    public void setUpFQ(int upFQ) {
        properties.setProperty(UPFQ_KEY, String.valueOf(upFQ));
        saveConfig();
    }
}
