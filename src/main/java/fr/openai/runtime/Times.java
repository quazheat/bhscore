package fr.openai.runtime;

import fr.openai.exec.Messages;
import fr.openai.exec.Names;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Times {
    private List<JSONObject> messages = new ArrayList<>();
    private Cleaner cleaner;

    public Times(Cleaner cleaner) {
        this.cleaner = cleaner;
    }

    public void timestamp(String line, Names names) {
        String message = Messages.getMessage(line);
        String playerName = names.getFinalName(line);

        JSONObject jsonMessage = new JSONObject();
        jsonMessage.put("message_number", messages.size());
        jsonMessage.put("timestamp", System.currentTimeMillis() / 1000);
        jsonMessage.put("message", message);
        jsonMessage.put("player_name", playerName);

        messages.add(jsonMessage);
        cleaner.addMessage(jsonMessage);
    }
}