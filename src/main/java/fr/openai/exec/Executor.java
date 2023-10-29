package fr.openai.exec;

import fr.openai.database.files.ConnectDb;
import fr.openai.filter.fixer.Names;
import fr.openai.filter.Filtering;
import fr.openai.runtime.MessageProcessor;
import fr.openai.filter.Validator;
import fr.openai.notify.NotificationSystem;

import java.util.List;
import java.util.concurrent.*;

public class Executor {

    private final Filtering filtering;
    private final ScheduledExecutorService executorService;

    private final MessageProcessor messageProcessor;

    public Executor(NotificationSystem notificationSystem) {
        this.filtering = new Filtering(notificationSystem);
        this.messageProcessor = new MessageProcessor(notificationSystem);
        // Create a thread pool using Executors
        this.executorService = Executors.newScheduledThreadPool(10);
    }

    public void execute(String line, Names names, double similarityThreshold, List<String> whitelistWords) {
        if (Validator.isNotValid(line)) {
            return;
        }

        String playerName = names.getFinalName(line);

        String message = Messages.getMessage(line);

        // Use executorService for executing tasks in the thread pool
        executorService.submit(() -> {
            filtering.onFilter(playerName, message, similarityThreshold, whitelistWords);
            messageProcessor.processMessage(playerName, message);
        });
    }

    public void executedLog(String line) {
        System.out.println(line);
    }
}
