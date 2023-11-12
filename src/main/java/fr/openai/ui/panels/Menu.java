package fr.openai.ui.panels;

import fr.openai.database.b;
import fr.openai.database.menu.AddNewWhitelistWord;
import fr.openai.database.menu.AddNewWord;
import fr.openai.database.menu.RemoveWhitelistWord;
import fr.openai.database.menu.RemoveWord;
import fr.openai.starter.VersionChecker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Menu {
    private final JFrame frame;
    private final JLabel outputLabel;

    private final b b = new b();

    public Menu(TrayIcon trayIcon) {
        VersionChecker versionChecker = new VersionChecker();
        String version = versionChecker.getCurrentVersion();

        frame = new JFrame("BHScore " + version);
        frame.setIconImage(trayIcon.getImage());
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 160);

        JTabbedPane tabbedPane = new JTabbedPane();

        AddNewWord addNewWord = new AddNewWord(this);
        RemoveWord removeWord = new RemoveWord(this);
        ForbiddenWordsPanel forbiddenWordsPanel = new ForbiddenWordsPanel(addNewWord, removeWord);
        AddNewWhitelistWord addNewWhitelistWord = new AddNewWhitelistWord(this);
        RemoveWhitelistWord removeWhitelistWord = new RemoveWhitelistWord(this);
        WhitelistPanel whitelistPanel = new WhitelistPanel(addNewWhitelistWord, removeWhitelistWord);
        ReportsPanel reportsPanel = new ReportsPanel(frame);
        ModesPanel modesPanel = new ModesPanel();
        PlayPanel playPanel = new PlayPanel();

        tabbedPane.addTab("Main", modesPanel);
        tabbedPane.addTab("Words", forbiddenWordsPanel);
        tabbedPane.addTab("Whitelist", whitelistPanel);
        tabbedPane.addTab("Reports", reportsPanel);
        tabbedPane.addTab("Playground",playPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                b.dqzxc();
                System.out.println("ConnectionClosed");
            }
        });

        frame.getContentPane().add(tabbedPane);
        outputLabel = new JLabel();
        outputLabel.setHorizontalAlignment(JLabel.CENTER);
        outputLabel.setForeground(Color.BLACK);
        frame.add(outputLabel, BorderLayout.SOUTH);
        tabbedPane.addChangeListener(e -> {
            int selectedIndex = tabbedPane.getSelectedIndex();
            if (selectedIndex == 0 || selectedIndex == 3) {
                clearOutputText();
            }
        });

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

    public void clearOutputText() {
        outputLabel.setText("");
    }


}
