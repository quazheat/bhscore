package fr.openai.ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ThreadLocalRandom;

public class y1 {
    private final JFrame a;
    private boolean b = false;

    public y1(JFrame a) {
        this.a = a;
    }

    public void a() {
        Timer t = new Timer(500, new ActionListener() {
            int numDots = 2;

            public void actionPerformed(ActionEvent e) {
                int option = JOptionPane.showOptionDialog(a, "Пытаемся отправить тикет" + ".".repeat(Math.max(0, numDots)),
                        "Ошибка", JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE, null, null, null);
                if (option == JOptionPane.CLOSED_OPTION || option == 0) {
                    b = true;
                    ((Timer) e.getSource()).stop();
                }
                numDots = (int) (ThreadLocalRandom.current().nextDouble() * 3) + 2;
            }
        });

        t.start();
    }

    public boolean isB() {
        return b;
    }
}
