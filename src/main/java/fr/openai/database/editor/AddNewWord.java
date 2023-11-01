package fr.openai.database.editor;

import com.mongodb.client.MongoCollection;
import fr.openai.database.IpAddressUtil;
import fr.openai.database.files.ConnectDb;
import fr.openai.database.files.TicketDocument;
import org.bson.Document;

import java.util.Date;

public class AddNewWord {
    private final Editor editor;

    public AddNewWord(Editor editor) {
        this.editor = editor;
    }

    public void addNewWord(String newWord) {
        // Normalize the new word to lowercase and remove special characters
        newWord = newWord.toLowerCase().replaceAll("[^a-zа-яё]", "");
        if (newWord.length() <= 1) {
            return;
        }

        // Get the MongoDB collection
        MongoCollection<Document> collection = ConnectDb.getMongoCollection("words");

        // Check if the word already exists in the array
        if (collection.countDocuments(new Document("forbidden_words", newWord)) == 0) {
            // Create a document for updating the database
            Document updateDocument = new Document("$addToSet", new Document("forbidden_words", newWord));

            // Add the new word to the forbidden_words array
            collection.updateOne(new Document(), updateDocument);

            // Send a ticket with the added word
            sendTicket("added word: " + newWord);

            editor.setOutputText(newWord + " добавлено.");
            ConnectDb.getWordsDB();
        } else {
            editor.setOutputText(newWord + " уже в списке.");
        }
    }

    private void sendTicket(String ticketText) {
        // Create a TicketDocument for the ticket
        TicketDocument ticketDocument = new TicketDocument(new Date(), ticketText, "UNKNOWN", IpAddressUtil.getUserPublicIpAddress());

        // Get the MongoDB collection for tickets
        MongoCollection<Document> ticketCollection = ConnectDb.getMongoCollection("tickets");

        // Insert the ticket document into the database
        ticketCollection.insertOne(ticketDocument.toDocument());

    }
}
