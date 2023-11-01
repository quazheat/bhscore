package fr.openai.database.customui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class InputValidator {
    public static void setupInputValidation(JTextField inputTextField, JButton addButton, JButton removeButton) {
        inputTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                validateInput(inputTextField, addButton, removeButton);
            }
        });
    }

    public static void validateInput(JTextField inputTextField, JButton addButton, JButton removeButton) {
        String inputText = inputTextField.getText().trim();  // Удаляем начальные и конечные пробелы

        boolean isInputValid = !inputText.matches(".*[^a-zA-Zа-яА-Я].*") && inputText.length() > 1;

        addButton.setEnabled(isInputValid);
        removeButton.setEnabled(isInputValid);

        if (isInputValid) {
            inputTextField.setForeground(Color.BLACK);  // Устанавливаем цвет текста в поле ввода в черный
        } else {
            inputTextField.setForeground(Color.RED);  // Устанавливаем цвет текста в поле ввода в красный
        }
    }
}
