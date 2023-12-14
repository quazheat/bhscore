package fr.openai.ui.ny;

import javax.swing.*;
import java.awt.*;

public class cvf extends JTextField {
    public cvf(String a) {
        super(a);
        a();
    }

    private void a() {
        setEditable(false);
        setForeground(Color.BLACK);
        setFont(new Font("Serif", Font.PLAIN, 16));
        setBackground(Color.GRAY);
        setHighlighter(null);
        setTransferHandler(null);
        setEditable(false);
        setFocusable(false);
    }
}