package fr.openai.database.customui;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import fr.openai.database.files.ConnectDb;
import fr.openai.database.files.TicketDocument;
import fr.openai.starter.uuid.HwidManager;
import fr.openai.starter.uuid.UuidChecker;
import fr.openai.starter.uuid.UuidProvider;
import org.bson.Document;

import javax.swing.*;
import java.awt.*;
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
        add(sendButton, BorderLayout.SOUTH);

        // Применение стиля CustomButton
        CustomButtonUI.setCustomStyle(sendButton);

        sendButton.addActionListener(e -> {
            // Логика отправки тикета
            sendTicket();
        });
    }

    private void sendTicket() {
        if (mongoClient == null) {
            mongoClient = ConnectDb.getMongoClient();
        }

        UuidChecker uuidChecker = new UuidChecker();
        boolean isUuidAllowed = uuidChecker.isAllowed();

        String problemText = textArea.getText();
        Date timestamp = new Date();

        String databaseName = "BHScore";
        String collectionName = "tickets";

        AtomicBoolean isSubmitting = new AtomicBoolean(false);

        JButton submitButton = sendButton;
        submitButton.setEnabled(false); // Отключаем кнопку перед отправкой

        Thread submissionThread = new Thread(() -> {
            while (!isSubmitting.get()) {
                try {
                    MongoDatabase database = mongoClient.getDatabase(databaseName);
                    MongoCollection<Document> collection = database.getCollection(collectionName);

                    TicketDocument ticketDocument = new TicketDocument(timestamp, problemText, isUuidAllowed ? HwidManager.getHwid(uuidProvider) : "UNKNOWN");

                    collection.insertOne(ticketDocument.toDocument());

                    SwingUtilities.invokeLater(() -> {
                        JOptionPane.showMessageDialog(frame, "Тикет успешно создан.");
                        frame.dispose();
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
                submitButton.setEnabled(true); // Включаем кнопку после отправки
            });
        });

        submissionThread.start();
    }
}
