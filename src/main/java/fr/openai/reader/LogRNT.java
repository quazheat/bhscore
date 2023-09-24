package fr.openai.reader;

import fr.openai.exec.Executor;
import fr.openai.database.Names;
import fr.openai.handler.filter.Cleaner;
import fr.openai.database.DatabaseManager;
import fr.openai.notify.NotificationSystem;
import fr.openai.runtime.MessageManager;
import fr.openai.runtime.SystemTrayManager;
import fr.openai.runtime.Times;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import fr.openai.handler.filter.FloodWarn;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import fr.openai.runtime.ConfigManager;
public class LogRNT {
    private volatile boolean stopRequested = false;

    private final ConfigManager configManager;
    private final Executor executor;
    private final Names names;
    private final MessageManager messageManager;
    private final Cleaner cleaner;
    private final Times times;
    private final FloodWarn floodWarn;
    private final ExecutorService chatReaderExec;
    private static final NotificationSystem notificationSystem = new NotificationSystem(); // Создаем экземпляр класса

    public LogRNT() {
        this.configManager = new ConfigManager();
        this.executor = new Executor();
        this.names = new Names();
        this.messageManager = new MessageManager();
        this.cleaner = new Cleaner(messageManager);
        this.times = new Times(messageManager);
        Thread cleanerThread = new Thread(cleaner);
        cleanerThread.start();
        this.floodWarn = new FloodWarn();
        this.chatReaderExec = Executors.newSingleThreadExecutor(); // Создаем пул потоков с одним потоком для FloodWarn
    }

    public static void main(String[] args) {
        LogRNT logReader = new LogRNT();
        //notificationSystem.showTestNotification(); // Вызываем метод showTestNotification
        SystemTrayManager trayManager = new SystemTrayManager();
        trayManager.setupSystemTray(); // Настройка системного трея
        logReader.run();
    }

    private void run() {
        long previousSize = getFileSize();
        long currentTime = System.currentTimeMillis();

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

            // Вызываем FloodWarn в отдельном потоке
            chatReaderExec.submit(floodWarn::checkWarn);

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
        JSONArray jsonArray = new JSONArray();

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

    private void processLogLine(String line, JSONArray jsonArray) {
        line = new String(line.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);

        if (Readable.check(line)) {
            executor.execute(line, names);
            times.timestamp(line, names);

            try {
                Object parsedObject = new JSONParser().parse(line);

                if (parsedObject instanceof JSONObject) {
                    jsonArray.add(parsedObject);
                }  // Действия по обработке не-JSON строки

            } catch (ParseException e) {
                // Обработка ошибок разбора JSON
            }
        }
    }
}
