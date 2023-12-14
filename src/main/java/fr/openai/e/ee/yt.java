package fr.openai.e.ee;
import fr.openai.b.dzz;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

public abstract class yt {

    public void eu() {
        final dzz dzz = new dzz();
        int period = dzz.bq(); // Set the period to the desired frequency

        try {
            Robot robot = new Robot();

            // Simulate pressing the "T" key
            robot.keyPress(KeyEvent.VK_T);
            robot.keyRelease(KeyEvent.VK_T);
            robot.delay(period + 20); // Delay after releasing T
            System.out.println(period);

            // Simulate pressing Ctrl key
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.delay(period + 20); // Delay after pressing Ctrl

            // Simulate pressing 'A' key to select all text
            robot.keyPress(KeyEvent.VK_A);
            robot.keyRelease(KeyEvent.VK_A);
            robot.delay(period + 20); // Delay after releasing A

            // Simulate pressing 'V' key to paste from clipboard
            robot.keyPress(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_V);
            robot.delay(period + 20); // Delay after releasing V

            // Release Ctrl key
            robot.keyRelease(KeyEvent.VK_CONTROL);

            // Simulate pressing the Win key and then Print Screen
            if (ak) {
                robot.keyPress(KeyEvent.VK_WINDOWS);
                robot.delay(period + 20); //
                robot.keyPress(KeyEvent.VK_PRINTSCREEN);
                robot.delay(period + 20);
                robot.keyRelease(KeyEvent.VK_PRINTSCREEN);
                robot.keyRelease(KeyEvent.VK_WINDOWS);
            }
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }
    public static boolean ak = true;
    public static void setE(boolean eba) {
        ak = eba;
    }
}
