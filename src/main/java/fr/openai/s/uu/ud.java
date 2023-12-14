package fr.openai.s.uu;

import com.sun.jna.platform.win32.Advapi32Util;
import com.sun.jna.platform.win32.WinReg;

import java.util.UUID;

public class ud {
    public UUID a() {
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

        return null;
    }
}
