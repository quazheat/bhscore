package fr.openai.starter.uuid.manager;

import fr.openai.starter.uuid.UuidProvider;

import java.util.UUID;

public class UUIDManager {
    private final UuidProvider uuidProvider = new UuidProvider();
    public UUID getSystemUUID() {
        return uuidProvider.getUUID();
    }
}
