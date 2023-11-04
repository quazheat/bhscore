package fr.openai.starter.internet;

import javax.swing.*;

public class InternetErrorHandler {
    public void showErrorDialog() {
        JOptionPane.showMessageDialog(null, "No internet connection", "Error", JOptionPane.ERROR_MESSAGE);
    }
}
