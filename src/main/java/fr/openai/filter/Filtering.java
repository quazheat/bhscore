package fr.openai.filter;

import fr.openai.discordfeatures.DiscordDetails;
import fr.openai.discordfeatures.DiscordRPC;
import fr.openai.exec.Messages;

import fr.openai.filter.fixer.Names;
import fr.openai.notify.NotificationSystem;

public class Filtering extends ViolationHandler {
    private final DiscordRPC discordRPC;
    private int mutes = 0;
    private int warns = 0;

    private final Filters filters;
    private final Names names;

    public Filtering(NotificationSystem notificationSystem) {
        super(notificationSystem);
        this.discordRPC = new DiscordRPC();
        this.names = new Names();
        this.filters = new Filters();
    }

    public void onFilter(String name, String line) {
        String message = Messages.getMessage(line);
        String playerName = names.formatPlayerName(name);

        if (message == null) {
            return;
        }

        if (filters.hasMuteCounter(message)) {
            mutes++; // Increment the mutes counter
        }

        if (filters.hasWarnCounter(message)) {
            warns++; // Increment the warns counter
        }

        String newState = mutes + " mutes | " + warns + " warns performed."; //💢
        System.out.println(newState);
        DiscordRPC.updateRPCState(newState);
        discordRPC.updateRPC();

        if (message.contains("㰳")
                || message.contains("по причине:")
                || message.contains("\\. Причина:")
                || "Unknown".equalsIgnoreCase(name)
        ) {
            return;
        }
        boolean flood = (filters.hasManySymbols(message) || filters.hasLaugh(message) || filters.hasWFlood(message));

        if (flood && filters.hasCaps(message) && filters.hasSwearing(message)
        ) {
            handleViolation(playerName, message, "/mute " + playerName + " 2.12+2.10+2.7", message, "2.12+2.10+2.7");
            DiscordDetails discordDetails = new DiscordDetails();
            String newDetails = discordDetails.getRandomScaryPhrase();
            System.out.println("Scary Phrase: " + newDetails);
            DiscordRPC.updateRPCDetails(newDetails);
            discordRPC.updateRPC();
            return; // ALL REASONS
        }

        if (flood && filters.hasCaps(message)
        ) {
            handleViolation(playerName, message, "/mute " + playerName + " 2.10+2.12+", message, "2.10+2.12+");
            return; // FLOOD + CAPS
        }

        if (flood && filters.hasSwearing(message)
        ) {
            handleViolation(playerName, message, "/mute " + playerName + " 2.10+2.12+", message, "2.10+2.12+");
            return; // FLOOD + SWEAR
        }

        if (filters.hasCaps(message) && filters.hasSwearing(message)
        ) {
            handleViolation(playerName, message, "/mute " + playerName + " 2.12+2.7", message, "2.12+2.7");
            return; // CAPS + SWEAR
        }

        if (filters.hasSwearing(message)) {
            handleViolation(playerName, message, "/warn " + playerName + " Не матерись", message, "2.7");
            return;
        }

        if (filters.hasManySymbols(message) || filters.hasLaugh(message) || filters.hasWFlood(message)) {
            handleViolation(playerName, message, "/warn " + playerName + " Не флуди", message, "2.10+");
            return;
        }

        if (filters.hasCaps(message)) {
            handleViolation(playerName, message, "/warn " + playerName + " Не капси", message, "2.12+");
        }

    }

}
