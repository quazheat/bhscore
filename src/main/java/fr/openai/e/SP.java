package fr.openai.e;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import fr.openai.b.j;
import fr.openai.e.ee.jcr;

import java.util.Objects;
import java.util.regex.Pattern;

public class SP {

    private static JsonArray il;

    static {
        jcr jcr = new jcr();
        jcr.cr();
        il = j.jj("ignore.json").getAsJsonArray("ignore_words");
    }

    public static String pS(String ae) {
        String eaz = o(ae);
        return Objects.requireNonNullElse(eaz, "No valid result");
    }

    private static String o(String xxx) {
        for (JsonElement e : il) {
            String tri = e.getAsString();
            xxx = xxx.replaceAll("(?U)\\b" + Pattern.quote(tri) + "\\b", "").trim();
        }
        return xxx;
    }

    public static void up(JsonArray upIl) {
        il = upIl;
    }
}
