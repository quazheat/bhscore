package fr.openai.starter;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import fr.openai.ui.VersionGUI;
import fr.openai.database.ConnectDb;
import org.bson.Document;

public class VersionChecker {
    public String getCurrentVersion() {
        // Текущая версия программы
        return "6.5.9";
    }

    public String getDbVersion() {
        MongoCollection<Document> versionCollection = ConnectDb.getMongoCollection("version");

        MongoCursor<Document> cursor = versionCollection.find().iterator();

        if (cursor.hasNext()) {
            return cursor.next().getString("version");
        } else {
            return "0";
        }
    }

    public void checkVersion() {
        VersionChecker versionChecker = new VersionChecker();
        String currentVersion = getCurrentVersion();
        String dbVersion = versionChecker.getDbVersion();

        // Сравниваем версии
        if (dbVersion != null && !dbVersion.equals(currentVersion)) {
            VersionGUI versionGUI = new VersionGUI();
            versionGUI.setVisible(true);
        }
    }
}
