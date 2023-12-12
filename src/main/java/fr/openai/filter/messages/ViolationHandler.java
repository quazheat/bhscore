package fr.openai.filter.messages;

import fr.openai.exec.utils.ClipboardUtil;
import fr.openai.exec.utils.PasteUtil;
import fr.openai.filter.FilteringModeManager;
import fr.openai.notify.NotificationSystem;
import fr.openai.notify.WindowsNotification;
import fr.openai.online.OnlineHandler;

import static java.awt.TrayIcon.MessageType.ERROR;
import static java.awt.TrayIcon.MessageType.INFO;

public abstract class ViolationHandler {
    protected final PasteUtil pasteUtil = new PasteUtil() {
        @Override
        public void pasteFromClipboard() {
            super.pasteFromClipboard();
        }
    };
    protected final NotificationSystem notificationSystem;

    public ViolationHandler(NotificationSystem notificationSystem) {
        this.notificationSystem = notificationSystem;
    }

    void handleViolation(String playerName, String message, String loyalAction, String loyalMessage, String rageAction) {
        System.out.println(OnlineHandler.hubDetected);
        if (OnlineHandler.hubDetected) {
            rageAction = "vc+" + rageAction;

        }

        if (OnlineHandler.hubDetected && loyalAction.contains("+")) {
            loyalAction = loyalAction + "+vc";
        }

        if (FilteringModeManager.isLoyalModeEnabled()) {
            showLoyalNotification(loyalMessage);

            copyToClipboard(loyalAction);
            pasteUtil.pasteFromClipboard();
            return;
        }

        if (!FilteringModeManager.isRageModeEnabled()) {
            notificationSystem.showNotification(playerName, loyalMessage);
            System.out.println(playerName);
            return;
        }

        showRageNotification(message);
        copyToClipboard("/mute " + playerName + " " + rageAction);
        pasteUtil.pasteFromClipboard();
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


