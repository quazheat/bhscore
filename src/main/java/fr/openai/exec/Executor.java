package fr.openai.exec;

import fr.openai.database.Names;
import fr.openai.handler.filter.Swearing;
import fr.openai.handler.filter.Filtering;
import fr.openai.handler.filter.Validator;

public class Executor {
    private final Swearing swearing;
    private final Filtering filtering;

    public Executor() {
        this.swearing = new Swearing();
        this.filtering = new Filtering();
    }

    public void execute(String line, Names names) {
        if (Validator.isValid(line)) {
            return;
        }

        String playerName = names.getFinalName(line);
        String message = Messages.getMessage(line);

        // Создаем отдельные потоки для Swearing и Filtering
        Thread swearingThread = new Thread(() -> swearing.onFilter(playerName, message));
        Thread filteringThread = new Thread(() -> filtering.onFilter(playerName, message));

        // Запускаем потоки
        swearingThread.start();
        filteringThread.start();

        if (message != null && !message.isEmpty()) {
            System.out.println("DEBUG_" + playerName + " »  " + message);
        }
    }

    public void executedLog(String line) {
        System.out.println(line);
    }
}
