package fr.openai.ui.panels;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import fr.openai.database.IpAddressUtil;
import fr.openai.ui.customui.CustomButtonUI;
import fr.openai.ui.SubmitTicketDialog;
import fr.openai.database.files.ConnectDb;
import fr.openai.database.files.TicketDocument;
import fr.openai.starter.logs.UuidLog;
import fr.openai.starter.uuid.manager.HwidManager;
import fr.openai.starter.uuid.UuidChecker;
import fr.openai.starter.uuid.UuidProvider;
import org.bson.Document;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;

public class ReportsPanel extends JPanel {
    private final JTextArea textArea;
    private final UuidProvider uuidProvider = new UuidProvider();
    private final JButton sendButton;
    private final JFrame frame;
    private MongoClient mongoClient;

    public ReportsPanel(JFrame parentFrame) {
        this.frame = parentFrame;

        setLayout(new BorderLayout());

        textArea = new JTextArea();
        add(new JScrollPane(textArea), BorderLayout.CENTER);

        sendButton = new JButton("Отправить");
        sendButton.setEnabled(false); // Initially, disable the button
        add(sendButton, BorderLayout.SOUTH);

        // Применение стиля CustomButton
        CustomButtonUI.setCustomStyle(sendButton);

        // Add a KeyListener to the text area for input validation
        textArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                validateInput();
            }
        });

        sendButton.addActionListener(e -> {
            // Логика отправки тикета
            try {
                sendTicket();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    private void validateInput() {
        String problemText = textArea.getText().trim();
        boolean isInputValid = problemText.length() >= 1; // Input should contain at least 1 symbol

        sendButton.setEnabled(isInputValid);
    }

    private void sendTicket() throws IOException {
        if (mongoClient == null) {
            mongoClient = (MongoClient) ConnectDb.getMongoClient();
        }

        UuidChecker uuidChecker = new UuidChecker();
        boolean isUuidAllowed = uuidChecker.isAllowed();

        UuidLog.logUuid(isUuidAllowed); // Log whether UUID is allowed

        if (!isUuidAllowed) {
            System.exit(1); // Exit the program with code 1 if UUID is not allowed
        }

        String problemText = textArea.getText();
        Date timestamp = new Date();
        String ipAddress = IpAddressUtil.getUserPublicIpAddress();

        String databaseName = "BHScore";
        String collectionName = "tickets";

        AtomicBoolean isSubmitting = new AtomicBoolean(false);

        JButton submitButton = sendButton;
        submitButton.setEnabled(false); // Disable the button before sending

        Thread submissionThread = new Thread(() -> {
            while (!isSubmitting.get()) {
                try {
                    MongoDatabase database = mongoClient.getDatabase(databaseName);
                    MongoCollection<Document> collection = database.getCollection(collectionName);

                    TicketDocument ticketDocument = new TicketDocument(timestamp, problemText, HwidManager.getHwid(uuidProvider), ipAddress);

                    collection.insertOne(ticketDocument.toDocument());

                    SwingUtilities.invokeLater(() -> {
                        JOptionPane.showMessageDialog(frame, "Тикет успешно создан.");
                        frame.dispose();
                        textArea.setText(""); // Clear the text area
                    });
                    isSubmitting.set(true);
                } catch (Exception ex) {
                    SwingUtilities.invokeLater(() -> {
                        SubmitTicketDialog submitTicketDialog = new SubmitTicketDialog(frame);
                        submitTicketDialog.showDialog();
                        if (submitTicketDialog.isCanceled()) {
                            isSubmitting.set(true);
                        }
                    });
                }
            }
            SwingUtilities.invokeLater(() -> {
                submitButton.setEnabled(true); // Enable the button after sending
            });
        });

        submissionThread.start();
    }
}
