package fr.openai.database.files;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import fr.openai.database.customui.CustomFrame;
import fr.openai.database.customui.SubmitTicketDialog;
import fr.openai.starter.uuid.HwidManager;
import fr.openai.starter.uuid.UuidChecker;
import org.bson.Document;

public class TicketSubmissionApp {
    private static TicketSubmissionApp instance;
    private MongoClient mongoClient;
    private JTextArea textArea;
    private CustomFrame frame;

    public static TicketSubmissionApp getInstance() {
        if (instance == null) {
            instance = new TicketSubmissionApp();
        }
        return instance;
    }

    public void showAppWindow() {
        if (frame != null && frame.isVisible()) {
            return;
        }

        mongoClient = ConnectDb.getMongoClient();

        ActionListener submitActionListener = e -> {
            UuidChecker uuidChecker = new UuidChecker();
            boolean isUuidAllowed = uuidChecker.isAllowed();

            String problemText = textArea.getText();
            Date timestamp = new Date();

            String databaseName = "BHScore";
            String collectionName = "tickets";

            AtomicBoolean isSubmitting = new AtomicBoolean(false);

            JButton submitButton = (JButton) e.getSource();
            submitButton.setEnabled(false); // Отключаем кнопку перед отправкой

            Thread submissionThread = new Thread(() -> {
                while (!isSubmitting.get()) {
                    try {
                        MongoDatabase database = mongoClient.getDatabase(databaseName);
                        MongoCollection<Document> collection = database.getCollection(collectionName);

                        TicketDocument ticketDocument = new TicketDocument(timestamp, problemText, isUuidAllowed ? HwidManager.getHwid() : "UNKNOWN");

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
        };

        frame = new CustomFrame("Reports", "tray_icon.png", submitActionListener);
        textArea = frame.getTextArea();
        frame.setVisible(true);
    }

    public void closeDb() {
        ConnectDb.closeMongoClient();
    }
}
