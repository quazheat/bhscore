package fr.openai.filter;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Validator {
    public static boolean isNotValid(String line) {
        String regex = " по причине:|разбанил игрока|кикнул игрока|\\. Причина:|получил награду с лутбокса" +
                "|\\n|\\[CHAT] \\(FAWE\\)|проводит ивент:|: \\[CHAT] \\[SC]|㰳|㨏|㰳 |㐎|㥐|: \\[CHAT] Очистка ┃" +
                "|: \\[CHAT] \\[Уборщик]|: \\[CHAT] i|INFO]: \\[CHAT] (H|M|Sr\\.M|Own|Owner|Урон|Ставки|ADM) " +
                "|: \\[CHAT] Предмет|: \\[CHAT] Теперь|: \\[CHAT] Баланс|: \\[CHAT] VK|: \\[CHAT] \\[I]" +
                "|размутил игрока|\\[CHAT] \\n|\\[CHAT] Всё|\\[CHAT] -----------|" +
                "[CHAT] \uE310 ЗБТ|ЗБТ чатов! Доступ только для персонала!";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(line);

        return matcher.find();
    }
}
