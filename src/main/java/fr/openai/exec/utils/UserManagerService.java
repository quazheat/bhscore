package fr.openai.exec.utils;

import fr.openai.database.menu.UserManager;
import fr.openai.ui.panels.UserManagerGUI;

import javax.swing.*;

public class UserManagerService {

    private final UserManager userManager = new UserManager();

    public void addUser(JFrame frame, String username, String uuid, boolean isAdmin) {
        if (!username.isEmpty() && !uuid.isEmpty()) {
            userManager.addUser(username, uuid, isAdmin);
            JOptionPane.showMessageDialog(frame, String.format("User %s added successfully.", username));
            showUserList(frame);
        } else {
            JOptionPane.showMessageDialog(frame, "Enter username and UUID.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void removeUser(JFrame frame, String username, String uuid) {
        if (!username.isEmpty()) {
            if (userManager.removeUserByUsername(username)) {
                JOptionPane.showMessageDialog(frame, String.format("User %s removed successfully.", username));
                showUserList(frame);
            }
        } else if (!uuid.isEmpty()) {
            if (userManager.removeUserByUUID(uuid)) {
                JOptionPane.showMessageDialog(frame, String.format("User with UUID %s removed successfully.", uuid));
                showUserList(frame);
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Enter username or UUID for removal.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showUserList(JFrame frame) {
        // show user list in the frame
        ((UserManagerGUI) frame).showUserList();
    }
}