package fr.openai.starter.uuid;

import fr.openai.database.menu.SendTicket;
import fr.openai.starter.logs.UuidLog;
import fr.openai.starter.uuid.manager.HwidManager;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class UuidChecker {
    private final UuidProvider uuidProvider = new UuidProvider();
    private final ProviderCheck providerCheck = new ProviderCheck();

    public boolean isAllowed() {
        String hwid = HwidManager.getHwid(uuidProvider);
        return hwid != null && isUuidAllowed(hwid);
    }

    private boolean isUuidAllowed(String uuid) {
        try {
            List<String> allowedUuids = providerCheck.getUuuidList();

            boolean isAllowed = allowedUuids.contains(uuid);
            UuidLog.logUuid(isAllowed);

            return isAllowed;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

}
