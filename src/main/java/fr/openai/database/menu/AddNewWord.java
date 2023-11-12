package fr.openai.database.menu;

import com.mongodb.client.MongoCollection;
import fr.openai.database.files.GetWords;
import fr.openai.ui.panels.Menu;
import fr.openai.database.b;
import org.bson.Document;

public class AddNewWord extends SendTicket {
    private final Menu menu;
    private final GetWords getWords = new GetWords();

    public AddNewWord(Menu menu) {
        this.menu = menu;
    }

    public void addNewWord(String newWord) {
        // Normalize the new word to lowercase and remove special characters
        newWord = newWord.toLowerCase().replaceAll("[^a-zа-яё]", "");
        if (newWord.length() <= 2) {
            return;
        }

        MongoCollection<Document> collection = b.Zxc("words");

        if (collection.countDocuments(new Document("forbidden_words", newWord)) == 0) {
            Document updateDocument = new Document("$addToSet", new Document("forbidden_words", newWord));
            collection.updateOne(new Document(), updateDocument);
            sendTicket("added word: " + newWord);

            menu.setOutputText(newWord + " добавлено.");
            getWords.getWordsFile();
        } else {
            menu.setOutputText(newWord + " уже в списке.");
        }
    }
}
