package fr.openai.handler.filter;

public class Validator {
    public static boolean isValid(String line) {
        return line.contains("по причине:") ||
                line.contains("разбанил игрока") ||
                line.contains("получил награду с лутбокса") ||
                line.contains("\\n") ||
                line.contains("проводит ивент:") ||
                line.contains("㨏") ||
                line.contains("размутил игрока");
    }
}
