package fr.openai.filter;

public class NameFix {
    public String cscFix(String playerName) {
        String[] words = playerName.split(" ");

        if (words.length >= 2) {
            words[1] = (words.length >= 3) ? words[2] : ""; // Replacing the second word with the third or setting it to an empty string
            playerName = String.join(" ", words).trim(); // Concatenating the words into a single string
        }

        return playerName;

    }
}
