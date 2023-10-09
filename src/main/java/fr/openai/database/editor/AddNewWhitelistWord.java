package fr.openai.database.editor;

import com.mongodb.client.MongoCollection;
import fr.openai.database.files.ConnectDb;
import org.bson.Document;

public class AddNewWhitelistWord {
    private final Editor editor; // поле для доступа к Editor

    public AddNewWhitelistWord(Editor editor) {
        this.editor = editor;
    }

    public void addNewWhitelistWord(String newWord) {
        // Приводим новое слово к нижнему регистру и удаляем лишние символы
        newWord = newWord.toLowerCase().replaceAll("[^a-zа-яё]", "");

        // Получить коллекцию из MongoDB
        MongoCollection<Document> collection = ConnectDb.getMongoCollection("words");

        // Создаем документ для обновления
        Document updateDocument = new Document();
        updateDocument.append("$addToSet", new Document("whitelist", newWord));

        // Проверяем, не существует ли уже такого слова в массиве whitelist
        if (collection.countDocuments(new Document("whitelist", newWord)) == 0) {
            // Добавляем новое слово в массив whitelist
            collection.updateOne(new Document(), updateDocument);
            editor.setOutputText(newWord + " добавлено в whitelist.");
            ConnectDb.getWordsDB();
        } else {
            editor.setOutputText(newWord + " уже существует в whitelist.");
        }
    }
}
