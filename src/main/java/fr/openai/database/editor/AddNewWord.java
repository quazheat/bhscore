package fr.openai.database.editor;

import com.mongodb.client.MongoCollection;
import fr.openai.database.files.ConnectDb;
import org.bson.Document;

public class AddNewWord {
    private final Editor editor; // Добавьте поле для доступа к Editor

    public AddNewWord(Editor editor) {
        this.editor = editor;
    }

    public void addNewWord(String newWord) {
        // Приводим новое слово к нижнему регистру
        newWord = newWord.toLowerCase();
        newWord = newWord.toLowerCase().replaceAll("[^a-zа-яё]", "");

        // Получить коллекцию из MongoDB
        MongoCollection<Document> collection = ConnectDb.getMongoCollection("words");

        // Создаем документ для обновления
        Document updateDocument = new Document();
        updateDocument.append("$addToSet", new Document("forbidden_words", newWord));

        // Проверяем, не существует ли уже такого слова в массиве
        if (collection.countDocuments(new Document("forbidden_words", newWord)) == 0) {

            // Добавляем новое слово в массив forbidden_words
            collection.updateOne(new Document(), updateDocument);
            collection.updateOne(new Document(), updateDocument);
            editor.setOutputText(newWord + " добавлено.");
            ConnectDb.getWordsDB();
        } else {
            editor.setOutputText(newWord + " уже в списке.");
        }
    }
}
