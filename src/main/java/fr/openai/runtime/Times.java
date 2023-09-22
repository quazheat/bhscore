package fr.openai.runtime;

import fr.openai.exec.Messages;
import fr.openai.exec.Names;
import org.json.simple.JSONObject;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Times {
    private MessageManager messageManager;

    public Times(MessageManager messageManager) {
        this.messageManager = messageManager;
    }

    public void timestamp(String line, Names names) {
        String message = Messages.getMessage(line);
        String playerName = names.getFinalName(line);

        JSONObject jsonMessage = new JSONObject();
        jsonMessage.put("message_number", messageManager.getMessages().size());
        jsonMessage.put("timestamp", System.currentTimeMillis() / 1000);
        jsonMessage.put("message", message);
        jsonMessage.put("player_name", playerName);

        // Вместо добавления сообщения в локальный список, добавляем его в MessageManager
        messageManager.addMessage(jsonMessage);
    }
}
