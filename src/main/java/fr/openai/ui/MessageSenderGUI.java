package fr.openai.ui;

import com.mongodb.client.MongoCollection;
import fr.openai.database.UsernameProvider;
import org.bson.Document;
import fr.openai.ui.customui.CustomButtonUI;

import javax.swing.*;
import java.awt.*;

public class MessageSenderGUI extends JFrame {

    private final UsernameProvider usernameProvider = new UsernameProvider() {
        @Override
        public String getUsername() {
            return super.getUsername();
        }
    };
    private final MongoCollection<Document> collection = fr.openai.database.b.Zxc("messager");

    private final JTextField uuidField;
    private final JTextField messageField;

    public MessageSenderGUI(String initialUuid) {
        setTitle("Message Sender");
        setSize(440, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.BOTH;

        JLabel titleLabel = new JLabel("Message Sender");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.BLACK);

        JLabel uuidLabel = new JLabel("UUID:");
        uuidField = new JTextField();
        uuidField.setColumns(24);
        uuidField.setEditable(false);

        JLabel messageLabel = new JLabel("Message:");
        messageField = new JTextField();
        messageField.setColumns(15);

        JButton sendButton = new JButton("Send");
        sendButton.setUI(new CustomButtonUI());
        sendButton.addActionListener(e -> sendMessage());

        gbc.insets = new Insets(15, 15, 15, 15);
        panel.add(titleLabel, gbc);

        gbc.gridy = 1;
        panel.add(uuidLabel, gbc);

        gbc.gridy = 2;
        panel.add(uuidField, gbc);

        gbc.gridy = 3;
        panel.add(messageLabel, gbc);

        gbc.gridy = 4;
        panel.add(messageField, gbc);

        gbc.gridy = 5;
        gbc.insets = new Insets(20, 15, 30, 15);
        panel.add(sendButton, gbc);

        ImageIcon icon = new ImageIcon("tray_icon.png");
        setIconImage(icon.getImage());

        Font font = new Font("Arial", Font.PLAIN, 16);
        titleLabel.setFont(font);
        uuidLabel.setFont(font);
        messageLabel.setFont(font);
        uuidField.setFont(font);
        messageField.setFont(font);
        sendButton.setFont(font);

        if (initialUuid != null && !initialUuid.isEmpty()) {
            uuidField.setText(initialUuid);
        } else {
            uuidField.setText("UUID and message cannot be empty");
        }

        add(panel);
        setVisible(true);
    }

    private void sendMessage() {
        String uuid = uuidField.getText();
        String message = messageField.getText();
        String senderName = usernameProvider.getUsername();

        if (!uuid.isEmpty() && !message.isEmpty()) {
            Document newDocument = new Document();
            newDocument.put("UUID", uuid);
            newDocument.put("senderName", senderName);
            System.out.println(senderName);
            newDocument.put("message", message);
            newDocument.put("Actual", true);

            collection.insertOne(newDocument);

            messageField.setText("");

            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "UUID and message cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
