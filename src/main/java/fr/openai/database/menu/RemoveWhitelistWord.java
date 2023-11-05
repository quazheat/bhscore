package fr.openai.database.menu;

import com.mongodb.client.MongoCollection;
import fr.openai.database.files.GetWords;
import fr.openai.ui.panels.Menu;
import fr.openai.database.ConnectDb;
import org.bson.Document;

public class RemoveWhitelistWord {
    private final GetWords getWords = new GetWords();
    private final Menu menu;
    public RemoveWhitelistWord(Menu menu) {
        this.menu = menu;
    }
    public void removeWhitelistWord(String wordToRemove) {
        wordToRemove = wordToRemove.toLowerCase().replaceAll("[^a-zа-яё]", "");
        MongoCollection<Document> collection = ConnectDb.getMongoCollection("words");
        Document filter = new Document("whitelist", wordToRemove); // Создаем фильтр для поиска документа,

        if (collection.countDocuments(filter) > 0) {
            Document updateDocument = new Document("$pull", new Document("whitelist", wordToRemove)); // Создаем документ для обновления
            collection.updateOne(new Document(), updateDocument); // Обновляем документ в коллекции

            menu.setOutputText(wordToRemove + " удалено из whitelist.");
            getWords.getWordsFile();
            return;
        }
        menu.setOutputText(wordToRemove + " не найдено в whitelist.");
    }
}
