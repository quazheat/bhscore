package fr.openai.database.menu;

import com.mongodb.client.MongoCollection;
import fr.openai.database.IpAddressUtil;
import fr.openai.ui.panels.Menu;
import fr.openai.database.files.ConnectDb;
import fr.openai.database.files.TicketDocument;
import org.bson.Document;

import java.util.Date;

public class AddNewWhitelistWord {
    private final Menu menu;

    public AddNewWhitelistWord(Menu menu) {
        this.menu = menu;
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
                } else {
                    outputText.append(word).append(" уже есть. ").append("\n");
                }
            }
        }

        menu.setOutputText(outputText.toString());
        ConnectDb.getWordsDB();
    }


    private void sendTicket(String ticketText) {
        TicketDocument ticketDocument = new TicketDocument(new Date(), ticketText, "UNKNOWN", IpAddressUtil.getUserPublicIpAddress());
        MongoCollection<Document> ticketCollection = ConnectDb.getMongoCollection("tickets");
        ticketCollection.insertOne(ticketDocument.toDocument());

    }
}
