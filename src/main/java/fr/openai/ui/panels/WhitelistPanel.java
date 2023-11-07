package fr.openai.ui.panels;

import fr.openai.ui.customui.CustomButtonUI;
import fr.openai.ui.customui.CustomTab;
import fr.openai.database.menu.InputValidator;
import fr.openai.database.menu.AddNewWhitelistWord;
import fr.openai.database.menu.RemoveWhitelistWord;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class WhitelistPanel extends JPanel {
    private final JTextField inputField;

    public WhitelistPanel(AddNewWhitelistWord addNewWhitelistWord, RemoveWhitelistWord removeWhitelistWord) {
        setLayout(new BorderLayout());
        String watermark = "Слово во множественном числе";
        setBackground(Color.lightGray);

        // Create the output label at the top
        JLabel outputLabel = new JLabel();
        outputLabel.setHorizontalAlignment(JLabel.CENTER);
        outputLabel.setForeground(Color.BLACK);
        add(outputLabel, BorderLayout.NORTH);

        JPanel inputButtonPanel = new JPanel();
        inputButtonPanel.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel inputLabel = new JLabel("Введите слово:");
        inputLabel.setForeground(Color.black);
        inputPanel.setBackground(Color.LIGHT_GRAY);

        inputField = new JTextField(watermark, 20);
        inputField.setForeground(Color.GRAY);
        inputPanel.add(inputLabel);
        inputPanel.add(inputField);

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

        inputField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (inputField.getText().equals(watermark)) {
                    inputField.setText("");
                    inputField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (inputField.getText().isEmpty()) {
                    inputField.setText(watermark);
                    inputField.setForeground(Color.GRAY);
                }
            }
        });

        addButton.addActionListener(e -> {
            String newWord = inputField.getText();
            if (!newWord.isEmpty()) {
                addNewWhitelistWord.addNewWhitelistWord(newWord);
            }
            inputField.setText("");
        });

        removeButton.addActionListener(e -> {
            String wordToRemove = inputField.getText();
            if (!wordToRemove.isEmpty()) {
                removeWhitelistWord.removeWhitelistWord(wordToRemove);
            }
            inputField.setText("");
        });

        InputValidator.setupInputValidation(inputField, addButton, removeButton);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setUI(new CustomTab());
        add(tabbedPane, BorderLayout.SOUTH);
    }
}
