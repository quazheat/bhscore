package fr.openai.o;

import com.mongodb.client.MongoCollection;
import fr.openai.b.k;
import fr.openai.b.upA;
import fr.openai.e.Nes;
import fr.openai.e.ee.DD;
import fr.openai.s.VV;
import org.bson.Document;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class YY extends upA {
    private final DD a = new DD();
    public final String b = "online";
    private final VV VV = new VV();
    private final String v = VV.aex();

    public void a(String ma9n) {
        String id = eqw(ma9n);
        String u = var11();
        if (u == null || u.length() <= 3) {
            return;
        }
        MongoCollection<Document> op = k.Zxc(b);
        Document f = new Document("username", u);
        a.dqI(op, f);

        long es = getInternetTimeMillis(); //
        long fa = es - (5 * 60 * 60 * 1000);

        Document ef = new Document("a22", new Document("$lt", fa));
        a.dqI(op, ef);

        Document d = new Document("userText", id)
                .append("username", u)
                .append("a22", es)
                .append("timezone", ZoneId.of("Europe/Moscow").toString()) //  MSK
                .append("ver", v);

        op.insertOne(d);
    }

    private long getInternetTimeMillis() {
        try {
            URI uri = new URI("http://worldtimeapi.org/api/timezone/Europe/Moscow");
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            String time = response.body().split("\"datetime\":\"")[1].split("\"")[0];
            Instant instant = Instant.parse(time);
            ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(instant, ZoneId.of("UTC"));

            return zonedDateTime.toInstant().toEpochMilli();
        } catch (Exception e) {
            e.printStackTrace();
            return System.currentTimeMillis(); //
        }
    }

    private String eqw(String man) {
        int startIndex = man.indexOf("Join to server") + "Join to server".length();
        if (man.substring(startIndex).trim().contains("Хаб")) {
            h = true;
            return man.substring(startIndex).trim();
        }
        if (man.substring(startIndex).trim().contains("SkyBlock")) {
            Nes.s = true;
            return man.substring(startIndex).trim();
        }
        if (man.substring(startIndex).trim().contains("CSC ")) {
            cs = true;
            return man.substring(startIndex).trim();
        }

        cs = false;
        Nes.s = false;
        h = false;
        return man.substring(startIndex).trim();
    }

    public static boolean cs = false;
    public static boolean h = false;
}
