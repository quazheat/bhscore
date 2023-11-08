package fr.openai.starter.logs;


import java.io.IOException;

public class UuidLog {
    public void logUuid(boolean isAllowed) throws IOException {
        if (isAllowed) {
            System.out.println("Is UUID Allowed: true");
        } else {
            System.out.println("Is UUID Allowed: false");
        }
    }
}
