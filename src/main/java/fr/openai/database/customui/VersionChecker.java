package fr.openai.database.customui;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import fr.openai.database.files.ConnectDb;
import org.bson.Document;

public class VersionChecker {

    public static void checkVersion() {
        String currentVersion = "6.2"; // Текущая версия программы

        // Устанавливаем соединение с базой данных
        MongoCollection<Document> versionCollection = ConnectDb.getMongoCollection("version");

        // Получаем версию из базы данных (предположим, что она хранится в поле "version")
        MongoCursor<Document> cursor = versionCollection.find().iterator();

        if (cursor.hasNext()) {
            String dbVersion = cursor.next().getString("version");

            // Сравниваем версии
            if (!dbVersion.equals(currentVersion)) {
                VersionGUI versionGUI = new VersionGUI();
                versionGUI.setVisible(true);
            }
        }

        // Версии совпадают или версия не найдена, ничего не делаем
    }
}
