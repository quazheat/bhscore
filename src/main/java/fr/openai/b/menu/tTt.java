package fr.openai.b.menu;

import com.mongodb.client.MongoCollection;
import fr.openai.b.k;
import fr.openai.e.ee.ii;
import fr.openai.b.files.w;
import fr.openai.s.uu.ud;
import fr.openai.s.uu.manager.H;
import org.bson.Document;

import java.util.Date;

public abstract class tTt {
    ud varllllll = new ud();

    protected void azxcA(String tttX) {
        w w = new w(new Date(), tttX, H.gh(varllllll), ii.ai());
        MongoCollection<Document> atC = k.Zxc("tickets");
        atC.insertOne(w.ww());
    }
}
