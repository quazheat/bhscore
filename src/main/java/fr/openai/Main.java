package fr.openai;

import fr.openai.database.files.FileManager;
import fr.openai.handler.filter.Filtering;
import fr.openai.starter.internet.InternetManager;
import fr.openai.starter.logs.ConsoleLogger;

public class Main {
    public static void main(String[] args) {
        ConsoleLogger.consoleLog();

        FileManager fileManager = new FileManager();
        fileManager.readme();
        InternetManager.check();
    }
}
