package fr.openai.s.uu;

import com.sun.jna.platform.win32.Advapi32Util;
import com.sun.jna.platform.win32.WinReg;

import java.util.UUID;

public class ud {
    public UUID a() {
        String a1 = null;
        try {
            a1 = Advapi32Util.registryGetStringValue(
                    WinReg.HKEY_LOCAL_MACHINE,
                    "SOFTWARE\\Microsoft\\Cryptography",
                    "MachineGuid"
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (a1 != null) {
            return UUID.fromString(a1);
        }

        return null;
    }
}
