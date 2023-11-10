package fr.openai.filter;

public class JustAnotherFilter {

    boolean shouldSkip;

    public JustAnotherFilter(String name, String message) {
        shouldSkip = message.contains("㰳")
                || message.contains("по причине:")
                || message.contains("\\[CHAT]\\s{2,}")
                || message.contains("Ошибка OpenGL:")
                || message.contains(". Причина:")
                || "Unknown".equalsIgnoreCase(name);
    }

    public boolean shouldSkip() {
        return shouldSkip;
    }

}
