package fr.openai.handler.filter;

public class Validator {
    public static boolean isValid(String line) {
        return line.contains("по причине:") ||
                line.contains("разбанил игрока") ||
                line.contains("кикнул игрока") ||
                line.contains(". Причина: ") ||
                line.contains("получил награду с лутбокса") ||
                line.contains("\\n") ||
                line.contains("проводит ивент:") ||
                line.contains("[CHAT] [SC]") ||
                line.contains("㰳") || /*Это анмут!!!*/
                line.contains("㨏") ||
                line.contains("㰳 ") || /*Это чужой;мут*/
                line.contains("㐎") || /*Это ;жалоба*/
                line.contains("㥐") || /*Это выдача варна*/
                line.contains("[CHAT] Очистка ┃ ") ||
                line.contains("размутил игрока");
    }
}
