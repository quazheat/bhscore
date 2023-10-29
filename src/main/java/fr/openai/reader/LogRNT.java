package fr.openai.reader;

import fr.openai.database.files.ConnectDb;
import fr.openai.exec.Executor;
import fr.openai.filter.fixer.Names;
import fr.openai.notify.NotificationSystem;
import fr.openai.runtime.SystemTrayManager;
import fr.openai.database.ConfigManager;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class LogRNT {
    private final ConfigManager configManager;
    private final Executor executor;
    private long previousSize;

    public LogRNT(NotificationSystem notificationSystem) {
        this.configManager = new ConfigManager();
        this.executor = new Executor(notificationSystem);
    }

    public void starter(double similarityThreshold, List<String> whitelistWords, Names names) {
        ConnectDb.getWordsDB();
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

        SystemTrayManager trayManager = new SystemTrayManager();
        trayManager.setupSystemTray();

        Runnable task = () -> checkLogChanges(similarityThreshold, whitelistWords, names);

        int initialDelay = 0; // Start immediately
        int period = configManager.getUpFQ(); // Set the period to the desired frequency
        previousSize = getFileSize();

        executorService.scheduleAtFixedRate(task, initialDelay, period, TimeUnit.MILLISECONDS);
    }


    private void checkLogChanges(double similarityThreshold, List<String> whitelistWords, Names names) {
        String logRntPath = configManager.getLogRntPath();
        long currentSize = getFileSize();

        if (currentSize > previousSize) {
            readNewLines(previousSize, logRntPath, similarityThreshold, whitelistWords, names);
            previousSize = currentSize;
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

    private void readNewLines(long start, String logRntPath, double similarityThreshold, List<String> whitelistWords, Names names) {
        try (RandomAccessFile raf = new RandomAccessFile(logRntPath, "r")) {
            raf.seek(start);
            String line;
            while ((line = raf.readLine()) != null) {
                processLogLine(line, similarityThreshold, whitelistWords, names);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void processLogLine(String line, double similarityThreshold, List<String> whitelistWords, Names names) throws IOException {
        line = new String(line.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);

        if (Readable.check(line)) {
            executor.execute(line, names, similarityThreshold, whitelistWords);
        }
    }

}