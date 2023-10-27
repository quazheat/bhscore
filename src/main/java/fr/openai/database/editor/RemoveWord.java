package fr.openai.database.editor;

import com.mongodb.client.MongoCollection;
import fr.openai.database.files.ConnectDb;
import org.bson.Document;

public class RemoveWord {
    private final Editor editor;

    public RemoveWord(Editor editor) {
        this.editor = editor;
    }

    public void removeWord(String wordToRemove) {
        // Приводим входное слово к нижнему регистру и удаляем лишние символы
        wordToRemove = wordToRemove.toLowerCase().replaceAll("[^a-zа-яё]", "");
        MongoCollection<Document> collection = ConnectDb.getMongoCollection("words"); //фильтр для поиска, удаляемое слово
        Document filter = new Document("forbidden_words", wordToRemove); // Проверяем, существует ли слово в массиве

        if (collection.countDocuments(filter) > 0) {
            // Создаем документ для обновления
            Document updateDocument = new Document("$pull", new Document("forbidden_words", wordToRemove));
            // Удаляем слово из массива forbidden_words
            collection.updateOne(new Document(), updateDocument);
            editor.setOutputText("Слово " + wordToRemove + " удалено из списка.");
            ConnectDb.getWordsDB();
            return;
        }

        editor.setOutputText("Слово " + wordToRemove + " не найдено.");
    }
}
