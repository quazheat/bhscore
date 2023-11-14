package fr.openai.starter;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import fr.openai.ui.VersionGUI;
import fr.openai.database.b;
import org.bson.Document;

public class VersionChecker {
    public String getCurrentVersion() {
        // Текущая версия программы
        return "6.6.7";
    }

    public String getDbVersion() {
        MongoCollection<Document> versionCollection = b.Zxc("version");
        MongoCursor<Document> cursor = versionCollection.find().iterator();

        if (cursor.hasNext()) {
            return cursor.next().getString("version");
        } else {
            return "0";
        }
    }

    public String getChangelog() {
        MongoCollection<Document> versionCollection = b.Zxc("version");
        MongoCursor<Document> cursor = versionCollection.find().iterator();

        if (cursor.hasNext()) {
            return cursor.next().getString("changelog");
        } else {
            return "";
        }
    }

    public void checkVersion() {
        VersionChecker versionChecker = new VersionChecker();
        String currentVersion = getCurrentVersion();
        String dbVersion = versionChecker.getDbVersion();
        String changelog = versionChecker.getChangelog();

        // Сравниваем версии
        if (dbVersion != null && !dbVersion.equals(currentVersion)) {
            VersionGUI versionGUI = new VersionGUI(dbVersion, changelog);
            versionGUI.setVisible(true);
        }
    }

}
