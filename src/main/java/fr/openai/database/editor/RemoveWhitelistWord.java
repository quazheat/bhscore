package fr.openai.database.editor;

import com.mongodb.client.MongoCollection;
import fr.openai.database.files.ConnectDb;
import org.bson.Document;

public class RemoveWhitelistWord {
    private final Editor editor; // поле для доступа к Editor

    public RemoveWhitelistWord(Editor editor) {
        this.editor = editor;
    }

    public void removeWhitelistWord(String wordToRemove) {
        // Приводим входное слово к нижнему регистру и удаляем лишние символы
        wordToRemove = wordToRemove.toLowerCase().replaceAll("[^a-zа-яё]", "");

        // Получить коллекцию из MongoDB
        MongoCollection<Document> collection = ConnectDb.getMongoCollection("words");

        // Создаем фильтр для поиска документа, содержащего удаляемое слово в массиве whitelist
        Document filter = new Document("whitelist", wordToRemove);

        // Проверяем, существует ли слово в массиве whitelist
        if (collection.countDocuments(filter) > 0) {
            // Создаем документ для обновления
            Document updateDocument = new Document("$pull", new Document("whitelist", wordToRemove));

            // Обновляем документ в коллекции
            collection.updateOne(new Document(), updateDocument);

            editor.setOutputText(wordToRemove + " удалено из whitelist.");
            ConnectDb.getWordsDB();
        } else {
            editor.setOutputText(wordToRemove + " не найдено в whitelist.");
        }
    }
}
