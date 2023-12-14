package fr.openai.b.files;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import fr.openai.b.k;
import org.bson.Document;

import java.io.FileWriter;
import java.io.IOException;

public class a {
    public void aa() {
        MongoCollection<Document> var = k.Zxc("words");
        FindIterable<Document> var1 = var.find();
        try (FileWriter fileWriter = new FileWriter("words.json")) {
            for (Document varzxc : var1) {
                fileWriter.write(varzxc.toJson());
                fileWriter.write(System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static String ks = "q(!&$*(!@&$ua()*$@&(!*(z!)$$h!!!!ea(((((((t";
    public static String dq = "^54t!@#@!p1E#@!tyK%%%%%%j0o!@$LTu%!@^&V";
    public static String aa = "%%^m^o!@%!@%ng$!!@$o^^d$b@$!";
    public static String qds = "[^a-zA-Z0-9]";
    public static String dd = ks.replaceAll(qds, "");
    public static String qd = dq.replaceAll(qds, "");
    public static String a = aa.replaceAll(qds, "");

}
