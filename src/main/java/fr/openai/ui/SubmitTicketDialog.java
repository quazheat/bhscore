package fr.openai.ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ThreadLocalRandom;

public class SubmitTicketDialog {
    private final JFrame parentFrame;
    private boolean canceled = false;

    public SubmitTicketDialog(JFrame parentFrame) {
        this.parentFrame = parentFrame;
    }

    public void showDialog() {
        Timer timer = new Timer(500, new ActionListener() {
            int numDots = 2;

            public void actionPerformed(ActionEvent e) {
                int option = JOptionPane.showOptionDialog(parentFrame, "Пытаемся отправить тикет" + ".".repeat(Math.max(0, numDots)),
                        "Ошибка", JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE, null, null, null);
                if (option == JOptionPane.CLOSED_OPTION || option == 0) {
                    canceled = true;
                    ((Timer) e.getSource()).stop(); // Stop the timer when canceled
                }
                numDots = (int) (ThreadLocalRandom.current().nextDouble() * 3) + 2;
            }
        });

        timer.start();
    }

    public boolean isCanceled() {
        return canceled;
    }
}
