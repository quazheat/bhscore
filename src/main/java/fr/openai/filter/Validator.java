package fr.openai.filter;

public class Validator {
    public static boolean notValid(String line) {
        return line.contains("по причине:") ||
                line.contains("разбанил игрока") ||
                line.contains("кикнул игрока") ||
                line.contains(". Причина: ") ||
                line.contains("получил награду с лутбокса") ||
                line.contains("\\n") ||
                line.contains("[CHAT] (FAWE)") ||
                line.contains("проводит ивент:") ||
                line.contains(": [CHAT] [SC]") ||
                line.contains("㰳") || /*Это анмут!!!*/
                line.contains("㨏") ||
                line.contains("㰳 ") || /*Это чужой;мут*/
                line.contains("㐎") || /*Это ;жалоба*/
                line.contains("㥐") || /*Это выдача варна*/
                line.contains(": [CHAT] Очистка ┃ ") ||
                line.contains(": [CHAT] [Уборщик] ") ||
                line.contains(": [CHAT] i ") ||
                line.contains("INFO]: [CHAT] H |") ||
                line.contains("INFO]: [CHAT] M |") ||
                line.contains("INFO]: [CHAT] Sr.M |") ||
                line.contains("INFO]: [CHAT] Own |") ||
                line.contains("INFO]: [CHAT] Owner |") ||
                line.contains("INFO]: [CHAT] Урон") ||
                line.contains("INFO]: [CHAT] Ставки") ||
                line.contains("INFO]: [CHAT]    ") ||
                line.contains("INFO]: [CHAT] ADM |") ||
                line.contains(": [CHAT] Предмет") ||
                line.contains(": [CHAT] Теперь") ||
                line.contains(": [CHAT] Баланс") ||
                line.contains(": [CHAT] VK") ||
                line.contains(": [CHAT] [I]") ||
                line.contains("размутил игрока");
    }
}
