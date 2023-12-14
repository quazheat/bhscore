package fr.openai.b.menu;

import com.mongodb.client.MongoCollection;
import fr.openai.b.files.a;
import fr.openai.ui.pp.u8;
import fr.openai.b.k;
import org.bson.Document;

public class aW extends tTt {
    private final a a = new a();
    private final u8 x;
    public aW(u8 x) {
        this.x = x;
    }

    public void wA(String pSt) {
        String[] a = pSt.split(",");
        MongoCollection<Document> var1 = k.Zxc("words");
        StringBuilder d = new StringBuilder();

        for (String pst : a) {
            if (pst.length() <= 2) {
                return;
            }
            pst = pst.trim().toLowerCase().replaceAll("[^a-zа-яё]", "");

            if (!pst.isEmpty()) {
                Document pd = new Document();
                pd.append("$addToSet", new Document("whitelist", pst));

                if (var1.countDocuments(new Document("whitelist", pst)) == 0) {
                    var1.updateOne(new Document(), pd);
                    d.append(pst).append(" добавлено. ").append("\n");

                    azxcA("a  w  w : " + pst);
                } else {
                    d.append(pst).append(" уже есть. ").append("\n");
                }
            }
        }
        x.dpO(d.toString());
        this.a.aa();
    }

}
