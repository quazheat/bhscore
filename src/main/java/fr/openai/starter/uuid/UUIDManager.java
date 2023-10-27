package fr.openai.starter.uuid;

import java.util.UUID;

public class UUIDManager {
    private final UuidProvider uuidProvider = new UuidProvider();
    public UUID getSystemUUID() {
        return uuidProvider.getUUID();
    }
}
