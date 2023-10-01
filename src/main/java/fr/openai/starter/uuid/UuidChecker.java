package fr.openai.starter.uuid;

import fr.openai.starter.logs.UuidLog;

import java.io.IOException;
import java.util.List;

public class UuidChecker {
    private final CheckProvider providerIsOk = new CheckProvider();

    public boolean isAllowed() {
        String hwid = HwidManager.getHwid();
        return hwid != null && isAllowed(hwid);
    }

    private boolean isAllowed(String uuid) {
        try {
            List<String> allowedUuids = providerIsOk.getUuuidList();

            for (String singleUuid : allowedUuids) {
                if (uuid.equals(singleUuid)) {
                    UuidLog.logUuid(true);
                    return true;
                }
            }

            UuidLog.logUuid(false);
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
