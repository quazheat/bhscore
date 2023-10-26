package fr.openai.starter.internet;

import fr.openai.starter.LoginManager;

public class InternetManager {
    public static void check() throws InterruptedException {
        if (InternetChecker.isReachable("https://google.com")) {
            LoginManager.attemptLogin();
        } else {
            InternetErrorHandler.showErrorDialog();
            System.exit(1); // Завершаем программу с ошибкой
        }
    }
}
