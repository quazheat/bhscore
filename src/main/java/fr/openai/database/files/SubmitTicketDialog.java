package fr.openai.database.files;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SubmitTicketDialog {
    private final JFrame parentFrame;
    private boolean canceled = false;

    public SubmitTicketDialog(JFrame parentFrame) {
        this.parentFrame = parentFrame;
    }

    public void showDialog() {
        Timer timer = new Timer(1000, new ActionListener() {
            int numDots = 2;

            @Override
            public void actionPerformed(ActionEvent e) {
                int option = JOptionPane.showOptionDialog(parentFrame, "Пытаемся отправить тикет" + ".".repeat(Math.max(0, numDots)),
                        "Ошибка", JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE, null, null, null);
                if (option == JOptionPane.CLOSED_OPTION || option == 0) {
                    canceled = true;
                    ((Timer) e.getSource()).stop(); // Stop the timer when canceled
                }
                numDots = (int) (Math.random() * 3) + 2; // Randomly change the number of dots
            }
        });

        timer.start();
    }

    public boolean isCanceled() {
        return canceled;
    }
}
