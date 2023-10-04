package fr.openai.runtime;

import com.google.gson.JsonObject;
import fr.openai.exec.Messages;
import fr.openai.database.Names;

import java.util.List;

public class Times {
    private final List<JsonObject> liveChat; // Список live_chat

    public Times(List<JsonObject> liveChat) {
        this.liveChat = liveChat;
    }

    public void timestamp(String line, Names names) {
        String message = Messages.getMessage(line);
        String playerName = names.getFinalName(line);

        JsonObject jsonMessage = new JsonObject();
        jsonMessage.addProperty("timestamp", System.currentTimeMillis() / 1000);
        jsonMessage.addProperty("message", message);
        jsonMessage.addProperty("player_name", playerName);
        System.out.println(playerName + " " + message);
        liveChat.add(jsonMessage);
    }
}
