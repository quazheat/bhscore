package fr.openai.starter.uuid.manager;

import fr.openai.starter.VersionChecker;
import fr.openai.ui.LoginUIManager;
import fr.openai.notify.NotificationSystem;
import fr.openai.reader.LogRNT;
import fr.openai.starter.uuid.UuidChecker;

public class LoginManager {


    public static void attemptLogin() {
        NotificationSystem notificationSystem = new NotificationSystem();
        UuidChecker uuidChecker = new UuidChecker();
        VersionChecker.checkVersion();
        if (uuidChecker.isAllowed()) {
            LogRNT logRNT = new LogRNT(notificationSystem);
            logRNT.starter();

            return;
        }

        LoginUIManager.loginPopup();
    }
}
