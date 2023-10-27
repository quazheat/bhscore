package fr.openai.database.editor;

import com.mongodb.client.MongoCollection;
import fr.openai.database.files.ConnectDb;
import org.bson.Document;

public class RemoveWhitelistWord {
    private final Editor editor;

    public RemoveWhitelistWord(Editor editor) {
        this.editor = editor;
    }

    public void removeWhitelistWord(String wordToRemove) {
        wordToRemove = wordToRemove.toLowerCase().replaceAll("[^a-zа-яё]", "");
        MongoCollection<Document> collection = ConnectDb.getMongoCollection("words");
        Document filter = new Document("whitelist", wordToRemove); // Создаем фильтр для поиска документа,

        // Проверяем, существует ли слово в массиве whitelist
        if (collection.countDocuments(filter) > 0) {
            Document updateDocument = new Document("$pull", new Document("whitelist", wordToRemove)); // Создаем документ для обновления
            collection.updateOne(new Document(), updateDocument); // Обновляем документ в коллекции

            editor.setOutputText(wordToRemove + " удалено из whitelist.");
            ConnectDb.getWordsDB();
            return;
        }

        editor.setOutputText(wordToRemove + " не найдено в whitelist.");
    }
}
