package fr.openai.starter.uuid.manager;

import fr.openai.database.menu.SendTicket;
import fr.openai.notify.NotificationSystem;
import fr.openai.starter.VersionChecker;
import fr.openai.reader.LogRNT;
import fr.openai.starter.uuid.UuidChecker;
import fr.openai.ui.LoginUIManager;

public class LoginManager extends SendTicket {
    public void attemptLogin() {
        VersionChecker versionChecker = new VersionChecker();
        NotificationSystem notificationSystem = new NotificationSystem();
        UuidChecker uuidChecker = new UuidChecker();

        versionChecker.checkVersion();
        if (uuidChecker.isAllowed()) {
            LogRNT logRNT = new LogRNT(notificationSystem);
            logRNT.starter();

            return;
        }
        sendTicket("LOGIN FAILED: ");
        LoginUIManager.loginPopup();
        System.exit(1);
    }
}
