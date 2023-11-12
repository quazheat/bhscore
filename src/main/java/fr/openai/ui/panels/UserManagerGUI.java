package fr.openai.ui.panels;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import fr.openai.database.menu.UserManager;
import fr.openai.ui.customui.CustomButtonUI;
import org.bson.Document;

public class UserManagerGUI extends JFrame {

    private final UserManager userManager = new UserManager();

    private final JTextField usernameField;
    private final JTextField uuidField;
    private final JCheckBox adminCheckbox;
    private final JList<String> userList;

    public UserManagerGUI() {
        setTitle("User Manager");
        setSize(700, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        ImageIcon icon = new ImageIcon("tray_icon.png");
        setIconImage(icon.getImage());
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JLabel nameLabel = new JLabel("Username:");
        usernameField = new JTextField();
        usernameField.setColumns(15);
        JLabel uuidLabel = new JLabel("UUID:");
        uuidField = new JTextField();
        uuidField.setColumns(25);
        adminCheckbox = new JCheckBox("Admin");

        JButton addUserButton = new JButton("Add User");
        CustomButtonUI.setCustomStyle(addUserButton);

        JButton removeUserButton = new JButton("Remove User");
        CustomButtonUI.setCustomStyle(removeUserButton);
        removeUserButton.addActionListener(e -> removeUser());

        addUserButton.addActionListener(e -> addUser());

        inputPanel.add(nameLabel);
        inputPanel.add(usernameField);
        inputPanel.add(uuidLabel);
        inputPanel.add(uuidField);
        inputPanel.add(adminCheckbox);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(addUserButton);
        buttonPanel.add(removeUserButton);

        userList = new JList<>();
        userList.setCellRenderer(new CenteredTextCellRenderer());
        userList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selectedUser = userList.getSelectedValue();
                if (selectedUser != null) {
                    String[] userInfo = selectedUser.split(", ");
                    if (userInfo.length == 3) {
                        String username = userInfo[0].substring(userInfo[0].indexOf(":") + 2);
                        String uuid = userInfo[1].substring(userInfo[1].indexOf(":") + 2);
                        boolean isAdmin = Boolean.parseBoolean(userInfo[2].substring(userInfo[2].indexOf(":") + 2));

                        // Update the text fields with the selected user information
                        usernameField.setText(username);
                        uuidField.setText(uuid);
                        adminCheckbox.setSelected(isAdmin);
                    }
                }
            }
        });

        JScrollPane userListScrollPane = new JScrollPane(userList);
        userListScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        userListScrollPane.setPreferredSize(new Dimension(400, 150));

        mainPanel.add(inputPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        mainPanel.add(userListScrollPane, BorderLayout.NORTH);

        showUserList();

        add(mainPanel);
        setVisible(true);
    }

    private void addUser() {
        String username = usernameField.getText();
        String uuid = uuidField.getText();
        boolean isAdmin = adminCheckbox.isSelected();

        if (!username.isEmpty() && !uuid.isEmpty()) {
            userManager.addUser(username, uuid, isAdmin);
            JOptionPane.showMessageDialog(this, "User " + username + " added successfully.");
            showUserList();
        } else {
            JOptionPane.showMessageDialog(this, "Enter username and UUID.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void removeUser() {
        String username = usernameField.getText();
        String uuid = uuidField.getText();

        if (!username.isEmpty()) {
            if (userManager.removeUserByUsername(username)) {
                JOptionPane.showMessageDialog(this, "User " + username + " removed successfully.");
                showUserList();
            }
        } else if (!uuid.isEmpty()) {
            if (userManager.removeUserByUUID(uuid)) {
                JOptionPane.showMessageDialog(this, "User with UUID " + uuid + " removed successfully.");
                showUserList();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Enter username or UUID for removal.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showUserList() {
        DefaultListModel<String> listModel = new DefaultListModel<>();
        List<Document> users = userManager.getUsers();

        if (users.isEmpty()) {
            listModel.addElement("No users found.");
        } else {
            listModel.addElement("User List:");
            for (Document user : users) {
                String username = user.getString("username");
                String uuid = user.getString("uuid");
                boolean isAdmin = user.getBoolean("admin", false);

                listModel.addElement("Username: " + username + ", UUID: " + uuid + ", Admin: " + isAdmin);
            }
        }

        userList.setModel(listModel);
    }

    private static class CenteredTextCellRenderer extends DefaultListCellRenderer {
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if (c instanceof JLabel) {
                ((JLabel) c).setHorizontalAlignment(JLabel.CENTER);
            }
            return c;
        }
    }
}
