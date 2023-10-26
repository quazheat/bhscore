package fr.openai.reader;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.openai.exec.Executor;
import fr.openai.filter.fixer.Names;
import fr.openai.notify.NotificationSystem;
import fr.openai.runtime.SystemTrayManager;
import fr.openai.database.ConfigManager;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;

public class LogRNT {

    private final ConfigManager configManager;
    private final Executor executor;
    private final Names names;
    private final NotificationSystem notificationSystem;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();


    public LogRNT(NotificationSystem notificationSystem) {
        this.notificationSystem = notificationSystem;
        this.configManager = new ConfigManager();
        this.executor = new Executor(notificationSystem); // Передайте liveChat в Executor
        this.names = new Names();
    }

    public void starter() throws InterruptedException {
        LogRNT logReader = new LogRNT(notificationSystem);

        SystemTrayManager trayManager = new SystemTrayManager();
        trayManager.setupSystemTray(); // Настройка системного трея
        logReader.run();
    }

    private void run() {
        long previousSize = getFileSize();
        long currentTime = System.currentTimeMillis();

        while (true) {
            long currentSize = getFileSize();
            long elapsedTime = System.currentTimeMillis() - currentTime;
            int upFQ = configManager.getUpFQ();

            if (currentSize > previousSize && elapsedTime >= upFQ) {
                String logRntPath = configManager.getLogRntPath();
                readNewLines(previousSize, logRntPath);
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

    private void readNewLines(long start, String logRntPath) {
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
    }

    private void processLogLine(String line, JsonArray jsonArray) throws IOException {
        line = new String(line.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);

        if (Readable.check(line)) {
            executor.execute(line, names);

            try {
                Object parsedObject = gson.fromJson(line, Object.class);

                if (parsedObject instanceof JsonObject) {
                    jsonArray.add((JsonObject) parsedObject);
                }  // Действия по обработке не-JSON строки

            } catch (JsonParseException e) {
                // Обработка ошибок
            }
        }
    }
}
