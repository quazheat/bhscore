package fr.openai.b.menu;

import com.mongodb.client.MongoCollection;
import fr.openai.b.upA;
import fr.openai.s.uu.ud;
import org.bson.Document;
import fr.openai.b.k;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class uu extends upA {

    private final MongoCollection<Document> zxc;
    private final ud ud;

    public uu() {
        this.zxc = k.Zxc("users");
        this.ud = new ud();
    }

    public void zxc(String s, String zx, boolean sS, boolean sSs) {
        if (zxc.find(new Document("uuid", zx)).limit(1).first() == null) {
            Document asd = new Document("username", s)
                    .append("uuid", zx)
                    .append("admin", sS)
                    .append("mod", sSs);

            zxc.insertOne(asd);
            System.out.println(s + " успешно добавлен.");
        } else {
            System.out.println(zx + " уже существует.");
        }
    }

    public boolean usd(String s) {
        Document d = zxc.find(new Document("username", s)).limit(1).first();
        if (d != null) {
            zxc.deleteOne(d);
            System.out.println(s + " успешно удален.");
            return true;
        } else {
            System.out.println(s + " не найден.");
            return false;
        }
    }

    public boolean awd(String uuid) {
        Document d = zxc.find(new Document("uuid", uuid)).limit(1).first();
        if (d != null) {
            zxc.deleteOne(d);
            System.out.println(uuid + " успешно удален.");
            return true;
        } else {
            System.out.println(uuid + " не найден.");
            return false;
        }
    }


    public void dqw() {
        List<Document> u = g();
        UUID o = ud.a();

        if (u.isEmpty()) {
            System.out.println("No users found.");
        } else {
            System.out.println("User List:");
            for (Document sd : u) {
                String a = sd.getString("username");
                String e = sd.getString("uuid");
                boolean eE = sd.getBoolean("admin", false);
                boolean eee = sd.getBoolean("mod", false);

                System.out.print("Username: " + a + ", UUID: " + e + ", A: " + eE + ", M: " + eee + "\n");

                if (eE && e.equals(o.toString())) {
                    uu.a = true;
                    m = true;

                }

                if (eee && e.equals(o.toString())) {
                    m = true;
                }

            }
        }

    }

    public static boolean a = false;
    public static boolean m = false;

    public List<Document> g() {
        return zxc.find().into(new ArrayList<>());
    }

    public List<String> gG() {
        List<String> ss = new ArrayList<>();
        List<Document> us = g();

        for (Document user : us) {
            String a = user.getString("uuid");
            ss.add(a);
        }

        return ss;
    }
}
