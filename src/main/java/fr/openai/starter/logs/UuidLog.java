package fr.openai.starter.logs;

import java.io.IOException;

public class UuidLog {
    public static void logUuid(boolean isAllowed) throws IOException {
        if (isAllowed) {
            System.out.println("Is UUID Allowed: true");
        } else {
            System.exit(1);
            System.out.println("Is UUID Allowed: false");
        }
    }
}
