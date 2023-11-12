package fr.openai.database;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.*;
import org.bson.Document;

public class b {
    private static MongoClient fij;

    public MongoClient Zg() {
        if (fij == null) {
            Zi();
        }
        return fij;
    }

    public void dqzxc() {
        if (fij != null) {
            fij.close();
        }
    }

    public void Zi() {
        String ks = "q(!&$*(!@&$ua()*$@&(!*(z!)$$h!!!!ea(((((((t";
        String dq = "^54t!@#@!p1E#@!tyK%%%%%%j0o!@$LTu%!@^&V";
        String qds = "[^a-zA-Z0-9]";
        String dd = ks.replaceAll(qds, "");
        String qd = dq.replaceAll(qds, "");
        String dx = "mongodb+srv://" + dd + ":" + qd + "@bhscore.w1rtmok.mongodb.net/?retryWrites=true&w=majority";
        MongoClientSettings d = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(dx))
                .build();

        fij = MongoClients.create(d);
    }

    public static MongoCollection<Document> Zxc(String dq) {
        b b = new b();
        MongoClient dk = b.Zg();
        MongoDatabase database = dk.getDatabase("BHScore");

        return database.getCollection(dq);
    }

}
