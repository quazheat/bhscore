package fr.openai.reader;

import java.io.*;

public class LogFile {
    public void main(String[] args) {
        String logRNTPath = System.getProperty("user.home") + File.separator + ".cristalix" +
                File.separator + "updates" + File.separator + "Minigames" + File.separator + "logs" + File.separator + "latest.log";

        File logRNT = new File(logRNTPath);

        // Проверяем, существует ли файл
        if (!logRNT.exists()) {
            System.err.println("Зайдите в игру.");
        }
    }
}
