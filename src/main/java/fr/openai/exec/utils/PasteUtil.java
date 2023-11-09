package fr.openai.exec.utils;
import fr.openai.database.ConfigManager;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

public abstract class PasteUtil {

    public void pasteFromClipboard() {
        final ConfigManager configManager = new ConfigManager();
        int period = configManager.getUpFQ(); // Set the period to the desired frequency

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
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }
}
