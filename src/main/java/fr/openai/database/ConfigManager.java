package fr.openai.database;

import java.io.*;
import java.util.Properties;
import java.util.concurrent.CopyOnWriteArrayList;

public class ConfigManager {
    private static final String CONFIG_FILE_PATH = "config.properties";
    private static final String LOG_RNT_PATH_KEY = "log_rnt_path";
    private static final String UPFQ_KEY = "upFQ";
    private static final String DEFAULT_LOG_RNT_PATH = System.getProperty("user.home") + File.separator + ".cristalix" +
            File.separator + "updates" + File.separator + "Minigames" + File.separator + "logs" + File.separator + "latest.log";
    private static final int DEFAULT_UPFQ = 40;

    private static final String WARNS_COUNTER_KEY = "warnsCounter";
    private static final String MUTED_COUNTER_KEY = "mutedCounter";

    private final Properties properties;
    private int upFQ;
    private int warnsCounter = 0;
    private int mutedCounter = 0;
    private final CopyOnWriteArrayList<UpFQChangeListener> upFQChangeListeners = new CopyOnWriteArrayList<>();

    public ConfigManager() {
        this.properties = new Properties();
        loadConfig();
    }

    private void loadConfig() {
        try (InputStream input = new FileInputStream(CONFIG_FILE_PATH)) {
            properties.load(input);
            upFQ = getIntPropertyOrDefault();
        } catch (IOException e) {
            properties.setProperty(LOG_RNT_PATH_KEY, DEFAULT_LOG_RNT_PATH);
            properties.setProperty(UPFQ_KEY, String.valueOf(DEFAULT_UPFQ));
            upFQ = DEFAULT_UPFQ;
            saveConfig();
        }

        // Load the warnsCounter and mutedCounter from the configuration file
        warnsCounter = getIntPropertyOrDefault(WARNS_COUNTER_KEY);
        mutedCounter = getIntPropertyOrDefault(MUTED_COUNTER_KEY);
    }

    private void saveConfig() {
        try (OutputStream output = new FileOutputStream(CONFIG_FILE_PATH)) {
            properties.store(output, "BHScore Configuration");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Save the warnsCounter and mutedCounter to the configuration file
        properties.setProperty(WARNS_COUNTER_KEY, String.valueOf(warnsCounter));
        properties.setProperty(MUTED_COUNTER_KEY, String.valueOf(mutedCounter));
    }

    private String getPropertyOrDefault() {
        String value = properties.getProperty(LOG_RNT_PATH_KEY);
        return (value != null) ? value : DEFAULT_LOG_RNT_PATH;
    }

    private int getIntPropertyOrDefault() {
        String value = properties.getProperty(UPFQ_KEY);
        try {
            return (value != null) ? Integer.parseInt(value) : DEFAULT_UPFQ;
        } catch (NumberFormatException e) {
            return DEFAULT_UPFQ;
        }
    }

    // Define a helper method to get an integer property with a default value
    private int getIntPropertyOrDefault(String key) {
        String value = properties.getProperty(key);
        try {
            return (value != null) ? Integer.parseInt(value) : 0; // Default value is 0
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public String getLogRntPath() {
        return getPropertyOrDefault();
    }

    public int getUpFQ() {
        return upFQ;
    }

    public void setUpFQ(int upFQ) {
        this.upFQ = upFQ;
        properties.setProperty(UPFQ_KEY, String.valueOf(upFQ));
        saveConfig();

        for (UpFQChangeListener listener : upFQChangeListeners) {
            listener.upFQChanged(upFQ);
        }
    }

    public void setWarnsCounter(int warnsCounter) {
        this.warnsCounter = warnsCounter;
        properties.setProperty(WARNS_COUNTER_KEY, String.valueOf(warnsCounter));
        saveConfig();
    }

    public void setMutedCounter(int mutedCounter) {
        this.mutedCounter = mutedCounter;
        properties.setProperty(MUTED_COUNTER_KEY, String.valueOf(mutedCounter));
        saveConfig();
    }

    public interface UpFQChangeListener {
        void upFQChanged(int newUpFQ);
    }

    public void addUpFQChangeListener(UpFQChangeListener listener) {
        upFQChangeListeners.add(listener);
    }
}
