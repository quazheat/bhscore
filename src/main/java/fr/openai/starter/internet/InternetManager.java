package fr.openai.starter.internet;

import fr.openai.starter.uuid.manager.LoginManager;

public class InternetManager {
    private final InternetErrorHandler internetErrorHandler = new InternetErrorHandler();
    private final LoginManager loginManager = new LoginManager();
    private final InternetChecker internetChecker = new InternetChecker();

    public void check() {
        final InternetManager internetManager = new InternetManager();
        if (internetChecker.isReachable("https://google.com")) {
            loginManager.attemptLogin();
        } else {
            internetManager.internetErrorHandler.showErrorDialog();
            System.exit(1); // Exit the program with an error
        }
    }
}
