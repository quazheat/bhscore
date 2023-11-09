package fr.openai.database;

import fr.openai.discordfeatures.DiscordRPCDiag;

public abstract class UsernameProvider {
    private final ConfigManager configManager = new ConfigManager();

    public String getUsername() {
        String username = configManager.getUsername();
        if (username == null || username.length() <= 3) {
            username = DiscordRPCDiag.getUsername();
            if (username == null) {
                return "";
            }
        }
        return username;
    }
}