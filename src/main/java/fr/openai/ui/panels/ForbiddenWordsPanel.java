package fr.openai.ui.panels;

import fr.openai.ui.customui.CustomButtonUI;
import fr.openai.ui.customui.CustomTab;
import fr.openai.database.menu.InputValidator;
import fr.openai.database.menu.AddNewWord;
import fr.openai.database.menu.RemoveWord;

import javax.swing.*;
import java.awt.*;

public class ForbiddenWordsPanel extends JPanel {
    private final JTextField inputTextField;
    private final JLabel outputLabel;

    public ForbiddenWordsPanel(AddNewWord addNewWord, RemoveWord removeWord) {
        setLayout(new BorderLayout());
        setBackground(Color.lightGray);

        // Create the output label at the top
        outputLabel = new JLabel();
        outputLabel.setHorizontalAlignment(JLabel.CENTER);
        outputLabel.setForeground(Color.BLACK);
        add(outputLabel, BorderLayout.NORTH);

        JPanel inputButtonPanel = new JPanel();
        inputButtonPanel.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel inputLabel = new JLabel("Введите слово:");
        inputLabel.setForeground(Color.BLACK);
        inputPanel.setBackground(Color.LIGHT_GRAY);

        inputTextField = new JTextField(20);
        inputPanel.add(inputLabel);
        inputPanel.add(inputTextField);

        JButton addButton = new JButton("Добавить");
        JButton removeButton = new JButton("Удалить");
        CustomButtonUI.setCustomStyle(addButton);
        CustomButtonUI.setCustomStyle(removeButton);
        addButton.setEnabled(false);
        removeButton.setEnabled(false);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.setBackground(Color.LIGHT_GRAY);

        inputButtonPanel.add(inputPanel, BorderLayout.CENTER);
        inputButtonPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(inputButtonPanel, BorderLayout.CENTER);

        addButton.addActionListener(e -> {
            String newWord = inputTextField.getText();
            if (!newWord.isEmpty()) {
                addNewWord.addNewWord(newWord);
            }
            inputTextField.setText("");
        });

        removeButton.addActionListener(e -> {
            String wordToRemove = inputTextField.getText();
            if (!wordToRemove.isEmpty()) {
                removeWord.removeWord(wordToRemove);
            }
            inputTextField.setText("");
        });

        InputValidator.setupInputValidation(inputTextField, addButton, removeButton);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setUI(new CustomTab());
        add(tabbedPane, BorderLayout.SOUTH);
    }

    public void setOutputText(String message) {
        outputLabel.setText(message);
    }
}
