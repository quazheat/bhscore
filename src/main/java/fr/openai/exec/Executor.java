package fr.openai.exec;

import fr.openai.filter.fixer.Names;
import fr.openai.filter.ChatMessage;
import fr.openai.filter.Filtering;
import fr.openai.runtime.MessageProcessor;
import fr.openai.filter.Validator;
import fr.openai.notify.NotificationSystem;
import fr.openai.reader.handler.LiveChatCleaner;
import fr.openai.reader.handler.LiveChatWriter;
import fr.openai.runtime.Times;

import java.util.List;
import java.util.concurrent.*;

public class Executor {

    private final Times times;
    private final Filtering filtering;
    private final NotificationSystem notificationSystem; // Добавьте поле для NotificationSystem
    private final ScheduledExecutorService executorService;

    private final MessageProcessor messageProcessor;

    public Executor(NotificationSystem notificationSystem) {
        this.notificationSystem = notificationSystem;
        this.filtering = new Filtering(notificationSystem);
        this.times = new Times();
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
        List<ChatMessage> chatMessages = times.getLiveChat();

        // Используем ранее созданный экземпляр liveChatAnalyzer для анализа чата
        // и вызываем метод loadChatFromJson() для выполнения проверки

        // Используем executorService для выполнения задач в пуле потоков
        executorService.submit(() -> {
            LiveChatWriter.writeLiveChat(chatMessages, "livechat.json");
            filtering.onFilter(playerName, message);
            times.timestamp(line, names);

            // Вызываем метод processMessage() объекта messageProcessor
            messageProcessor.processMessage(playerName, message);

            LiveChatCleaner.cleanLiveChat();
        });
    }



    public void executedLog(String line) {
        System.out.println(line);
    }
}
