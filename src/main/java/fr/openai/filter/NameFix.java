package fr.openai.filter;

public class NameFix {
    public static String sbFix(String playerName) {
        String[] words = playerName.split(" ");
        if (words.length >= 2) {
            if (words.length >= 3) {
                words[1] = words[2]; // Заменяем второе слово на третье
            } else {
                words[1] = ""; // Устанавливаем второе слово как пустую строку
            }
            playerName = String.join(" ", words).trim(); // Объединяем слова в одну строку
        }
        return playerName;
    }

}

