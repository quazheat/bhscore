package fr.openai.exec;

import java.util.regex.Pattern;

public class Names {
    private String finalName; // Поле для хранения отформатированного имени

    public String getFinalName(String line) {

        if (line.contains("»")) {
            int start = line.indexOf("[Client thread/INFO]: [CHAT]") + "[Client thread/INFO]: [CHAT]".length();
            int end = line.indexOf("»");

            if (end != -1) {
                String getName = line.substring(start, end).trim();
                // System.out.println(getName); // del

                // Вызываем метод finalName и сохраняем результат в поле finalName
                finalName = finalName(getName);
                System.out.println("Debug NAMES: " + finalName); // Выводим finalName для отладки
                return finalName; // Возвращаем finalName
            }
        } else {
            int startIndex = line.indexOf("[Client thread/INFO]: [CHAT]") + "[Client thread/INFO]: [CHAT]".length();
            int minColonIndex = line.indexOf(":", startIndex);

            if (minColonIndex != -1) {
                String getName = line.substring(startIndex, minColonIndex).trim();
                // System.out.println(getName); // del

                // Вызываем метод finalName и сохраняем результат в поле finalName
                finalName = finalName(getName);
                return finalName; // Возвращаем finalName
            }
        }

        // Если не найдено имя игрока, возвращаем "Unknown" (или что вам нужно)
        return "Unknown";
    }


    private static final Pattern SQUARE_BRACKETS_PATTERN = Pattern.compile("\\[.+?\\]");
    private static final Pattern HASHTAG_PATTERN = Pattern.compile("#.*");
    private static final Pattern PIPE_PATTERN = Pattern.compile(".+?┃");

    private String formatName(String line) {
        // Убираем символы "[" и "]", а также всё между ними
        line = SQUARE_BRACKETS_PATTERN.matcher(line).replaceAll("");

        // Убираем символы "#" и всё после них
        line = HASHTAG_PATTERN.matcher(line).replaceAll("");

        // Убираем символы "┃" и всё до них
        line = PIPE_PATTERN.matcher(line).replaceAll("");

        // Удаляем лишние пробелы в начале и конце строки
        line = line.trim();

        return line;
    }

    private String finalName(String nickname) {
        // Форматируем имя
        String finalName = formatName(nickname);

        // Проверяем длину форматированного имени и присваиваем "Unknown", если меньше трех символов
        if (finalName.length() <= 3) {
            finalName = "Unknown";
        }

        // Выводим результат
        //System.out.println("Formatted Name: " + finalName);
        return finalName;
    }
}
