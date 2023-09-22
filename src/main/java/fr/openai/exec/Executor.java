package fr.openai.exec;

import fr.openai.handler.filter.Filtering;

public class Executor {
    private final Filtering filtering;

    public Executor() {
        this.filtering = new Filtering();
    }

    public void execute(String line, Names names) {
        if (Validator.isValid(line)) {
            return;
        }

        String playerName = names.getFinalName(line);
        String message = Messages.getMessage(line);

        filtering.onFilter(playerName, message);

        if (message != null && !message.isEmpty()) {
            System.out.println("Player Message: " + message);
        }
    }

    public void executedLog(String line) {
        System.out.println(line);
    }
}
