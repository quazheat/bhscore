package fr.openai.starter.uuid;

import java.util.UUID;

public class HwidManager {
    public static String getHwid() {
        UUID systemUUID = UuidProvider.getUUID();
        return systemUUID != null ? systemUUID.toString().toLowerCase() : null;
    }
}
