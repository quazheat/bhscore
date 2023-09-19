package fr.openai.exec;

public class Validator {
    public static boolean isValid(String line) {
        return line.contains("по причине:") ||
                line.contains("разбанил игрока") ||
                line.contains("размутил игрока");
    }
}
