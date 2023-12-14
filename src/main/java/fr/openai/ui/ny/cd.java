package fr.openai.ui.ny;

import javax.swing.*;
import java.awt.*;

public class cd extends JDialog {
    public cd() {
        cd1();
    }

    private void cd1() {
        setSize(300, 100);
        setLayout(new BorderLayout());
        setUndecorated(true);
        setAlwaysOnTop(true);
        setFocusableWindowState(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
