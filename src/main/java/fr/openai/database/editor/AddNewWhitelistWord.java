package fr.openai.database.editor;

import com.mongodb.client.MongoCollection;
import fr.openai.database.files.ConnectDb;
import org.bson.Document;

public class AddNewWhitelistWord {
    private final Editor editor; // поле для доступа к Editor

    public AddNewWhitelistWord(Editor editor) {
        this.editor = editor;
    }

    public void addNewWhitelistWord(String words) {
        // Разделяем входную строку на слова, используя запятую как разделитель
        String[] wordArray = words.split(",");

        // Получить коллекцию из MongoDB
        MongoCollection<Document> collection = ConnectDb.getMongoCollection("words");

        StringBuilder outputText = new StringBuilder(); // Создаем StringBuilder для накопления текста

        // Перебираем каждое слово и добавляем его в базу данных
        for (String word : wordArray) {
            // Приводим слово к нижнему регистру и удаляем лишние символы
            word = word.trim().toLowerCase().replaceAll("[^a-zа-яё]", "");

            if (!word.isEmpty()) {
                // Создаем документ для обновления
                Document updateDocument = new Document();
                updateDocument.append("$addToSet", new Document("whitelist", word));

                // Проверяем, не существует ли уже такого слова в массиве whitelist
                if (collection.countDocuments(new Document("whitelist", word)) == 0) {
                    // Добавляем новое слово в массив whitelist
                    collection.updateOne(new Document(), updateDocument);
                    outputText.append(word).append(" добавлено. ").append("\n");
                } else {
                    outputText.append(word).append(" уже есть. ").append("\n");
                }
            }
        }

        // Установим собранный текст в нижней панели
        editor.setOutputText(outputText.toString());
        ConnectDb.getWordsDB();
    }

}
