package fr.openai.filter.fixer;

import java.util.Scanner;
import java.util.regex.Pattern;

public class Names {
    private String finalName;
    private final Pattern SQUARE_BRACKETS_PATTERN = Pattern.compile("\\[.+?]");
    private final Pattern BRACKETS_PATTERN = Pattern.compile("\\(.+?\\)");
    private final Pattern HASHTAG_PATTERN = Pattern.compile("#.*");
    private final Pattern PIPE_PATTERN = Pattern.compile(".+?┃");

    public static String formatPlayerName(String playerName) {
        String[] words = playerName.split(" ");
        if (words.length >= 2) {
            if (words.length >= 3) {
                words[1] = words[2]; // Replace the second word with the third
            } else {
                words[0] = ""; // Set the second word to an empty string
            }
            playerName = String.join(" ", words).trim(); // Combine the words into one string
        }
        return playerName;
    }


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
        line = BRACKETS_PATTERN.matcher(line).replaceAll("");
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

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Names formatter = new Names();

        System.out.print("Enter a raw string: ");
        String rawString = scanner.nextLine();

        String playerName = formatter.getFinalName(rawString);
        System.out.println("Formatted Player Name: " + playerName);
    }
}
