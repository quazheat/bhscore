package fr.openai.handler.filter;
import fr.openai.handler.filter.fixer.SbFix;
import fr.openai.notify.NotificationSystem;
import fr.openai.exec.Messages;

public class Filtering {
    private final NotificationSystem notificationSystem;

    public Filtering() {
        this.notificationSystem = new NotificationSystem();
    }

    public void onFilter(String name, String line) {
        // Проверяем, что ник игрока не равен "Unknown"
        if ("Unknown".equalsIgnoreCase(name)) return; // Пропускаем сообщение

        String message = Messages.getMessage(line);

        if (message != null) {
            message = SbFix.fixMessage(message); // Используем SbFix для обработки сообщения
            /*System.out.println("Filtering: " + name + " » " + message);*/

            boolean violationDetected = false; // Флаг для обнаружения нарушений

            if (hasManySymbols(message)) {
                showNotification(name, "Symbol flood");
                violationDetected = true; // Устанавливаем флаг на true
            }

            if (!violationDetected && hasLaugh(message)) {
                showNotification(name, "Laugh flood");
                violationDetected = true; // Устанавливаем флаг на true
            }

            if (!violationDetected && hasWFlood(message)) {
                System.out.println("DETECTED: 5 одинаковых слов");
                showNotification(name, "Flood");
                violationDetected = true; // Устанавливаем флаг на true
            }

            if (!violationDetected && hasCaps(message)) {
                showNotification(name, "CAPS");
            }
        } else {
            System.out.println("Filtering: No message received");
        }
    }

    private void showNotification(String playerName, String violation) {
        notificationSystem.showNotification(playerName, violation);
    }
    private boolean hasManySymbols(String message) {
        char[] chars = message.toCharArray();
        char prevChar = '\0';
        int count = 0;

        for (char c : chars) {
            if (c == prevChar) {
                count++;
                if (count >= 6) return true;
            } else {
                prevChar = c;
                count = 1;
            }
        } return false;
    }

    private boolean hasLaugh(String message) {
        String[] words = message.split("\\s+");

        for (String word : words) {
            if (word.length() > 9) {
                int uniqueChars = (int) word.chars().distinct().count();
                if (uniqueChars <= 3) return true;
            }
        } return false;
    }

    private boolean hasWFlood(String message) {
        String[] words = message.split("\\s+");
        for (String word : words) {
            int count = 0;
            for (String w : words) {
                if (word.equals(w)) {
                    count++;
                    if (count >= 5) return true;
                }
            }
        } return false;
    }
    private String removeSpecialCharacters(String input) {
        // Оставляем только латиницу (a-zA-Z) и кириллицу (а-яА-Я)
        return input.replaceAll("[^a-zA-Zа-яА-Я]", "");
    }


    private boolean hasCaps(String message) {
        int upperCaseCount = 0;
        String cleanedMessage = removeSpecialCharacters(message);

        for (char c : cleanedMessage.toCharArray()) {
            if (Character.isUpperCase(c)) {
                upperCaseCount++;
            }
        }

        // Проверяем, что букв в верхнем регистре составляют более 55% и букв более 6
        return upperCaseCount > 5 && (double) upperCaseCount / cleanedMessage.length() > 0.55;
    }

}
