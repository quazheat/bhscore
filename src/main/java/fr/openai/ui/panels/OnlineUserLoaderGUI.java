package fr.openai.ui.panels;

import com.mongodb.client.MongoCollection;
import fr.openai.database.ConnectDb;
import fr.openai.online.DatabaseUtils;
import org.bson.Document;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class OnlineUserLoaderGUI {
    public final String COLLECTION_NAME = "online";

    public void createAndShowGUI(ArrayList<String> onlineUsers, String yourUsername) {
        JFrame frame = new JFrame("Онлайн");
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.setResizable(false);
        ImageIcon icon = new ImageIcon("tray_icon.png");
        frame.setIconImage(icon.getImage());

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        for (String user : onlineUsers) {
            JPanel userPanel = new JPanel();
            userPanel.setLayout(new FlowLayout());
            userPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

            JLabel label = new JLabel(user);
            userPanel.add(label);

            if (user.contains(yourUsername)) {
                JButton removeButton = new JButton("X");
                removeButton.setFocusPainted(false);
                removeButton.setBackground(Color.LIGHT_GRAY);
                removeButton.setForeground(Color.RED);
                removeButton.setFont(new Font("Serif", Font.BOLD, 15));

                final JPanel finalUserPanel = userPanel;
                removeButton.addActionListener(e -> {
                    MongoCollection<Document> collection = ConnectDb.getMongoCollection(COLLECTION_NAME);
                    Document filter = new Document("username", yourUsername);
                    DatabaseUtils.deleteDocuments(collection, filter);
                    finalUserPanel.setVisible(false);
                    frame.revalidate();
                    frame.repaint();
                });

                userPanel.add(removeButton);
            }

            panel.add(userPanel);
        }

        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        frame.add(scrollPane);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}
