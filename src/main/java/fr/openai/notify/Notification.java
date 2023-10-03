package fr.openai.notify;

import javax.swing.*;
import java.awt.*;

public class Notification {
    private final String playerName;
    private final String violation;

    public Notification(String playerName, String violation) {
        this.playerName = playerName;
        this.violation = violation;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getViolation() {
        return violation;
    }
}
