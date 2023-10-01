package fr.openai.database.customui;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;

public class CustomButtonUI extends BasicButtonUI {

    private static final Color BUTTON_BACKGROUND = Color.darkGray;
    private static final Color BUTTON_FOREGROUND = Color.white;

    public CustomButtonUI() {
        super();
    }

    @Override
    public void installUI(JComponent c) {
        super.installUI(c);
        AbstractButton button = (AbstractButton) c;
        button.setBackground(BUTTON_BACKGROUND);
        button.setForeground(BUTTON_FOREGROUND);
        button.setFocusPainted(false);
    }

    @Override
    public void uninstallUI(JComponent c) {
        super.uninstallUI(c);
        AbstractButton button = (AbstractButton) c;
        button.setBackground(null);
        button.setForeground(null);
    }

    public static void setCustomStyle(AbstractButton button) {
        button.setUI(new CustomButtonUI());
    }
}
