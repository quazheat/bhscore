package fr.openai.s.uu;

import fr.openai.b.menu.uu;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.UUID;

public class ex {
    private final ud ud = new ud();
    private final uu uu = new uu();

    public void validateUuid() throws IOException, URISyntaxException {
        List<String> allowedUuids = uu.gG();
        UUID systemUUID = ud.a();

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
