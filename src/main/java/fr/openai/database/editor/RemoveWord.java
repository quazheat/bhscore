package fr.openai.database.editor;

import com.mongodb.client.MongoCollection;
import fr.openai.database.files.ConnectDb;
import org.bson.Document;

public class RemoveWord {
    private final Editor editor; // Добавьте поле для доступа к Editor

    public RemoveWord(Editor editor) {
        this.editor = editor;
    }

    public void removeWord(String wordToRemove) {
        // Приводим входное слово к нижнему регистру и удаляем лишние символы
        wordToRemove = wordToRemove.toLowerCase().replaceAll("[^a-zа-яё]", "");

        // Получить коллекцию из MongoDB
        MongoCollection<Document> collection = ConnectDb.getMongoCollection("words");

        // Создаем фильтр для поиска документа, содержащего удаляемое слово
        Document filter = new Document("forbidden_words", wordToRemove);

        // Проверяем, существует ли слово в массиве forbidden_words
        if (collection.countDocuments(filter) > 0) {
            // Создаем документ для обновления
            Document updateDocument = new Document("$pull", new Document("forbidden_words", wordToRemove));

            // Удаляем слово из массива forbidden_words
            collection.updateOne(new Document(), updateDocument);

            editor.setOutputText("Слово " + wordToRemove + " удалено из списка.");
            ConnectDb.getWordsDB();
        } else {
            editor.setOutputText("Слово " + wordToRemove + " не найдено.");
        }
    }
}
