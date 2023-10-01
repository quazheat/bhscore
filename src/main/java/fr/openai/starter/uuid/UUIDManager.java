package fr.openai.starter.uuid;


import java.util.UUID;

public class UUIDManager {
    public static UUID getSystemUUID() {
        return UuidProvider.getUUID();
    }
}
