package fr.openai.database.files;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import fr.openai.database.customui.CustomFrame;
import fr.openai.starter.uuid.HwidManager;
import fr.openai.starter.uuid.UuidChecker;
import org.bson.Document;

public class TicketSubmissionApp {
    private MongoClient mongoClient;
    private JTextArea textArea;
    private CustomFrame frame;

    public void showAppWindow() {
        mongoClient = ConnectDb.getMongoClient();

        ActionListener submitActionListener = e -> {
            UuidChecker uuidChecker = new UuidChecker();
            boolean isUuidAllowed = uuidChecker.isAllowed();

            String problemText = textArea.getText();
            Date timestamp = new Date();

            String databaseName = "BHScore";
            String collectionName = "tickets";

            AtomicBoolean submitted = new AtomicBoolean(false);

            Thread submissionThread = new Thread(() -> {
                while (!submitted.get()) {
                    try {
                        MongoDatabase database = mongoClient.getDatabase(databaseName);
                        MongoCollection<Document> collection = database.getCollection(collectionName);

                        // Create a TicketDocument
                        TicketDocument ticketDocument = new TicketDocument(timestamp, problemText, isUuidAllowed ? HwidManager.getHwid() : "UNKNOWN");

                        // Insert the ticket document
                        collection.insertOne(ticketDocument.toDocument());

                        SwingUtilities.invokeLater(() -> {
                            JOptionPane.showMessageDialog(frame, "Тикет успешно создан.");
                            frame.dispose();
                        });
                        submitted.set(true);
                    } catch (Exception ex) {
                        SwingUtilities.invokeLater(() -> {
                            SubmitTicketDialog submitTicketDialog = new SubmitTicketDialog(frame);
                            submitTicketDialog.showDialog();
                            if (submitTicketDialog.isCanceled()) {
                                submitted.set(true);
                            }
                        });
                    }
                }
            });

            submissionThread.start();
        };

        frame = new CustomFrame("Reports", "tray_icon.png", submitActionListener);
        textArea = frame.getTextArea();
        frame.setVisible(true);
    }

    public void closeDb() {
        ConnectDb.closeMongoClient();
    }
}
