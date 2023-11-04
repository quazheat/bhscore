package fr.openai;

import fr.openai.starter.internet.InternetManager;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        InternetManager internetManager = new InternetManager();
        internetManager.check();
    }
}
