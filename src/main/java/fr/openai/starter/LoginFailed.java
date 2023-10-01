package fr.openai.starter;

import javax.swing.JOptionPane;

public class LoginFailed {
    public static void showErrorMessage(String errorMessage) {
        JOptionPane.showMessageDialog(null, errorMessage, "Login Failed", JOptionPane.ERROR_MESSAGE);
    }
}
