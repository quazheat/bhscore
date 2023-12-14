package fr.openai.b.menu;

import com.mongodb.client.MongoCollection;
import fr.openai.b.files.a;
import fr.openai.ui.pp.u8;
import fr.openai.b.k;
import org.bson.Document;

public class aNw extends tTt {
    private final u8 zX;
    private final a a = new a();

    public aNw(u8 zX) {
        this.zX = zX;
    }

    public void sss(String po) {
        po = po.toLowerCase().replaceAll("[^a-zа-яё]", "");
        if (po.length() <= 2) {
            return;
        }

        MongoCollection<Document> o = k.Zxc("words");

        if (o.countDocuments(new Document("forbidden_words", po)) == 0) {
            Document updateDocument = new Document("$addToSet", new Document("forbidden_words", po));
            o.updateOne(new Document(), updateDocument);
            azxcA("a  w : " + po);

            zX.dpO(po + " добавлено.");
            a.aa();
        } else {
            zX.dpO(po + " уже в списке.");
        }
    }
}
