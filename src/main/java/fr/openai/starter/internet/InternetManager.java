package fr.openai.starter.internet;

import fr.openai.starter.LoginManager;

public class InternetManager {
    public static void check() {
        if (InternetChecker.isReachable("https://google.com")) {
            LoginManager.attemptLogin();

            return;
        }
        InternetErrorHandler.showErrorDialog();
        System.exit(1); // Завершаем программу с ошибкой
    }
}
