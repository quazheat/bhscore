package fr.openai.database.menu;

import com.mongodb.client.MongoCollection;
import fr.openai.database.files.GetWords;
import fr.openai.ui.panels.Menu;
import fr.openai.database.ConnectDb;
import org.bson.Document;

public class AddNewWhitelistWord extends SendTicket {
    private final GetWords getWords = new GetWords();
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
        getWords.getWordsFile();
    }

}
