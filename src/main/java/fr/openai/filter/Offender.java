package fr.openai.filter;

public class Offender {
    private final String name;
    private int behaviorScore;

    public Offender(String name) {
        this.name = name;
        this.behaviorScore = 10000; // Стандартный счет порядочности
    }

    public String getName() {
        return name;
    }

    public int getBehaviorScore() {
        return behaviorScore;
    }

    public void decreaseBehaviorScore(int points) {
        behaviorScore -= points;
        // счет порядочности не станет меньше 0
        behaviorScore = Math.max(behaviorScore, 0);
    }

    public void increaseBehaviorScore(int points) {
        behaviorScore += points;
    }
}
