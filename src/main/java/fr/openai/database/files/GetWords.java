package fr.openai.database.files;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import fr.openai.database.ConnectDb;
import org.bson.Document;

import java.io.FileWriter;
import java.io.IOException;

public class GetWords {
    public void getWordsFile() {
        MongoCollection<Document> collection = ConnectDb.getMongoCollection("words");
        FindIterable<Document> documents = collection.find();
        try (FileWriter fileWriter = new FileWriter("words.json")) {
            for (Document document : documents) {
                fileWriter.write(document.toJson());
                fileWriter.write(System.lineSeparator()); // Добавить перевод строки между документами
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("успешно экспортирован файл 'words.json'.");
    }

}
