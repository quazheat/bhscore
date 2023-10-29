package fr.openai.starter;

import fr.openai.database.customui.LoginUIManager;
import fr.openai.database.customui.VersionChecker;
import fr.openai.filter.fixer.Names;
import fr.openai.notify.NotificationSystem;
import fr.openai.reader.LogRNT;
import fr.openai.starter.uuid.UuidChecker;

import java.util.ArrayList;
import java.util.List;

public class LoginManager {


    public static void attemptLogin() {
        NotificationSystem notificationSystem = new NotificationSystem();
        UuidChecker uuidChecker = new UuidChecker();
        VersionChecker.checkVersion();

        if (uuidChecker.isAllowed()) {
            LogRNT logRNT = new LogRNT(notificationSystem);
            double similarityThreshold = 0.7;
            List<String> whitelistWords = new ArrayList<>();
            Names names = new Names();

            logRNT.starter(similarityThreshold, whitelistWords, names);
            return;
        }

        LoginUIManager.loginPopup();
    }

}
