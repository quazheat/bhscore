package fr.openai.filter;

public class NameFix {
    public static String sbFix(String playerName) {
        String[] words = playerName.split(" ");
        if (words.length >= 2) {
            words[1] = ""; // Устанавливаем второе слово как пустую строку
            playerName = String.join(" ", words).trim(); // Объединяем слова в одну строку
        }
        return playerName;
    }
}

