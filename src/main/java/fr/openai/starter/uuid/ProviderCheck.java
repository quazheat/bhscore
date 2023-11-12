package fr.openai.starter.uuid;

import fr.openai.database.menu.UserManager;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.UUID;

public class ProviderCheck {
    private final UuidProvider uuidProvider = new UuidProvider();
    private final UserManager userManager = new UserManager();

    public void validateUuid() throws IOException, URISyntaxException {
        List<String> allowedUuids = userManager.getUsersUuids();
        UUID systemUUID = uuidProvider.getUUID();

        if (systemUUID != null) {
            if (!allowedUuids.contains(systemUUID.toString())) {
                System.out.println("System UUID not found in the allowed UUIDs. Terminating.");
                System.exit(0);
            } else {
                System.out.println("System UUID is valid.");
            }
        } else {
            System.out.println("System UUID not found.");
        }
    }
}
