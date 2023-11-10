package fr.openai.filter;

public class BehaviorLine {
    private final BehaviorScoresDatabase behaviorScoresDatabase;

    public BehaviorLine(BehaviorScoresDatabase behaviorScoresDatabase) {
        this.behaviorScoresDatabase = behaviorScoresDatabase;
    }

    public String muteFinder(String message) {
        String targetPhrase = "замутил игрока";
        int muteIndex = message.indexOf(targetPhrase);

        if (muteIndex != -1) {
            String substring = message.substring(muteIndex + targetPhrase.length());
            int pipeIndex = substring.indexOf("┃");

            String[] violatorName = (pipeIndex != -1)
                    ? substring.substring(pipeIndex + 1).trim().split("\\s+")
                    : substring.trim().split("\\s+");

            if (violatorName.length > 0) {
                processOffender(violatorName[0]);
                return violatorName[0];
            }
        }

        return null;
    }

    public String warnFinder(String message) {
        String targetPhrase = "предупредил игрока";
        int warnIndex = message.indexOf(targetPhrase);

        if (warnIndex != -1) {
            String substring = message.substring(warnIndex + targetPhrase.length());
            int pipeIndex = substring.indexOf("┃");

            String[] violatorName = (pipeIndex != -1)
                    ? substring.substring(pipeIndex + 1).trim().split("\\s+")
                    : substring.trim().split("\\s+");

            if (violatorName.length > 0) {
                processOffender(violatorName[0]);
                return violatorName[0];
            }
        }

        return null;
    }

    private void processOffender(String offenderName) {
        // Проверяем, существует ли нарушитель в базе данных
        if (behaviorScoresDatabase.getScore(offenderName) > 0) {
            // Если нарушитель существует, уменьшаем его счет порядочности на 150
            behaviorScoresDatabase.addOrUpdateScore(offenderName);
            System.out.println("Счет порядочности нарушителя " + offenderName + " уменьшен.");
        } else {
            // Если нарушителя нет в базе данных, добавляем его со стандартным счетом и уменьшаем на 150
            behaviorScoresDatabase.addOrUpdateScore(offenderName);
            System.out.println("Новый нарушитель " + offenderName + " добавлен со счетом порядочности 9850.");
        }
    }
}
