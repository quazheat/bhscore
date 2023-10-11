package fr.openai.database.editor;

import fr.openai.database.customui.ForbiddenWordsPanel;
import fr.openai.database.customui.ModesPanel;
import fr.openai.database.customui.ReportsPanel;
import fr.openai.database.customui.WhitelistPanel;

import javax.swing.*;
import java.awt.*;

public class Editor {

    private final JFrame frame;
    private final JLabel outputLabel;

    public Editor() {
        frame = new JFrame("Word Editor");
        frame.setIconImage(new ImageIcon("tray_icon.png").getImage());
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 160);

        JTabbedPane tabbedPane = new JTabbedPane();

        // Добавляем новую вкладку "Режимы" в самое начало

        AddNewWord addNewWord = new AddNewWord(this);
        RemoveWord removeWord = new RemoveWord(this);
        ForbiddenWordsPanel forbiddenWordsPanel = new ForbiddenWordsPanel(addNewWord, removeWord);
        AddNewWhitelistWord addNewWhitelistWord = new AddNewWhitelistWord(this);
        RemoveWhitelistWord removeWhitelistWord = new RemoveWhitelistWord(this);
        WhitelistPanel whitelistPanel = new WhitelistPanel(addNewWhitelistWord, removeWhitelistWord);
        ReportsPanel reportsPanel = new ReportsPanel(frame); // Передаем frame в ReportsPanel

        ModesPanel modesPanel = new ModesPanel();

        //tabbedPane.addTab("Режимы", modesPanel);
        tabbedPane.addTab("Ругательства", forbiddenWordsPanel);
        tabbedPane.addTab("Белый список", whitelistPanel);
        tabbedPane.addTab("Отчеты", reportsPanel);

        frame.getContentPane().add(tabbedPane);

        outputLabel = new JLabel();
        outputLabel.setHorizontalAlignment(JLabel.CENTER);
        outputLabel.setForeground(Color.BLACK);
        frame.add(outputLabel, BorderLayout.SOUTH);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - frame.getWidth()) / 2;
        int y = (screenSize.height - frame.getHeight()) / 2;
        frame.setLocation(x, y);

        frame.setVisible(true);
    }

    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }

    public void setOutputText(String message) {
        outputLabel.setText(message);
    }
}
