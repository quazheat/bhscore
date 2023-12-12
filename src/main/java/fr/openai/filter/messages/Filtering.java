package fr.openai.filter.messages;

import fr.openai.discordfeatures.DiscordRPC;
import fr.openai.discordfeatures.UpdateDiscordRPCDetails;
import fr.openai.exec.Messages;
import fr.openai.exec.Names;
import fr.openai.filter.Filters;
import fr.openai.filter.JustAnotherFilter;
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
        final String swear = "2.";
        final String caps = "2.12";
        final String flood = "2.10";
        final String banWord = "2.4";
        final String space = " ";
        final String specText = "Spectating ";
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

        if (floodFilters || filters.hasCaps(message) || filters.hasSwearing(message) || filters.hasBanWord(message)) {
            String newState = specText + playerName;

            discordRPC.updateRPCState(newState);
        }

        if (floodFilters && filters.hasCaps(message) && filters.hasSwearing(message) && filters.hasBanWord(message)) {
            handleViolation(playerName, message, muteText + playerName + " 3.7", message,
                    "3.7");
            updateDiscordRPCDetails.updateDiscordRPCDetailsScary();
            return; // FLOOD + CAPS + SWEAR + BAN_WORD
        }

        if (floodFilters && filters.hasCaps(message) && filters.hasSwearing(message)) {
            handleViolation(playerName, message, muteText + playerName + space + caps + plus + flood + plus + swear, message,
                    caps + plus + flood + plus + swear);
            updateDiscordRPCDetails.updateDiscordRPCDetailsScary();
            return; // FLOOD + CAPS + SWEAR
        }

        if (floodFilters && filters.hasCaps(message) && filters.hasBanWord(message)) {
            handleViolation(playerName, message, muteText + playerName + space + flood + plus + caps + plus + banWord, message,
                    flood + plus + caps + plus + banWord + plus);
            return; // FLOOD + CAPS + BAN_WORD
        }

        if (filters.hasBanWord(message) && filters.hasSwearing(message)) {
            handleViolation(playerName, message, muteText + playerName + space + banWord + plus + swear, message,
                    banWord + plus + swear);
            return; // BAN_WORD + SWEAR
        }

        if (floodFilters && filters.hasSwearing(message)) {
            handleViolation(playerName, message, muteText + playerName + space + flood + plus + swear, message,
                    flood + plus + swear);
            return; // FLOOD + SWEAR
        }

        if (filters.hasCaps(message) && filters.hasSwearing(message)) {
            handleViolation(playerName, message, muteText + playerName + space + caps + plus + swear, message,
                    caps + plus + swear);
            return; // CAPS + SWEAR
        }

        if (floodFilters && filters.hasBanWord(message)) {
            handleViolation(playerName, message, muteText + playerName + space + flood + plus + banWord, message,
                    flood + plus + banWord + plus);
            return; // FLOOD + BAN_WORD
        }

        if (filters.hasCaps(message) && filters.hasBanWord(message)) {
            handleViolation(playerName, message, muteText + playerName + space + caps + plus + banWord, message,
                    caps + plus + banWord + plus);
            return; // CAPS + BAN_WORD
        }

        if (floodFilters && filters.hasCaps(message)) {
            handleViolation(playerName, message, muteText + playerName + space + caps + plus + flood, message,
                    caps + plus + flood + plus);
            return; // FLOOD + CAPS
        }

        if (filters.hasSwearing(message)) {
            handleViolation(playerName, message, warnText + playerName + " Не ругайся", message, swear);
            return;
        }

        if (filters.hasBanWord(message)) {
            handleViolation(playerName, message, warnText + playerName + " Без банвордов", message,
                    banWord + plus);
            return;
        }

        if (filters.hasManySymbols(message) || filters.hasLaugh(message) || filters.hasWFlood(message)) {
            handleViolation(playerName, message, warnText + playerName + " Не флуди", message,
                    flood + plus);
            return;
        }

        if (filters.hasCaps(message)) {
            handleViolation(playerName, message, warnText + playerName + " Не капси", message,
                    caps + plus);
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
