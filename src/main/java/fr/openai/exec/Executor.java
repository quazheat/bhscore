package fr.openai.exec;

import fr.openai.discordfeatures.DiscordRPC;
import fr.openai.filter.fixer.Names;
import fr.openai.filter.Filtering;
import fr.openai.filter.MessageProcessor;
import fr.openai.filter.Validator;
import fr.openai.notify.NotificationSystem;

import java.util.concurrent.*;

public class Executor {

    private final Filtering filtering;
    private final DiscordRPC discordRPC;
    private final ScheduledExecutorService executorService;

    private final MessageProcessor messageProcessor;

    public Executor(NotificationSystem notificationSystem) {
        this.discordRPC = new DiscordRPC();
        this.filtering = new Filtering(notificationSystem);
        this.messageProcessor = new MessageProcessor(notificationSystem);
        this.executorService = Executors.newScheduledThreadPool(10);
    }

    public void execute(String line, Names names) {
        if (Validator.isNotValid(line)) {
            return;
        }

        String playerName = names.getFinalName(line);
        String message = Messages.getMessage(line);
        if (message == null) {
            return;
        }
        // Используем executorService для выполнения задач в пуле потоков
        executorService.submit(() -> {
            filtering.onFilter(playerName, message);
            messageProcessor.processMessage(playerName, message);
            executorService.scheduleAtFixedRate(discordRPC::updateRPC, 0, 1, TimeUnit.SECONDS);
        });
    }

}
