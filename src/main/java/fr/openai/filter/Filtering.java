package fr.openai.filter;

import fr.openai.discordfeatures.DiscordRPC;
import fr.openai.discordfeatures.UpdateDiscordRPCDetails;
import fr.openai.exec.Messages;

import fr.openai.filter.fixer.Names;
import fr.openai.notify.NotificationSystem;

public class Filtering extends ViolationHandler {
    private final DiscordRPC discordRPC;
    public static int warns = 0;
    public static int mutes = 0;
    private final Filters filters;
    UpdateDiscordRPCDetails updateDiscordRPCDetails;
    private final Names names;

    public Filtering(NotificationSystem notificationSystem) {
        super(notificationSystem);
        this.discordRPC = new DiscordRPC();
        this.names = new Names();
        this.filters = new Filters();
    }

    public void onFilter(String name, String line) {
        final String warnText = "/warn ";
        final String muteText = "/mute ";

        String message = Messages.getMessage(line);
        String playerName = names.formatPlayerName(name);

        if (message == null) {
            return;
        }
        updateCounters(message);
        JustAnotherFilter justAnotherFilter = new JustAnotherFilter(name, message);
        if (justAnotherFilter.shouldSkip()) {
            return;
        }

        boolean flood = (filters.hasManySymbols(message) || filters.hasLaugh(message) || filters.hasWFlood(message));

        if (flood || filters.hasCaps(message) || filters.hasSwearing(message)) {
            String newState = "Spectating " + playerName;
            DiscordRPC.updateRPCState(newState);
            discordRPC.updateRPC();
        }
        if (flood && filters.hasCaps(message) && filters.hasSwearing(message)) {
            handleViolation(playerName, message, muteText + playerName + " 2.12+2.10+2.7", message, "2.12+2.10+2.7");
            updateDiscordRPCDetails.updateDiscordRPCDetailsScary();
            return; // ALL REASONS
        }

        if (flood && filters.hasCaps(message)) {
            handleViolation(playerName, message, muteText + playerName + " 2.10+2.12+", message, "2.10+2.12+");
            return; // FLOOD + CAPS
        }

        if (flood && filters.hasSwearing(message)) {
            handleViolation(playerName, message, muteText + playerName + " 2.10+2.12+", message, "2.10+2.12+");
            return; // FLOOD + SWEAR
        }

        if (filters.hasCaps(message) && filters.hasSwearing(message)) {
            handleViolation(playerName, message, muteText + playerName + " 2.12+2.7", message, "2.12+2.7");
            return; // CAPS + SWEAR
        }

        if (filters.hasSwearing(message)) {
            handleViolation(playerName, message, warnText + playerName + " Не матерись", message, "2.7");
            return;
        }

        if (filters.hasManySymbols(message) || filters.hasLaugh(message) || filters.hasWFlood(message)) {
            handleViolation(playerName, message, warnText + playerName + " Не флуди", message, "2.10+");
            return;
        }

        if (filters.hasCaps(message)) {
            handleViolation(playerName, message, warnText + playerName + " Не капси", message, "2.12+");
        }
    }

    private void updateCounters(String message) {
        if (filters.hasMuteCounter(message)) {
            mutes++;
        }

        if (filters.hasWarnCounter(message)) {
            warns++;
        }
    }

    public static int getWarns() {
        return warns;
    }

    public static int getMutes() {
        return mutes;
    }

}
