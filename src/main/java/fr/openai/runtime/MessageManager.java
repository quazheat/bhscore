package fr.openai.runtime;

import com.google.gson.JsonObject;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MessageManager {
    private final ConcurrentLinkedQueue<JsonObject> messages = new ConcurrentLinkedQueue<>();

    public void addMessage(JsonObject jsonMessage) {
        messages.add(jsonMessage);
    }

    public ConcurrentLinkedQueue<JsonObject> getMessages() {
        return messages;
    }
}
