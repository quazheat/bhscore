package fr.openai.ui.panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import fr.openai.database.menu.UserManager;
import fr.openai.exec.utils.UserManagerService;
import fr.openai.ui.MessageSenderGUI;
import fr.openai.ui.customui.CustomButtonUI;
import org.bson.Document;

public class UserManagerGUI extends JFrame {

    private final UserManager userManager = new UserManager();
    private final UserManagerService userManagerService = new UserManagerService();
    private final JTextField usernameField;
    private final JTextField uuidField;
    private final JCheckBox adminCheckbox;
    private final JList<String> userList;

    private final JPopupMenu contextMenu;
    private final JCheckBox modCheckbox;

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
        modCheckbox = new JCheckBox("Mod");
        modCheckbox.setFocusPainted(false);

        JButton addUserButton = new JButton("Add User");
        CustomButtonUI.setCustomStyle(addUserButton);

        JButton removeUserButton = new JButton("Remove User");
        CustomButtonUI.setCustomStyle(removeUserButton);
        removeUserButton.addActionListener(e -> userManagerService.removeUser(this, usernameField.getText(), uuidField.getText()));

        addUserButton.addActionListener(e -> {
            boolean isAdmin = adminCheckbox.isSelected();
            boolean isMod = modCheckbox.isSelected();
            userManagerService.addUser(this, usernameField.getText(), uuidField.getText(), isAdmin, isMod);
        });

        inputPanel.add(nameLabel);
        inputPanel.add(usernameField);
        inputPanel.add(uuidLabel);
        inputPanel.add(uuidField);
        inputPanel.add(adminCheckbox);
        inputPanel.add(modCheckbox);

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

        contextMenu = new JPopupMenu();
        JMenuItem sendMessageItem = new JMenuItem("Send Message");
        sendMessageItem.addActionListener(e -> sendMessageToSelectedUser());
        contextMenu.add(sendMessageItem);

        userList.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                showContextMenu(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                showContextMenu(e);
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

    private void showContextMenu(MouseEvent e) {
        if (e.isPopupTrigger() && userList.getSelectedIndex() > 0) {
            contextMenu.show(e.getComponent(), e.getX(), e.getY());
        }
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
            boolean isMod = user.getBoolean("mod", false);


            String formattedInfo = "Username: " + username +
                    ", UUID: " + uuid +
                    (isAdmin ? ", Admin" : "") +
                    (isMod ? ", Mod" : "");

            adminCheckbox.setSelected(isAdmin);

            modCheckbox.setSelected(isMod);
            listModel.addElement(formattedInfo);
        }

        userList.setModel(listModel);
    }



    private void sendMessageToSelectedUser() {
        int selectedIndex = userList.getSelectedIndex();
        if (selectedIndex > 0) {
            String selectedUser = userList.getSelectedValue();
            if (selectedUser != null) {
                String uuid = getValueFromUserInfo(selectedUser, "UUID");

                SwingUtilities.invokeLater(() -> new MessageSenderGUI(uuid));
            }
        }
    }


    private void handleUserListSelection() {
        int selectedIndex = userList.getSelectedIndex();
        if (selectedIndex == 0) {
            userList.clearSelection();
        } else {
            String selectedUser = userList.getSelectedValue();
            if (selectedUser != null) {
                String[] userInfoArray = selectedUser.split(", ");
                updateFieldsFromUserInfo(userInfoArray);
            }
        }
    }


    private void updateFieldsFromUserInfo(String[] userInfoArray) {
        if (userInfoArray.length >= 2) {
            String username = getValueFromUserInfo(userInfoArray[0], "Username");
            String uuid = getValueFromUserInfo(userInfoArray[1], "UUID");

            usernameField.setText(username);
            uuidField.setText(uuid);

            if (userInfoArray.length == 3) {
                boolean isAdmin = Boolean.parseBoolean(getValueFromUserInfo(userInfoArray[2], "Admin"));
                adminCheckbox.setSelected(isAdmin);
            } else {
                adminCheckbox.setSelected(false);
            }
        }
    }


    private String getValueFromUserInfo(String info, String key) {
        String[] userInfoArray = info.split(", ");
        for (String userInfo : userInfoArray) {
            if (userInfo.startsWith(key)) {
                return userInfo.substring(userInfo.indexOf(":") + 2);
            }
        }
        return "";
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
