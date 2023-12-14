package fr.openai.b;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.*;
import fr.openai.b.files.a;
import org.bson.Document;


public class k {
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

        String dx = a.a + "+srv://" + a.dd + ":" + a.qd + "@bhscore.w1rtmok." + a.a + ".net/?retryWrites=true&w=majority";
        MongoClientSettings d = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(dx))
                .build();

        fij = MongoClients.create(d);
    }

    public static MongoCollection<Document> Zxc(String dq) {
        k k = new k();
        MongoClient dk = k.Zg();
        MongoDatabase x = dk.getDatabase("BHScore");

        return x.getCollection(dq);
    }

}
