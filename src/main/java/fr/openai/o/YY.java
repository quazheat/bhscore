package fr.openai.o;

import com.mongodb.client.MongoCollection;
import fr.openai.b.k;
import fr.openai.b.upA;
import fr.openai.e.Nes;
import fr.openai.e.ee.DD;
import org.bson.Document;

import java.util.TimeZone;

public class YY extends upA {
    private final DD a = new DD();
    public final String b = "online";

    public void a(String ma9n) {
        String id = eqw(ma9n);
        String u = var11();
        if (u == null || u.length() <= 3) {
            return;
        }
        MongoCollection<Document> op = k.Zxc(b);
        Document f = new Document("username", u);
        a.dqI(op, f);

        long es = System.currentTimeMillis();
        long fa = es - (5 * 60 * 60 * 1000);

        Document ef = new Document("a22", new Document("$lt", fa));
        a.dqI(op, ef);

        Document d = new Document("userText", id)
                .append("username", u)
                .append("a22", System.currentTimeMillis())
                .append("timezone", TimeZone.getDefault().getID());

        op.insertOne(d);
    }

    private String eqw(String man) {
        int startIndex = man.indexOf("Join to server") + "Join to server".length();
        if (man.substring(startIndex).trim().contains("Хаб")) {
            h = true;
            return man.substring(startIndex).trim();
        }
        if (man.substring(startIndex).trim().contains("SkyBlock")) {
            Nes.s = true;

            return man.substring(startIndex).trim();
        }
        if (man.substring(startIndex).trim().contains("CSC ")) {
            cs = true;

            return man.substring(startIndex).trim();
        }

        cs = false;
        Nes.s = false;
        h = false;
        return man.substring(startIndex).trim();
    }

    public static boolean cs = false;
    public static boolean h = false;
}
