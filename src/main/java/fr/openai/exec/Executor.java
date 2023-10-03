package fr.openai.exec;

import fr.openai.database.Names;
import fr.openai.handler.filter.SwearingFilter;
import fr.openai.handler.filter.Filtering;
import fr.openai.handler.filter.Validator;
import fr.openai.notify.NotificationSystem;

public class Executor {
    private final SwearingFilter swearingFilter;
    private final Filtering filtering;
    private final NotificationSystem notificationSystem;

    public Executor(NotificationSystem notificationSystem) {
        this.notificationSystem = notificationSystem;
        this.swearingFilter = new SwearingFilter();
        this.filtering = new Filtering(notificationSystem);
    }

    public void execute(String line, Names names) {
        if (Validator.isValid(line)) {
            return;
        }

        String playerName = names.getFinalName(line);
        String message = Messages.getMessage(line);

        // Создаем отдельные потоки для Swearing и Filtering
        Thread filteringThread = new Thread(() -> filtering.onFilter(playerName, message));

        // Запускаем потоки
        filteringThread.start();

//        if (message != null && !message.isEmpty()) {
//            System.out.println("DEBUG_" + playerName + " »  " + message);
//        }
    }

    public void executedLog(String line) {
        System.out.println(line);
    }
}
