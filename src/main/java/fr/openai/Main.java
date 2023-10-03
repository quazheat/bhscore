package fr.openai;

import fr.openai.database.files.FileManager;
import fr.openai.handler.filter.Filtering;
import fr.openai.starter.internet.InternetManager;

public class Main {
    public static void main(String[] args) {


        FileManager fileManager = new FileManager();
        fileManager.readme();
        InternetManager.check();
    }
}
