package fr.openai.starter.internet;

import fr.openai.starter.LoginManager;

public class InternetManager {
    public static void check() {
        if (InternetChecker.isReachable("https://pastebin.com")) {
            LoginManager.attemptLogin();
        } else {
            InternetErrorHandler.showErrorDialog();
            System.exit(1); // Завершаем программу с ошибкой
        }
    }
}
