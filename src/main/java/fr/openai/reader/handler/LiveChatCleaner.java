package fr.openai.reader.handler;

import fr.openai.filter.ChatMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class LiveChatCleaner {
    private static final String LIVE_CHAT_FILE_PATH = "livechat.json";
    private static final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
    public static void startCleaning() {
        executorService.scheduleAtFixedRate(LiveChatCleaner::cleanLiveChat, 0, 1, TimeUnit.SECONDS);
        System.out.println("LiveChatCleaner");
    }

    public static void cleanLiveChat() {
        List<ChatMessage> chatMessages = LiveChatWriter.readLiveChat(LIVE_CHAT_FILE_PATH);


        if (chatMessages != null) {
            long currentTimestamp = System.currentTimeMillis() / 1000;
            List<ChatMessage> cleanedChatMessages = new ArrayList<>();

            for (ChatMessage message : chatMessages) {
                long messageTimestamp = message.getTimestamp();

                if (messageTimestamp > 0 && currentTimestamp - messageTimestamp <= 60) {
                    cleanedChatMessages.add(message);
                }
            }

            LiveChatWriter.writeLiveChat(cleanedChatMessages, LIVE_CHAT_FILE_PATH);
        }
    }
}
