package fr.openai.starter;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import fr.openai.database.customui.VersionGUI;
import fr.openai.database.files.ConnectDb;
import org.bson.Document;

public class VersionChecker {
    public static String getCurrentVersion() {
        // Текущая версия программы
        return "6.5";
    }

    public static String getDbVersion() {
        MongoCollection<Document> versionCollection = ConnectDb.getMongoCollection("version");

        MongoCursor<Document> cursor = versionCollection.find().iterator();

        if (cursor.hasNext()) {
            return cursor.next().getString("version");
        } else {
            return "0";
        }
    }

    public static void checkVersion() {
        String currentVersion = getCurrentVersion();
        String dbVersion = getDbVersion();

        // Сравниваем версии
        if (dbVersion != null && !dbVersion.equals(currentVersion)) {
            VersionGUI versionGUI = new VersionGUI();
            versionGUI.setVisible(true);
        }
    }
}
