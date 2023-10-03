package fr.openai.runtime;

import fr.openai.exec.Messages;
import fr.openai.database.Names;
import org.json.simple.JSONObject;

import java.util.List;

public class Times {
    private List<JSONObject> liveChat; // Список live_chat

    public Times(List<JSONObject> liveChat) {
        this.liveChat = liveChat;
    }

    public void timestamp(String line, Names names) {
        String message = Messages.getMessage(line);
        String playerName = names.getFinalName(line);

        JSONObject jsonMessage = new JSONObject();
        jsonMessage.put("timestamp", System.currentTimeMillis() / 1000);
        jsonMessage.put("message", message);
        jsonMessage.put("player_name", playerName);

        // Вместо добавления сообщения в MessageManager, добавляем его в liveChat
        liveChat.add(jsonMessage);
    }
}
