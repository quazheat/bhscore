package fr.openai.reader;

import fr.openai.exec.Executor;
import fr.openai.exec.Names;
import fr.openai.handler.filter.Flooding;
import fr.openai.reader.Readable;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;

public class LogRNT {
    private static final String LOG_RNT_PATH = System.getProperty("user.home") + File.separator + ".cristalix" +
            File.separator + "updates" + File.separator + "Minigames" + File.separator + "logs" + File.separator + "latest.log";

    public static void main(String[] args) {
        long previousSize = getFileSize();
        long currentTime = System.currentTimeMillis();
        Executor executor = new Executor(); // Создаем экземпляр Executor
        Names names = new Names(); // Создаем экземпляр Names
        Flooding flooding = new Flooding(); // Создаем экземпляр Flooding

        while (true) {
            long currentSize = getFileSize();
            long elapsedTime = System.currentTimeMillis() - currentTime;

            if (currentSize > previousSize && elapsedTime >= 100) {
                readNewLines(previousSize, currentSize, executor, names, flooding);
                // Передаем экземпляры Executor и Names
                previousSize = currentSize;
                currentTime = System.currentTimeMillis();
            }

            try {
                Thread.sleep(100); // Подождем 100 миллисекунд перед следующей проверкой
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

    private static void readNewLines(long start, long end, Executor executor, Names names, Flooding flooding) {
        try (RandomAccessFile raf = new RandomAccessFile(LOG_RNT_PATH, "r")) {
            raf.seek(start);
            String line;
            while ((line = raf.readLine()) != null) {
                // Чтобы открыть файл в кодировке UTF-8
                line = new String(line.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);

                if (Readable.check(line)) {
                    executor.execute(line, names); // Вызываем метод execute через экземпляр Executor и передаем экземпляр Names

                    // Добавляем сообщение в систему Flooding
                    flooding.addMessage(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
