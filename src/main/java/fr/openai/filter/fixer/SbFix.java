package fr.openai.filter.fixer;

public class SbFix {
    public static String fixMessage(String message) {
        if (message.contains("[CHAT] ")) {
            int colonIndex = message.indexOf(':');
            if (colonIndex != -1) {
                return message.substring(colonIndex + 1).trim();
            }
        }
        return message;
    }
}
