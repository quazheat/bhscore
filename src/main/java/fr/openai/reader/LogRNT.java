package fr.openai.reader;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.openai.exec.Executor;
import fr.openai.database.Names;
import fr.openai.handler.filter.Cleaner;
import fr.openai.database.DatabaseManager;
import fr.openai.handler.filter.FloodWarn;
import fr.openai.notify.NotificationSystem;
import fr.openai.runtime.SystemTrayManager;
import fr.openai.runtime.Times;
import fr.openai.runtime.ConfigManager;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class LogRNT {

    private final ConfigManager configManager;
    private final Executor executor;
    private final Names names;
    private final List<JsonObject> liveChat; // Список live_chat
    private final Cleaner cleaner;
    private final Times times;
    private final NotificationSystem notificationSystem;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final FloodWarn floodWarn;

    public LogRNT(NotificationSystem notificationSystem) {
        this.notificationSystem = notificationSystem;
        this.configManager = new ConfigManager();
        this.executor = new Executor(notificationSystem);
        this.names = new Names();
        this.liveChat = new ArrayList<>(); // Создайте список live_chat
        this.cleaner = new Cleaner(liveChat); // Передайте liveChat в Cleaner
        this.floodWarn = new FloodWarn(notificationSystem);
        Thread cleanerThread = new Thread(cleaner);
        cleanerThread.start();
        this.times = new Times(liveChat); // Передайте liveChat в Times
    }

    public void starter() {
        LogRNT logReader = new LogRNT(notificationSystem);
        SystemTrayManager trayManager = new SystemTrayManager();
        trayManager.setupSystemTray(); // Настройка системного трея
        logReader.run();
    }

    private void run() {
        long previousSize = getFileSize();
        long currentTime = System.currentTimeMillis();

        boolean stopRequested = false;
        while (!stopRequested) {
            long currentSize = getFileSize();
            long elapsedTime = System.currentTimeMillis() - currentTime;
            int upFQ = configManager.getUpFQ();

            if (currentSize > previousSize && elapsedTime >= upFQ) {
                String logRntPath = configManager.getLogRntPath();
                readNewLines(previousSize, currentSize, logRntPath);
                previousSize = currentSize;
                currentTime = System.currentTimeMillis();
            }

            try {
                Thread.sleep(upFQ);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private long getFileSize() {
        try {
            String logRntPath = configManager.getLogRntPath();
            Path path = Paths.get(logRntPath);
            return Files.size(path);
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }

    private void readNewLines(long start, long end, String logRntPath) {
        JsonArray jsonArray = new JsonArray();

        try (RandomAccessFile raf = new RandomAccessFile(logRntPath, "r")) {
            raf.seek(start);
            String line;
            while ((line = raf.readLine()) != null) {
                processLogLine(line, jsonArray);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        DatabaseManager.saveMessages(jsonArray);
    }

    private void processLogLine(String line, JsonArray jsonArray) {
        line = new String(line.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);

        if (Readable.check(line)) {
            executor.execute(line, names);
            times.timestamp(line, names);

            try {
                Object parsedObject = gson.fromJson(line, Object.class);

                if (parsedObject instanceof JsonObject) {
                    jsonArray.add((JsonObject) parsedObject);
                }  // Действия по обработке не-JSON строки

            } catch (JsonParseException e) {
                // Обработка ошибок разбора JSON
            }
        }
    }
}
