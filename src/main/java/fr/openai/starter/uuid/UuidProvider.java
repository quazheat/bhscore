package fr.openai.starter.uuid;

import com.sun.jna.platform.win32.Advapi32Util;
import com.sun.jna.platform.win32.WinReg;

import java.util.UUID;

public class UuidProvider {
    public UUID getUUID() {
        String rawUuid = null;
        try {
            rawUuid = Advapi32Util.registryGetStringValue(
                    WinReg.HKEY_LOCAL_MACHINE,
                    "SOFTWARE\\Microsoft\\Cryptography",
                    "MachineGuid"
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (rawUuid != null) {
            // Преобразование к UUID (опционально)
            return UUID.fromString(rawUuid);
        }

        // Если не удалось получить UUID из реестра, возвращаем null
        return null;
    }
}
