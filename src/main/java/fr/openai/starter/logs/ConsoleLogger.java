package fr.openai.starter.logs;

import fr.openai.runtime.TimeUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Date;

import static fr.openai.runtime.TimeUtil.getCurrentTime;

public class ConsoleLogger {
    private static final String LOG_FILE_NAME = "last.log";

    public static void consoleLog() {
        String currentTime = TimeUtil.getCurrentTime();
        // Удалить существующий файл last.log, если он существует
        File logFile = new File(LOG_FILE_NAME);
        if (logFile.exists() && logFile.isFile()) {
            logFile.delete();
        }

        try {
            // Создать новый файл last.log
            logFile.createNewFile();

            // Перенаправить стандартный вывод в файл last.log
            PrintStream fileOutputStream = new PrintStream(new FileOutputStream(logFile));
            System.setOut(fileOutputStream);

            // Пример использования: вывод текста в консоль и запись в файл
            System.out.println("начало last.log.");
            System.out.println(currentTime);
            System.out.println(" ");
            System.out.println(" \\\\\\\\\\\\ ");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
