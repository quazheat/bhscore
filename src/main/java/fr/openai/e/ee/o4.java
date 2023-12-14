package fr.openai.e.ee;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

public class o4 {
    public static void a4(String ma) {
        StringSelection se = new StringSelection(ma);
        Clipboard cc = Toolkit.getDefaultToolkit().getSystemClipboard();
        cc.setContents(se, null);
    }
}
