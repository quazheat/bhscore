package fr.openai.b;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;
import fr.openai.ff.m.xc;

public class sss {
    private final MongoCollection<Document> op = k.Zxc("mutes_warns");

    public void ap(String var) {
        if (var.length() <= 3) {
            return;
        }
        int zxc = xc.gk();
        int zxc1 = xc.gv();

        Document sq = new Document("username", var);

        FindIterable<Document> fi = op.find(sq);
        MongoCursor<Document> cc = fi.iterator();

        if (cc.hasNext()) {
            Document ex = cc.next();
            int exm = ex.getInteger("mutes", 0);
            int exw = ex.getInteger("warns", 0);

            ex.put("mutes", exm + zxc);
            ex.put("warns", exw + zxc1);

            op.replaceOne(sq, ex);
            xc.a = 0;
            xc.nea = 0;
            return;
        }

        Document a = new Document();
        a.put("username", var);

        a.put("mutes", zxc);
        a.put("warns", zxc1);

        op.insertOne(a);
    }

    public void ia(String oe) {

        Document op = new Document("username", oe);
        FindIterable<Document> d1 = this.op.find(op);
        MongoCursor<Document> d = d1.iterator();

        if (d.hasNext()) {
            Document ot = new Document("$set", new Document("mutes", 0).append("warns", 0));
            this.op.updateOne(op, ot);
        }
    }

    public int sS(String main) {

        Document sQ = new Document("username", main);
        FindIterable<Document> ite = op.find(sQ);
        MongoCursor<Document> ccC = ite.iterator();

        if (ccC.hasNext()) {
            Document dc = ccC.next();
            return dc.getInteger("mutes", 0);
        }

        return 0;
    }

    public int s(String ka9) {

        Document sQ = new Document("username", ka9);
        FindIterable<Document> s89 = op.find(sQ);
        MongoCursor<Document> cC = s89.iterator();

        if (cC.hasNext()) {
            Document dC = cC.next();
            return dC.getInteger("warns", 0);
        }

        return 0;
    }
}
