package fr.openai.filter;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Validator {
    public static boolean isNotValid(String line) {
        String regex = "\\[CHAT]\\s{2,}" +
                "|Ошибка OpenGL:" +
                "|\\[CHAT] Ставки выиграли:" +
                "|\\[CHAT] Вы " +
                "|\\[CHAT] Игрок" +
                "|получил с лутбокса:" +
                "|разбанил игрока" +
                "|кикнул игрока" +
                "|получил награду с лутбокса" +
                "|\\n|\\[CHAT] \\(FAWE\\)" +
                "|проводит ивент:" +
                "|: \\[CHAT] \\[SC]" +
                "|㨏|㐎|㥐" +
                "|: \\[CHAT] Очистка ┃" +
                "|: \\[CHAT] \\[Уборщик]" +
                "|: \\[CHAT] i" +
                "|: \\[CHAT] Предмет" +
                "|: \\[CHAT] Теперь" +
                "|: \\[CHAT] Баланс" +
                "|: \\[CHAT] VK" +
                "|: \\[CHAT] \\[I]" +
                "|размутил игрока" +
                "|\\[CHAT] Всё" +
                "|\\[CHAT] -----------" +
                "|\\[CHAT] \uE310 ЗБТ" +
                "|ЗБТ чатов! Доступ только для персонала!" +
                "|\\[CHAT] Урон " +
                "| \\[CHAT] \\\\n ";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(line);

        return matcher.find();
    }
}
