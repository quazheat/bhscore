package fr.openai.starter.internet;

import fr.openai.discordfeatures.DiscordRPC;
import fr.openai.discordfeatures.DiscordRPCApp;
import fr.openai.starter.uuid.manager.LoginManager;

public class InternetManager {
    public static void check() {
        if (InternetChecker.isReachable("https://google.com")) {
            DiscordRPCApp discordRPCApp = new DiscordRPCApp();
            discordRPCApp.setModal(true); // MODAL DIALOG
            discordRPCApp.setVisible(true);
            DiscordRPC.updateRPC();
            LoginManager.attemptLogin();
        } else {
            InternetErrorHandler.showErrorDialog();
            System.exit(1); // Завершаем программу с ошибкой
        }
    }
}
