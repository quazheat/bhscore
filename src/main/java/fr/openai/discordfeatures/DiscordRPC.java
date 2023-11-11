package fr.openai.discordfeatures;

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRichPresence;
import fr.openai.database.UsernameProvider;

public class DiscordRPC extends UsernameProvider {
    private static final DiscordRichPresence presence = new DiscordRichPresence();
    private static String details = "Big brother watching you"; // The main text field
    private static String state = "Looking for something..."; // Initialize the state
    private static final long startTime = System.currentTimeMillis() / 1000; // Store the start time
    boolean isRPCEnabled = true; // Flag to enable or disable the RPC
    private final DiscordEventHandlers handlers = new DiscordEventHandlers();

    public void updateRPC() {
        if (!isRPCEnabled) {
            return; // Don't update the RPC if it's disabled
        }

        String username = getUsername();

        club.minnced.discord.rpc.DiscordRPC lib = club.minnced.discord.rpc.DiscordRPC.INSTANCE;
        lib.Discord_Initialize("1058737271341338655", handlers, true, null);

        presence.details = details;
        presence.state = state;
        presence.startTimestamp = startTime;
        presence.largeImageKey = "https://media.tenor.com/22oV0lhc-H8AAAAd/anime-girl-crazy-physco-black-and-white.gif";
        presence.largeImageText = "BHScore";
        presence.smallImageKey = "minigames";
        //presence.smallImageKey = "https://mc-heads.net/avatar/" + username;
        presence.smallImageText = username;
        presence.partySize = 0; // Number of players in the party
        presence.partyMax = 0; // Maximum size of the party
        presence.matchSecret = "match-secret";
        presence.spectateSecret = "spectate-secret";
        presence.instance = (byte) 1; // For true

        // Добавляем метаданные для кнопки
        presence.partyId = "party-id"; // Уникальный идентификатор группы
        presence.joinSecret = "quazheat"; // Секретный ключ для присоединения к группе

        lib.Discord_UpdatePresence(presence);
    }

    public void updateRPCState(String newState) {
        if (!isRPCEnabled) {
            return;
        }
        state = newState; // Update the class-level state variable
        club.minnced.discord.rpc.DiscordRPC lib = club.minnced.discord.rpc.DiscordRPC.INSTANCE;
        lib.Discord_UpdatePresence(presence);
    }

    public void updateRPCDetails(String newDetails) {
        if (!isRPCEnabled) {
            return;
        }
        details = newDetails; // Update the class-level state variable
        club.minnced.discord.rpc.DiscordRPC lib = club.minnced.discord.rpc.DiscordRPC.INSTANCE;
        lib.Discord_UpdatePresence(presence);
    }

    public void setRPCEnabled(boolean enabled) {
        isRPCEnabled = enabled;
    }
}
