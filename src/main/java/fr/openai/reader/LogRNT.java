package fr.openai.reader;

import fr.openai.database.files.GetWords;
import fr.openai.discordfeatures.DiscordRPC;
import fr.openai.discordfeatures.DiscordRPCDiag;
import fr.openai.exec.Executor;
import fr.openai.exec.Names;
import fr.openai.notify.NotificationSystem;
import fr.openai.online.OnlineHandler;
import fr.openai.runtime.SystemTrayManager;
import fr.openai.database.ConfigManager;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class LogRNT {
    private final OnlineHandler onlineHandler = new OnlineHandler();
    private final ConfigManager configManager;
    private final GetWords getWords = new GetWords();
    private final Executor executor;
    private final Names names;
    private long previousSize;

    public LogRNT(NotificationSystem notificationSystem) {
        this.configManager = new ConfigManager();
        this.executor = new Executor(notificationSystem);
        this.names = new Names();
    }

    public void starter() {
        DiscordRPC discordRPC = new DiscordRPC();
        DiscordRPCDiag discordRPCDiag = new DiscordRPCDiag();
        String username = (configManager.getUsername());
        if (username == null || username.length() <= 3) {
            discordRPCDiag.setModal(true); // MODAL DIALOG
            discordRPCDiag.setVisible(true);
        }
        discordRPC.updateRPC();

        getWords.getWordsFile();
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

        SystemTrayManager trayManager = new SystemTrayManager();
        trayManager.setupSystemTray();
        Runnable task = this::checkLogChanges;
        int initialDelay = 0; // Start immediately
        int period = configManager.getUpFQ(); // Set the period to the desired frequency
        previousSize = getFileSize();

        executorService.scheduleAtFixedRate(task, initialDelay, period, TimeUnit.MILLISECONDS);
    }


    private void checkLogChanges() {
        String logRntPath = configManager.getLogRntPath();
        long currentSize = getFileSize();

        if (currentSize > previousSize) {
            readNewLines(previousSize, logRntPath);
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

    private void readNewLines(long start, String logRntPath) {

        try (RandomAccessFile raf = new RandomAccessFile(logRntPath, "r")) {
            raf.seek(start);
            String line;
            while ((line = raf.readLine()) != null) {
                processLogLine(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processLogLine(String line) throws IOException {
        line = new String(line.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);

        if (Readable.check(line)) {
            executor.execute(line, names);
        }

        if (Readable.checkServer(line)) {
            onlineHandler.addOnlineUser(line);
        }
    }
}