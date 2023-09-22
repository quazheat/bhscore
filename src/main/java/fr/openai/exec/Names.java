package fr.openai.exec;

import java.util.regex.Pattern;

public class Names {
    private String finalName;
    private final Pattern SQUARE_BRACKETS_PATTERN = Pattern.compile("\\[.+?]");

    private final Pattern HASHTAG_PATTERN = Pattern.compile("#.*");
    private final Pattern PIPE_PATTERN = Pattern.compile(".+?┃");

    public String getFinalName(String line) {
        if (line.contains("»")) {
            int start = line.indexOf("[Client thread/INFO]: [CHAT]") + "[Client thread/INFO]: [CHAT]".length();
            int end = line.indexOf("»");
            if (end != -1) {
                String getName = line.substring(start, end).trim();
                finalName = formatName(getName);
                return getFormattedName();
            }
        } else {
            int startIndex = line.indexOf("[Client thread/INFO]: [CHAT]") + "[Client thread/INFO]: [CHAT]".length();
            int minColonIndex = line.indexOf(":", startIndex);
            if (minColonIndex != -1) {
                String getName = line.substring(startIndex, minColonIndex).trim();
                finalName = formatName(getName);
                return getFormattedName();
            }
        }
        return "Unknown";
    }

    private String formatName(String line) {
        line = SQUARE_BRACKETS_PATTERN.matcher(line).replaceAll("");
        line = HASHTAG_PATTERN.matcher(line).replaceAll("");
        line = PIPE_PATTERN.matcher(line).replaceAll("");
        line = line.trim();
        return line;
    }

    private String getFormattedName() {
        finalName = finalName.replaceAll(".*?\\|", "").trim();
        finalName = finalName.replaceAll("(?i)Хаб", "").trim();
        if (finalName.length() <= 3) {
            return "Unknown";
        }
        return finalName;
    }
}
