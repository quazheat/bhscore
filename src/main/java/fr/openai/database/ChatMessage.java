package fr.openai.database;

import com.google.gson.annotations.SerializedName;

public class ChatMessage {
    @SerializedName("player_name")
    private String playerName;

    private String message;

    // Геттеры и сеттеры
    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
