package fr.openai.b.menu;

import com.mongodb.client.MongoCollection;
import fr.openai.b.files.a;
import fr.openai.ui.pp.u8;
import fr.openai.b.k;
import org.bson.Document;

public class ds extends tTt {
    private final u8 sS;
    private final a a = new a();

    public ds(u8 s) {
        this.sS = s;
    }

    public void dda(String oPt) {
        if (oPt.length() <= 2){
            return;
        }
        oPt = oPt.toLowerCase().replaceAll("[^a-zа-яё]", "");
        MongoCollection<Document> o = k.Zxc("words");
        Document f = new Document("forbidden_words", oPt);

        if (o.countDocuments(f) > 0) {
            Document updateDocument = new Document("$pull", new Document("forbidden_words", oPt));
            o.updateOne(new Document(), updateDocument);
            sS.dpO("Слово " + oPt + " удалено из списка.");
            a.aa();
            azxcA("removed word: " + oPt);
            return;
        }

        sS.dpO(oPt + " не найдено.");
    }
}
