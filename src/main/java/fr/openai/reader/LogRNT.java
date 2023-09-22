package fr.openai.reader;

import fr.openai.exec.Executor;
import fr.openai.exec.Names;
import fr.openai.handler.MsgHandler;
import fr.openai.handler.filter.Flooding;
import fr.openai.runtime.Cleaner;
import fr.openai.runtime.Times;
import fr.openai.runtime.DbSaver; // Импортируем DbSaver для сохранения базы данных

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;

public class LogRNT {
    private static final String LOG_RNT_PATH = System.getProperty("user.home") + File.separator + ".cristalix" +
            File.separator + "updates" + File.separator + "Minigames" + File.separator + "logs" + File.separator + "latest.log";
    private static MsgHandler msgHandler;

    public static void main(String[] args) {
        long previousSize = getFileSize();
        long currentTime = System.currentTimeMillis();
        Executor executor = new Executor();
        Names names = new Names();
        Flooding flooding = new Flooding();

        // Создаем экземпляр Cleaner
        Cleaner cleaner = new Cleaner();

        // Создаем экземпляр Times, передавая cleaner
        Times times = new Times(cleaner);

        // Создаем экземпляр MsgHandler, передавая cleaner и DbSaver

        // Создаем поток для выполнения Cleaner
        Thread cleanerThread = new Thread(cleaner);
        cleanerThread.start();

        while (true) {
            long currentSize = getFileSize();
            long elapsedTime = System.currentTimeMillis() - currentTime;

            if (currentSize > previousSize && elapsedTime >= 100) {
                readNewLines(previousSize, currentSize, executor, names, flooding, times);
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

    private static void readNewLines(long start, long end, Executor executor, Names names, Flooding flooding, Times times) {
        try (RandomAccessFile raf = new RandomAccessFile(LOG_RNT_PATH, "r")) {
            raf.seek(start);
            String line;
            while ((line = raf.readLine()) != null) {
                line = new String(line.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);

                if (Readable.check(line)) {
                    executor.execute(line, names);
                    times.timestamp(line, names); // Use the Times instance to call timestamp method

                    // Теперь можно вызывать метод checkForFlood() из экземпляра msgHandler

                    // Сохраняем базу данных с помощью DbSaver
                    // Нет необходимости вызывать DbSaver.saveDatabase() здесь, так как это делает MsgHandler
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}