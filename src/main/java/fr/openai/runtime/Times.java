package fr.openai.runtime;

import fr.openai.exec.Messages;
import fr.openai.database.Names;
import fr.openai.filter.ChatMessage;

import java.util.ArrayList;
import java.util.List;

public class Times {
    private final List<ChatMessage> liveChat; // Список live_chat

    public Times() {
        this.liveChat = new ArrayList<>();
    }

    public void timestamp(String line, Names names) {
        String message = Messages.getMessage(line);
        String playerName = names.getFinalName(line);
        long timestamp = System.currentTimeMillis() / 1000;

        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setMessage(message);
        chatMessage.setPlayerName(playerName);
        chatMessage.setTimestamp(timestamp);

        if (playerName == null || playerName.equals("Unknown")) {
            return;
        }

        liveChat.add(chatMessage); // Добавляем chatMessage в liveChat
    }

    public List<ChatMessage> getLiveChat() {
        return liveChat;
    }
}
