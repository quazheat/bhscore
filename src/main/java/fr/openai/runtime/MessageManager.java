package fr.openai.runtime;

import org.json.simple.JSONObject;

import java.util.concurrent.ConcurrentLinkedQueue;

public class MessageManager {
    private final ConcurrentLinkedQueue<JSONObject> messages = new ConcurrentLinkedQueue<>();

    public void addMessage(JSONObject jsonMessage) {
        messages.add(jsonMessage);
    }

    public ConcurrentLinkedQueue<JSONObject> getMessages() {
        return messages;
    }
}
