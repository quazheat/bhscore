package fr.openai.n;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import fr.openai.b.k;
import org.bson.Document;
import fr.openai.s.uu.ud;
import fr.openai.s.uu.manager.H;

public class mm {
    private final ud ud = new ud();
    private final ae ae = new ae();

    public void zx() {
        try {
            MongoCollection<Document> op = k.Zxc("messager");

            String ea = H.gh(ud);

            Document sq = new Document("UUID", ea)
                    .append("Actual", true);

            FindIterable<Document> fi = op.find(sq);

            for (Document a : fi) {
                String b = a.getString("senderName");
                String c = a.getString("varM");

                ae.e(b, c);
                if (c.equalsIgnoreCase("ban")) {
                    System.exit(0);
                }
                a.put("Actual", false);
                op.replaceOne(sq, a);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
