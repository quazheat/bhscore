package fr.openai.exec;


import fr.openai.filter.Validator;

public class Messages {
    public static String getMessage(String line) {
        if (Validator.isNotValid(line)) {

            System.out.println(line + " INVALID");
            return null;
        }

        String message = extract(line);
        return !message.isEmpty() ? message : null;
    }


    private static String extract(String line) {
        int chatIndex = line.indexOf(": [CHAT]");
        if (chatIndex == -1) {
            // The entire line is considered the message if ": [CHAT]" is not found
            return line;
        }

        int start = chatIndex + ": [CHAT]".length(); // Start after the ": [CHAT]"
        int end = line.indexOf("»", chatIndex);

        if (end != -1) {
            start = end + 1;
        } else {
            int colonIndex = line.indexOf(":", chatIndex);
            if (colonIndex != -1) {
                start = colonIndex + 1;
            }
        }
        System.out.println(line.substring(start).trim() + " TRIMMED");
        return line.substring(start).trim();
    }


    public String TestMessage(String rawString) {
        String message = getMessage(rawString);
        if (message != null) {
            System.out.println("Extracted Message: " + message);
            return message;
        } else {
            System.out.println("No valid message found in the input string.");
            return "No valid message found";
        }
    }

}
