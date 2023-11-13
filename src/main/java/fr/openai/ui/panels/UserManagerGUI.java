package fr.openai.ui.panels;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import fr.openai.database.menu.UserManager;
import fr.openai.ui.customui.CustomButtonUI;
import fr.openai.exec.utils.UserManagerService;
import org.bson.Document;

public class UserManagerGUI extends JFrame {

    private final UserManager userManager = new UserManager();
    private final UserManagerService userManagerService = new UserManagerService();
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
        setResizable(false);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JLabel nameLabel = new JLabel("Username:");
        usernameField = new JTextField();
        usernameField.setColumns(15);
        JLabel uuidLabel = new JLabel("UUID:");
        uuidField = new JTextField();
        uuidField.setColumns(25);
        adminCheckbox = new JCheckBox("Admin");
        adminCheckbox.setFocusPainted(false);

        JButton addUserButton = new JButton("Add User");
        CustomButtonUI.setCustomStyle(addUserButton);

        JButton removeUserButton = new JButton("Remove User");
        CustomButtonUI.setCustomStyle(removeUserButton);
        removeUserButton.addActionListener(e -> userManagerService.removeUser(this, usernameField.getText(), uuidField.getText()));

        addUserButton.addActionListener(e -> userManagerService.addUser(this, usernameField.getText(), uuidField.getText(), adminCheckbox.isSelected()));

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
        userList.setCellRenderer(new UserListCellRenderer());
        userList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        userList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                handleUserListSelection();
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

    public void showUserList() {
        DefaultListModel<String> listModel = new DefaultListModel<>();
        List<Document> users = userManager.getUsers();

        if (users.isEmpty()) {
            listModel.addElement("No users found.");
            return;
        }
        listModel.addElement("User List:");
        for (Document user : users) {
            String username = user.getString("username");
            String uuid = user.getString("uuid");
            boolean isAdmin = user.getBoolean("admin", false);

            String formattedInfo = "Username: " + username +
                    ", UUID: " + uuid;

            // Include "Admin" part only if isAdmin is true
            formattedInfo += isAdmin ? ", Admin: true" : "";

            listModel.addElement(formattedInfo);
        }

        userList.setModel(listModel);
    }

    private void handleUserListSelection() {
        int selectedIndex = userList.getSelectedIndex();
        if (selectedIndex == 0) {
            userList.clearSelection();
        } else {
            String selectedUser = userList.getSelectedValue();
            if (selectedUser != null) {
                updateFieldsFromUserInfo(selectedUser);
            }
        }
    }

    private void updateFieldsFromUserInfo(String userInfo) {
        String[] userInfoArray = userInfo.split(", ");
        if (userInfoArray.length >= 2) {
            String username = getValueFromUserInfo(userInfoArray[0]);
            String uuid = getValueFromUserInfo(userInfoArray[1]);

            usernameField.setText(username);
            uuidField.setText(uuid);

            if (userInfoArray.length == 3) {
                boolean isAdmin = Boolean.parseBoolean(getValueFromUserInfo(userInfoArray[2]));
                adminCheckbox.setSelected(isAdmin);
            } else {
                adminCheckbox.setSelected(false);
            }
        }
    }

    private String getValueFromUserInfo(String info) {
        return info.substring(info.indexOf(":") + 2);
    }

    private static class UserListCellRenderer extends DefaultListCellRenderer {
        private final DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();

        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            JLabel renderer = (JLabel) defaultRenderer.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

            if (index == 0) {
                // Center the first item
                renderer.setHorizontalAlignment(SwingConstants.CENTER);
            }

            if (index != 0) {
                renderer.setHorizontalAlignment(SwingConstants.LEFT);
            }

            renderer.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));

            return renderer;
        }
    }

}