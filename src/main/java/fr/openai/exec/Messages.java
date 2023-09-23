package fr.openai.exec;

import fr.openai.handler.filter.Validator;

public class Messages {
    public static String getMessage(String line) {
        if (Validator.isValid(line)) {
            return null;
        }

        String message = extract(line);
        return !message.isEmpty() ? message : null;
    }

    private static String extract(String line) {
        int chatIndex = line.indexOf(": [CHAT]");
        if (chatIndex == -1) {
            return line;
        }

        int start = -1;
        int end = line.indexOf("»", chatIndex);

        if (end != -1) {
            start = end + 1;
        } else {
            int colonIndex = line.indexOf(":", chatIndex);
            if (colonIndex != -1) {
                start = colonIndex + 1;
            }
        }

        return (start != -1) ? line.substring(start).trim() : line;
    }
}
