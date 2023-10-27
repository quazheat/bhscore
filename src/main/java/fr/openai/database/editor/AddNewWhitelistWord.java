package fr.openai.database.editor;

import com.mongodb.client.MongoCollection;
import fr.openai.database.IpAddressUtil;
import fr.openai.database.files.ConnectDb;
import fr.openai.database.files.TicketDocument;
import org.bson.Document;

import java.util.Date;

public class AddNewWhitelistWord {
    private final Editor editor;

    public AddNewWhitelistWord(Editor editor) {
        this.editor = editor;
    }

    public void addNewWhitelistWord(String words) {
        String[] wordArray = words.split(",");
        MongoCollection<Document> collection = ConnectDb.getMongoCollection("words");
        StringBuilder outputText = new StringBuilder();

        for (String word : wordArray) {
            word = word.trim().toLowerCase().replaceAll("[^a-zа-яё]", "");

            if (!word.isEmpty()) {
                Document updateDocument = new Document();
                updateDocument.append("$addToSet", new Document("whitelist", word));

                if (collection.countDocuments(new Document("whitelist", word)) == 0) {
                    collection.updateOne(new Document(), updateDocument);
                    outputText.append(word).append(" добавлено. ").append("\n");

                    // Send a ticket with the added word
                    sendTicket("added whitelist word: " + word);

                    return;
                }

                outputText.append(word).append(" уже есть. ").append("\n");
            }
        }

        editor.setOutputText(outputText.toString());
        ConnectDb.getWordsDB();
    }

    private void sendTicket(String ticketText) {
        // Create a TicketDocument for the ticket
        TicketDocument ticketDocument = new TicketDocument(new Date(), ticketText, "UNKNOWN", IpAddressUtil.getUserPublicIpAddress());

        // Get the MongoDB collection for tickets
        MongoCollection<Document> ticketCollection = ConnectDb.getMongoCollection("tickets");

        // Insert the ticket document into the database
        ticketCollection.insertOne(ticketDocument.toDocument());

        // Optionally, you can log the successful ticket creation or perform any additional actions here.
    }
}
