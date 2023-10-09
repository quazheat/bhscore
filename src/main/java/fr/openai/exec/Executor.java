package fr.openai.exec;

import fr.openai.database.Names;
import fr.openai.database.files.ConnectDb;
import fr.openai.filter.ChatMessage;
import fr.openai.filter.Filtering;
import fr.openai.filter.Validator;
import fr.openai.notify.NotificationSystem;
import fr.openai.filter.LiveChatAnalyzer;
import fr.openai.reader.handler.LiveChatCleaner;
import fr.openai.reader.handler.LiveChatWriter;
import fr.openai.runtime.Times;
import fr.openai.starter.logs.ConsoleLogger;

import java.util.List;
import java.util.concurrent.*;

public class Executor {
    private final Times times;
    private final Filtering filtering;
    private final NotificationSystem notificationSystem; // Добавьте поле для NotificationSystem
    private final ScheduledExecutorService executorService;

    public Executor(NotificationSystem notificationSystem) {
        this.notificationSystem = notificationSystem;
        // Список live_chat
        this.filtering = new Filtering(notificationSystem);
        this.times = new Times();
        // Создаем пул потоков с помощью Executors
        this.executorService = Executors.newScheduledThreadPool(10); //
    }
    public void periodic() {
        LiveChatAnalyzer liveChatAnalyzer = new LiveChatAnalyzer(notificationSystem);

        executorService.submit(() -> liveChatAnalyzer.startFindingFlood("livechat.json"));

    }

    public void execute(String line, Names names) {
        if (Validator.notValid(line)) {
            return;
        }

        String playerName = names.getFinalName(line);
        String message = Messages.getMessage(line);
        List<ChatMessage> chatMessages = times.getLiveChat();


        // Используем executorService для выполнения задач в пуле потоков
        executorService.submit(() -> {
            ConsoleLogger.consoleLog();

            filtering.onFilter(playerName, message);
            times.timestamp(line, names);
            LiveChatWriter.writeLiveChat(chatMessages, "livechat.json");
            LiveChatCleaner.cleanLiveChat();
        });


    }


    public void executedLog(String line) {
        System.out.println(line);
    }
}
