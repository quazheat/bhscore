package fr.openai.filter;

import fr.openai.discordfeatures.DiscordRPC;
import fr.openai.discordfeatures.UpdateDiscordRPCDetails;
import fr.openai.exec.Messages;

import fr.openai.exec.Names;
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
        final String plus = "+";
        final String swear = "2.7";
        final String caps = "2.12";
        final String flood = "2.10";
        final String space = " ";


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

        boolean floodFilters = (filters.hasManySymbols(message) || filters.hasLaugh(message) || filters.hasWFlood(message));

        if (floodFilters || filters.hasCaps(message) || filters.hasSwearing(message)) {
            String newState = "Spectating " + playerName;
            DiscordRPC.updateRPCState(newState);
            discordRPC.updateRPC();
        }
        if (floodFilters && filters.hasCaps(message) && filters.hasSwearing(message)) {

            handleViolation(playerName, message, muteText + playerName + space+caps+plus+flood+plus+swear, message, caps+plus+flood+plus+swear);
            updateDiscordRPCDetails.updateDiscordRPCDetailsScary();
            return; // ALL REASONS
        }

        if (floodFilters && filters.hasCaps(message)) {
            handleViolation(playerName, message, muteText + playerName + space+caps+plus+flood, message, caps+plus+flood+plus);
            return; // FLOOD + CAPS
        }

        if (floodFilters && filters.hasSwearing(message)) {
            handleViolation(playerName, message, muteText + playerName + space+flood+plus+swear, message, flood+plus+swear+plus);
            return; // FLOOD + SWEAR
        }

        if (filters.hasCaps(message) && filters.hasSwearing(message)) {
            handleViolation(playerName, message, muteText + playerName + space+caps+plus+swear, message, caps+plus+swear+plus);
            return; // CAPS + SWEAR
        }

        if (filters.hasSwearing(message)) {
            handleViolation(playerName, message, warnText + playerName + " Не матерись", message, swear);
            return;
        }

        if (filters.hasManySymbols(message) || filters.hasLaugh(message) || filters.hasWFlood(message)) {
            handleViolation(playerName, message, warnText + playerName + " Не флуди", message, flood+plus);
            return;
        }

        if (filters.hasCaps(message)) {
            handleViolation(playerName, message, warnText + playerName + " Не капси", message, caps+plus);
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
