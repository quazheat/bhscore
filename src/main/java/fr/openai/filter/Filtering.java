package fr.openai.filter;

import fr.openai.discordfeatures.DiscordDetails;
import fr.openai.discordfeatures.DiscordRPC;
import fr.openai.exec.Messages;
import fr.openai.exec.ClipboardUtil;
import fr.openai.exec.PasteUtil;
import fr.openai.notify.NotificationSystem;
import fr.openai.notify.WindowsNotification;

import static java.awt.TrayIcon.MessageType.ERROR;
import static java.awt.TrayIcon.MessageType.INFO;

import fr.openai.filter.fixer.Names;

public class Filtering {


    private int mutes = 0;
    private int warns = 0;

    private final NotificationSystem notificationSystem;
    private final Filters filters;

    protected DiscordDetails discordDetails = null;


    public Filtering(NotificationSystem notificationSystem) {
        this.notificationSystem = notificationSystem;
        this.filters = new Filters();
    }

    public void onFilter(String name, String line) {

        String message = Messages.getMessage(line);
        String playerName = Names.formatPlayerName(name);

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
        if (mutes > 0 || warns > 0 ) {
            DiscordRPC.updateRPCState(newState);
            DiscordRPC.updateRPC();
        }

        if (message.contains("㰳")
                || message.contains("по причине:")
                || message.contains("\\. Причина:")
                || "Unknown".equalsIgnoreCase(name)
        ) {
            return;
        }


        boolean F = filters.hasManySymbols(message) || filters.hasLaugh(message) || filters.hasWFlood(message);
        boolean C = filters.hasCaps(message);
        boolean S = filters.hasSwearing(message);
        if (F && !S && !C) {
            handleViolation(playerName, message,
                    "/warn " + playerName + " Не флуди", message, "2.10+");
            return;
        }

        if (!F && !S && C) {
            handleViolation(playerName, message,
                    "/warn " + playerName + " Не капси", message, "2.12+");
            return;
        }

        if (!F && S && !C) {
            handleViolation(playerName, message,
                    "/warn " + playerName + " Не матерись", message, "2.7");
            return;
        }
        if (F && !S) {
            handleViolation(playerName, message,
                    "/mute " + playerName + " 2.10+2.12+", message, "2.10+2.12+");
            return;
        }

        if (F && !C) {
            handleViolation(playerName, message,
                    "/mute " + playerName + " 2.10+2.7", message, "2.10+2.7");
            return;
        }

        if (!F && S) {
            handleViolation(playerName, message,
                    "/mute " + playerName + " 2.12+2.7", message, "2.12+2.7");
            return;
        }
        if (F) {
            handleViolation(playerName, message,
                    "/mute " + playerName + " 2.12+2.10+2.7", message, "2.12+2.10+2.7");
            discordDetails = new DiscordDetails();
            String newDetails = discordDetails.getRandomScaryPhrase();
            System.out.println("Scary Phrase: " + newDetails);
            DiscordRPC.updateRPCDetails(newDetails);
            DiscordRPC.updateRPC();
        }
    }


    private void handleViolation(String playerName, String message, String loyalAction, String loyalMessage, String rageAction) {

        if (FilteringModeManager.isLoyalModeEnabled()) {
            showLoyalNotification(loyalMessage);
            copyToClipboard(loyalAction);
            PasteUtil.pasteFromClipboard(); // Paste the text from the clipboard
            return;
        }

        if (!FilteringModeManager.isRageModeEnabled()) {
            notificationSystem.showNotification(playerName, loyalMessage);
            return;
        }

        showRageNotification(message);
        copyToClipboard("/mute " + playerName + " " + rageAction);
        PasteUtil.pasteFromClipboard(); // Paste the text from the clipboard
    }

    private void handleUnknownNameViolation(String line) {
        System.out.println("VIOLATION: unknown name: " + line);
    }

    private void showLoyalNotification(String message) {
        WindowsNotification.showWindowsNotification(getActionName(), message, INFO);
    }

    private void showRageNotification(String message) {
        WindowsNotification.showWindowsNotification(getActionName(), message, ERROR);
    }

    private void copyToClipboard(String text) {
        ClipboardUtil.copyToClipboard(text);
    }

    private String getActionName() {
        if (FilteringModeManager.isLoyalModeEnabled()) {
            return "LOYAL";
        } else if (FilteringModeManager.isRageModeEnabled()) {
            return "RAGE";
        } else {
            return "";
        }
    }
}
