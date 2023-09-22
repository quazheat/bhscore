package fr.openai.reader;

import fr.openai.exec.Executor;
import fr.openai.exec.Names;
import fr.openai.handler.filter.Flooding;
import fr.openai.runtime.Cleaner;
import fr.openai.runtime.DatabaseManager;
import fr.openai.runtime.MessageManager;
import fr.openai.runtime.Times;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;

public class LogRNT {
    private static final String LOG_RNT_PATH = System.getProperty("user.home") + File.separator + ".cristalix" +
            File.separator + "updates" + File.separator + "Minigames" + File.separator + "logs" + File.separator + "latest.log";

    public static void main(String[] args) {
        long previousSize = getFileSize();
        long currentTime = System.currentTimeMillis();
        Executor executor = new Executor();
        Names names = new Names();
        Flooding flooding = new Flooding();

        // Создаем экземпляр MessageManager
        MessageManager messageManager = new MessageManager();

        // Создаем экземпляр Cleaner и передаем ему MessageManager
        Cleaner cleaner = new Cleaner(messageManager);
        Times times = new Times(messageManager); // Передаем MessageManager

        // Создаем поток для выполнения Cleaner
        Thread cleanerThread = new Thread(cleaner);
        cleanerThread.start();

        while (true) {
            long currentSize = getFileSize();
            long elapsedTime = System.currentTimeMillis() - currentTime;

            if (currentSize > previousSize && elapsedTime >= 100) {
                readNewLines(previousSize, currentSize, executor, names, times, messageManager);
                previousSize = currentSize;
                currentTime = System.currentTimeMillis();
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static long getFileSize() {
        try {
            Path path = Paths.get(LOG_RNT_PATH);
            return Files.size(path);
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }

    private static void readNewLines(long start, long end, Executor executor, Names names, Times times, MessageManager messageManager) {
        JSONArray jsonArray = new JSONArray(); // Создаем JSON массив для хранения сообщений

        try (RandomAccessFile raf = new RandomAccessFile(LOG_RNT_PATH, "r")) {
            raf.seek(start);
            String line;
            while ((line = raf.readLine()) != null) {
                line = new String(line.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);

                if (Readable.check(line)) {
                    executor.execute(line, names);
                    times.timestamp(line, names);

                    // Теперь можно вызывать метод checkForFlood() из экземпляра msgHandler

                    try {
                        // Попытка разобрать строку как JSON
                        Object parsedObject = new JSONParser().parse(line);

                        if (parsedObject instanceof JSONObject) {
                            // Это корректный JSON-объект, добавляем его в массив
                            jsonArray.add(parsedObject);
                        } else {
                            // Это не JSON-объект, можете выполнить другие действия по вашему выбору
                        }
                    } catch (ParseException e) {
                        // Обработка ошибки разбора JSON, если строка не является корректным JSON
                        // Можете добавить логирование ошибки или другие действия по вашему выбору
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Преобразуем JSON массив в объект и передаем его в DatabaseManager
        DatabaseManager.saveMessages(jsonArray);
    }

}
