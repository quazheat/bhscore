package fr.openai.handler.filter;
import fr.openai.notify.NotificationSystem;
import fr.openai.exec.Messages;

public class Filtering {
    private final NotificationSystem notificationSystem;

    public Filtering() {
        this.notificationSystem = new NotificationSystem();
    }

    public void onFilter(String name, String line) {
        // Проверяем, что ник игрока не равен "Unknown"
        if ("Unknown".equalsIgnoreCase(name)) {
            return; // Пропускаем сообщение
        }

        String message = Messages.getMessage(line);

        if (message != null) {
            message = SbFix.fixMessage(message); // Используем SbFix для обработки сообщения
            System.out.println("Filtering: " + name + " » " + message);

            boolean violationDetected = false; // Флаг для обнаружения нарушений

            if (hasManySymbols(message)) {
                System.out.println("DETECTED: 6 или более одинаковых символов в ряд");
                showNotification(name, "Symbol flood");
                violationDetected = true; // Устанавливаем флаг на true
            }

            if (!violationDetected && hasLaugh(message)) {
                System.out.println("DETECTED: Длинное слово с малым количеством уникальных букв");
                showNotification(name, "Laugh flood");
                violationDetected = true; // Устанавливаем флаг на true
            }

            if (!violationDetected && hasWFlood(message)) {
                System.out.println("DETECTED: 5 одинаковых слов");
                showNotification(name, "Flood");
                violationDetected = true; // Устанавливаем флаг на true
            }

            if (!violationDetected && hasCaps(message)) {
                System.out.println("DETECTED: Сообщение с более чем 55% букв в верхнем регистре");
                showNotification(name, "CAPS");
                violationDetected = true; // Устанавливаем флаг на true
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
                if (count >= 6) {
                    return true;
                }
            } else {
                prevChar = c;
                count = 1;
            }
        }

        return false;
    }

    private boolean hasLaugh(String message) {
        String[] words = message.split("\\s+");

        for (String word : words) {
            if (word.length() > 9) {
                int uniqueChars = (int) word.chars().distinct().count();
                if (uniqueChars <= 3) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean hasWFlood(String message) {
        String[] words = message.split("\\s+");
        for (String word : words) {
            int count = 0;
            for (String w : words) {
                if (word.equals(w)) {
                    count++;
                    if (count >= 5) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private boolean hasCaps(String message) {
        int totalChars = message.length();
        int upperCaseCount = 0;

        for (char c : message.toCharArray()) {
            if (Character.isUpperCase(c)) {
                upperCaseCount++;
            }
        }

        // Проверяем, что букв в верхнем регистре составляют более 55% и букв более 6
        return upperCaseCount > 6 && (double) upperCaseCount / totalChars > 0.55;
    }
}