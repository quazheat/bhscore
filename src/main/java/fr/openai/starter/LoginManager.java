package fr.openai.starter;

import fr.openai.database.customui.VersionChecker;
import fr.openai.database.customui.VersionGUI;
import fr.openai.notify.NotificationSystem;
import fr.openai.notify.WindowsNotification;
import fr.openai.reader.LogRNT;
import fr.openai.starter.uuid.UuidChecker;

public class LoginManager {


    public static void attemptLogin() throws InterruptedException {

        // Остальной код вашей функции attemptLogin() остается без изменений
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
