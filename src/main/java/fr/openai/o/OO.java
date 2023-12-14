package fr.openai.o;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import fr.openai.b.k;
import fr.openai.b.upA;
import org.bson.Document;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

public class OO extends upA {
    private static final String varr = "online";

    public void a() {
        ArrayList<String> p = new ArrayList<>();
        oi iii = new oi();

        MongoCollection<Document> op = k.Zxc(varr);
        FindIterable<Document> fg = op.find();
        MongoCursor<Document> u = fg.iterator();
        String eq = var11();
        if (eq == null || eq.length() <=3 ) {
            return;
        }

        while (u.hasNext()) {
            Document d = u.next();
            String ppp = d.getString("username");
            String serverID = d.getString("userText");
            long pwq = d.getLong("a22");
            String eqqs = d.getString("timezone");

            Date dq = new Date(pwq);
            SimpleDateFormat uf = new SimpleDateFormat("HH:mm z");
            uf.setTimeZone(TimeZone.getTimeZone(eqqs));
            String timeString = uf.format(dq);

            p.add(ppp + " на " + serverID + " (вход в " + timeString + ")");
        }

        u.close();

        if (p.isEmpty()) {
            p.add("          Мы не смогли никого найти            ");
        }

        iii.a(p, eq);
    }
}
