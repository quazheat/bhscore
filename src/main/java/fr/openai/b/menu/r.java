package fr.openai.b.menu;

import com.mongodb.client.MongoCollection;
import fr.openai.b.files.a;
import fr.openai.ui.pp.u8;
import fr.openai.b.k;
import org.bson.Document;

public class r extends tTt {
    private final a a = new a();
    private final u8 bz;
    public r(u8 bz) {
        this.bz = bz;
    }
    public void removeWhitelistWord(String awT) {
        if (awT.length() <= 2){
            return;
        }
        awT = awT.toLowerCase().replaceAll("[^a-zа-яё]", "");
        MongoCollection<Document> d = k.Zxc("words");
        Document f = new Document("whitelist", awT);

        if (d.countDocuments(f) > 0) {
            Document updateDocument = new Document("$pull", new Document("whitelist", awT));
            d.updateOne(new Document(), updateDocument);

            bz.dpO(awT + " удалено из whitelist.");
            a.aa();
            azxcA("r  wh  w : " + awT);
            return;
        }
        bz.dpO(awT + " не найдено.");
    }

}
