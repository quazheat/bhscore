package fr.openai;

import fr.openai.database.files.ConnectDb;
import fr.openai.database.files.FileManager;
import fr.openai.starter.internet.InternetManager;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        FileManager fileManager = new FileManager();
        fileManager.readme();
        InternetManager.check();
    }
}
