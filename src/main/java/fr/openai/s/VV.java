package fr.openai.s;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import fr.openai.ui.Vv;
import fr.openai.b.k;
import org.bson.Document;

public class VV {
    public String aex() {
        return "7.0.3.";
    }

    public String d() {
        MongoCollection<Document> a = k.Zxc("version");
        MongoCursor<Document> b = a.find().iterator();

        if (b.hasNext()) {
            return b.next().getString("version");
        } else {
            return "0";
        }
    }

    public String ch() {
        MongoCollection<Document> a = k.Zxc("version");
        MongoCursor<Document> r = a.find().iterator();

        if (r.hasNext()) {
            return r.next().getString("changelog");
        } else {
            return "";
        }
    }

    public void rs() {
        VV VV = new VV();
        String avbb = aex();
        String i = VV.d();
        String o = VV.ch();

        if (i != null && !i.equals(avbb)) {
            Vv vv = new Vv(i, o);
            vv.setVisible(true);
        }
    }

}
