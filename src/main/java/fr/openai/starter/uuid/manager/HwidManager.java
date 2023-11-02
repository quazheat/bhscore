package fr.openai.starter.uuid.manager;

import fr.openai.starter.uuid.UuidProvider;

import java.util.UUID;

public class HwidManager {
    public static String getHwid(UuidProvider uuidProvider) {
        UUID systemUUID = uuidProvider.getUUID();

        return systemUUID != null ? systemUUID.toString().toLowerCase() : null;
    }
}
