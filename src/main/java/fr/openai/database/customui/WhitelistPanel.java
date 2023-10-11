package fr.openai.database.customui;

import fr.openai.database.customui.CustomButtonUI;
import fr.openai.database.customui.CustomTab;
import fr.openai.database.editor.AddNewWhitelistWord;
import fr.openai.database.editor.RemoveWhitelistWord;

import javax.swing.*;
import java.awt.*;

public class WhitelistPanel extends JPanel {
    private final AddNewWhitelistWord addNewWhitelistWord;
    private final RemoveWhitelistWord removeWhitelistWord;
    private final JTextField inputTextField;
    private final JLabel outputLabel;

    public WhitelistPanel(AddNewWhitelistWord addNewWhitelistWord, RemoveWhitelistWord removeWhitelistWord) {
        this.addNewWhitelistWord = addNewWhitelistWord;
        this.removeWhitelistWord = removeWhitelistWord;

        setLayout(new BorderLayout());
        setBackground(Color.lightGray);

        JPanel inputButtonPanel = new JPanel();
        inputButtonPanel.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel inputLabel = new JLabel("Введите слово:");
        inputLabel.setForeground(Color.black);
        inputPanel.setBackground(Color.LIGHT_GRAY);

        inputTextField = new JTextField(20);
        inputPanel.add(inputLabel);
        inputPanel.add(inputTextField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        JButton addButton = new JButton("Добавить");
        JButton removeButton = new JButton("Удалить");

        CustomButtonUI.setCustomStyle(addButton);
        CustomButtonUI.setCustomStyle(removeButton);

        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.setBackground(Color.LIGHT_GRAY);

        inputButtonPanel.add(inputPanel, BorderLayout.CENTER);
        inputButtonPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(inputButtonPanel, BorderLayout.NORTH);

        addButton.addActionListener(e -> {
            String newWord = inputTextField.getText();
            if (!newWord.isEmpty()) {
                addNewWhitelistWord.addNewWhitelistWord(newWord);
            }
            inputTextField.setText("");
        });

        removeButton.addActionListener(e -> {
            String wordToRemove = inputTextField.getText();
            if (!wordToRemove.isEmpty()) {
                removeWhitelistWord.removeWhitelistWord(wordToRemove);
            }
            inputTextField.setText("");
        });

        outputLabel = new JLabel();
        outputLabel.setHorizontalAlignment(JLabel.CENTER);
        outputLabel.setForeground(Color.BLACK);
        add(outputLabel, BorderLayout.SOUTH);

        JTabbedPane tabbedPane = new JTabbedPane();
        // Установка пользовательского UI для вкладок
        tabbedPane.setUI(new CustomTab());
        add(tabbedPane, BorderLayout.CENTER);
    }

    public void setOutputText(String message) {
        outputLabel.setText(message);
    }
}
