package fr.openai.database.customui;

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRichPresence;
import java.time.Instant;
import java.time.Duration;

public class DiscordRPC {
    private static final DiscordRichPresence presence = new DiscordRichPresence();
    private static String state = "Looking for something..."; // Initialize the state
    private static final long startTime = System.currentTimeMillis() / 1000; // Store the start time
    private static boolean isRPCEnabled = true; // Flag to enable or disable the RPC

    public static void updateRPC() {
        if (!isRPCEnabled) {
            return; // Don't update the RPC if it's disabled
        }

        final String username = DiscordRPCApp.getUsername();
        Instant currentTime = Instant.ofEpochSecond(System.currentTimeMillis() / 1000);
        Instant startTimeInstant = Instant.ofEpochSecond(startTime);
        Duration duration = Duration.between(startTimeInstant, currentTime);
        String elapsedTime = formatDuration(duration);

        club.minnced.discord.rpc.DiscordRPC lib = club.minnced.discord.rpc.DiscordRPC.INSTANCE;
        DiscordEventHandlers handlers = new DiscordEventHandlers();
        lib.Discord_Initialize("1058737271341338655", handlers, true, null);

        presence.details = "Следим за игроками (" + elapsedTime + ")"; // The main text field
        presence.largeImageKey = "https://media.tenor.com/22oV0lhc-H8AAAAd/anime-girl-crazy-physco-black-and-white.gif";
        presence.largeImageText = "BHScore";
        presence.smallImageKey = "minigames";
        presence.smallImageText = username;
        presence.state = state;

        presence.partySize = 0; // Number of players in the party
        presence.partyMax = 0; // Maximum size of the party
        presence.matchSecret = "match-secret";
        presence.joinSecret = "join-secret";
        presence.spectateSecret = "spectate-secret";
        presence.instance = (byte) 1; // For true

        lib.Discord_UpdatePresence(presence);
    }

    public static void updateRPCState(String newState) {
        state = newState; // Update the class-level state variable
        club.minnced.discord.rpc.DiscordRPC lib = club.minnced.discord.rpc.DiscordRPC.INSTANCE;
        lib.Discord_UpdatePresence(presence);
    }

    public static void setRPCEnabled(boolean enabled) {
        isRPCEnabled = enabled;
    }

    private static String formatDuration(Duration duration) {
        long hours = duration.toHours();
        long minutes = duration.minusHours(hours).toMinutes();
        long seconds = duration.minusHours(hours).minusMinutes(minutes).getSeconds();

        if (hours > 0) {
            return String.format("%02d:%02d:%02d", hours, minutes, seconds);
        } else {
            return String.format("%02d:%02d", minutes, seconds);
        }
    }
}
