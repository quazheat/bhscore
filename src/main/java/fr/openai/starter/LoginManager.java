package fr.openai.starter;

import fr.openai.notify.NotificationSystem;
import fr.openai.notify.WindowsNotification;
import fr.openai.reader.LogRNT;
import fr.openai.starter.uuid.UuidChecker;

public class LoginManager {
    public static void attemptLogin() throws InterruptedException {
        NotificationSystem notificationSystem = new NotificationSystem();
        UuidChecker uuidChecker = new UuidChecker();

        if (uuidChecker.isAllowed()) {
            LogRNT logRNT = new LogRNT(notificationSystem);
            logRNT.starter();


        } else {
            LoginUIManager.loginPopup();
        }
    }
}
