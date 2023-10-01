package fr.openai.starter.uuid;

import fr.openai.starter.CheckProvider;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class UuidChecker {
    private final CheckProvider providerIsOk = new CheckProvider();

    public boolean isAllowed() {
        UuidProvider uuidProvider = new UuidProvider();
        UUID systemUUID = uuidProvider.getUUID();
        String hwid = systemUUID.toString().toLowerCase();
        return isAllowed(hwid);
    }

    private boolean isAllowed(String uuid) {
        try {
            List<String> allowedUuids = providerIsOk.getUuuidList();

            System.out.println("Checking UUID: " + uuid);

            // Разбиваем строку с разделенными запятыми UUID
            String[] uuidArray = uuid.split(",");

            for (String singleUuid : uuidArray) {
                // Удалите пробелы из начала и конца сравниваемого UUID и переведите в нижний регистр
                singleUuid = singleUuid.trim().toLowerCase();

                for (String allowedUuid : allowedUuids) {
                    // Удалите пробелы из начала и конца разрешенного UUID и переведите в нижний регистр
                    allowedUuid = allowedUuid.trim().toLowerCase();

                    if (singleUuid.equals(allowedUuid)) {
                        System.out.println("Is UUID Allowed: true");
                        return true;
                    }
                }
            }

            System.out.println("Is UUID Allowed: false");
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}