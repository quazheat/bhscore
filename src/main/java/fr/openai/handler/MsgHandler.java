package fr.openai.handler;

import fr.openai.exec.Messages;

public class MsgHandler {
    String toFilter = null;
    public String handle (String line) {
        String message = Messages.getMessage(line);
        return toFilter;
    }
}
