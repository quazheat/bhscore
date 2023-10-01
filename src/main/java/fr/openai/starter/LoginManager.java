package fr.openai.starter;

import fr.openai.reader.LogRNT;
import fr.openai.starter.uuid.UuidChecker;

public class LoginManager {
    public static void attemptLogin() {
        UuidChecker uuidChecker = new UuidChecker();

        if (uuidChecker.isAllowed()) {
            LogRNT logRNT = new LogRNT();
            logRNT.starter();

        } else {
            LoginUIManager.loginPopup();
        }
    }
}
