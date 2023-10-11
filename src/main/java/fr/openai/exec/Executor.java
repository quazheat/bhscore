package fr.openai.exec;

import fr.openai.filter.fixer.Names;
import fr.openai.filter.ChatMessage;
import fr.openai.filter.Filtering;
import fr.openai.runtime.MessageProcessor;
import fr.openai.filter.Validator;
import fr.openai.notify.NotificationSystem;

import java.util.List;
import java.util.concurrent.*;

public class Executor {

    private final Filtering filtering;
    private final NotificationSystem notificationSystem; // Добавьте поле для NotificationSystem
    private final ScheduledExecutorService executorService;

    private final MessageProcessor messageProcessor;

    public Executor(NotificationSystem notificationSystem) {
        this.notificationSystem = notificationSystem;
        this.filtering = new Filtering(notificationSystem);
        this.messageProcessor = new MessageProcessor(notificationSystem);
        // Создаем пул потоков с помощью Executors
        this.executorService = Executors.newScheduledThreadPool(10); //
    }

    public void execute(String line, Names names) {
        if (Validator.notValid(line)) {
            return;
        }

        String playerName = names.getFinalName(line);

        String message = Messages.getMessage(line);

        // Используем ранее созданный экземпляр liveChatAnalyzer для анализа чата
        // и вызываем метод loadChatFromJson() для выполнения проверки

        // Используем executorService для выполнения задач в пуле потоков
        executorService.submit(() -> {
            filtering.onFilter(playerName, message);
            messageProcessor.processMessage(playerName, message);
        });
    }



    public void executedLog(String line) {
        System.out.println(line);
    }
}
