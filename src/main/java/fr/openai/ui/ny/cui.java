package fr.openai.ui.ny;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;

public class cui extends BasicButtonUI {

    private static final Color var1 = Color.darkGray;
    private static final Color var = Color.white;

    public cui() {
        super();
    }

    public void installUI(JComponent c) {
        super.installUI(c);
        AbstractButton button = (AbstractButton) c;
        button.setBackground(var1);
        button.setForeground(var);
        button.setFocusPainted(false);
    }

    public void uninstallUI(JComponent c) {
        super.uninstallUI(c);
        AbstractButton button = (AbstractButton) c;
        button.setBackground(null);
        button.setForeground(null);
    }

    public static void cios(AbstractButton button) {
        button.setUI(new cui());
    }
}
