package fr.openai.handler.filter;

import fr.openai.exec.Messages;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Filtering {
    private static final Pattern forbiddenPattern = Pattern.compile("\\b(запрещенное слово1|матное слово|fuck|хуй)\\b", Pattern.CASE_INSENSITIVE);

    public void onFilter(String name, String line) {
        String message = Messages.getMessage(line);

        if (message != null) {
            System.out.println("Filtering: Message received: " + message);
            if (containsForbiddenWords(message)) {
                System.out.println("Filtering: Forbidden words detected in the message");

                // Создаем сообщение о нарушении
                String violationMessage = name + " нарушил что-то";

                // Выводим сообщение о нарушении
                System.out.println("Filtering: " + violationMessage);

                // Здесь можно выполнить действия в случае обнаружения, например, предупреждение
            }
        } else {
            System.out.println("Filtering: No message received");
        }
    }

    private boolean containsForbiddenWords(String message) {
        Matcher matcher = forbiddenPattern.matcher(message);
        return matcher.find();
    }
}
