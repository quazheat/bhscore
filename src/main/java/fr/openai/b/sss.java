package fr.openai.b;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;
import fr.openai.ff.m.xc;

public class sss {
    private final MongoCollection<Document> op = k.Zxc("mutes_warns");

    public void ap(String var) {
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
            return;
        }

        Document a = new Document();
        a.put("username", var);
        a.put("mutes", zxc);
        a.put("warns", zxc1);

        op.insertOne(a);
    }

    public void deleteDocumentIfMatch(String username) {

        Document searchQuery = new Document("username", username);
        FindIterable<Document> findIterable = op.find(searchQuery);
        MongoCursor<Document> cursor = findIterable.iterator();

        if (cursor.hasNext()) {
            Document updateDocument = new Document("$set", new Document("mutes", 0).append("warns", 0));
            op.updateOne(searchQuery, updateDocument);
        }
    }

    public int sS(String username) {

        Document searchQuery = new Document("username", username);
        FindIterable<Document> findIterable = op.find(searchQuery);
        MongoCursor<Document> cursor = findIterable.iterator();

        if (cursor.hasNext()) {
            Document document = cursor.next();
            return document.getInteger("mutes", 0);
        }

        return 0;
    }

    public int s(String username) {

        Document searchQuery = new Document("username", username);
        FindIterable<Document> findIterable = op.find(searchQuery);
        MongoCursor<Document> cursor = findIterable.iterator();

        if (cursor.hasNext()) {
            Document document = cursor.next();
            return document.getInteger("warns", 0);
        }

        return 0;
    }
}
