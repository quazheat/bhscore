package fr.openai.b;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import java.util.concurrent.CopyOnWriteArrayList;

public class dzz {
    private static final String de = "config.properties";
    private static final String v1 = "log_rnt_path";
    private static final String v11 = "upFQ";
    private static final String v33 = "username"; // New key for the username
    private static final String vw3 = System.getProperty("user.home") + File.separator + ".cristalix" +
            File.separator + "updates" + File.separator + "Minigames" + File.separator + "logs" + File.separator + "latest.log";
    private static final int dw = 40;

    private final Properties eq;
    private int q;
    private final CopyOnWriteArrayList<er> oqz = new CopyOnWriteArrayList<>();

    public dzz() {
        this.eq = new Properties();
        e1();
    }

    private void e1() {
        try (InputStream i = new FileInputStream(de)) {
            eq.load(new InputStreamReader(i, StandardCharsets.UTF_8));
            q = w();
        } catch (IOException e) {
            eq.setProperty(v1, vw3);
            eq.setProperty(v11, String.valueOf(dw));
            q = dw;
            s();
        }
    }


    private void s() {
        try (OutputStream sz = new FileOutputStream(de)) {
            eq.store(new OutputStreamWriter(sz, StandardCharsets.UTF_8), "BHScore Configuration");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private String aaA() {
        String var = eq.getProperty(dzz.v1);
        return (var != null) ? var : "";
    }

    private int w() {
        String var = eq.getProperty(dzz.v11);
        try {
            return (var != null) ? Integer.parseInt(var) : 0;
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public String bb() {
        return aaA();
    }

    public int bq() {
        return q;
    }

    public void qq(int q) {
        this.q = q;
        eq.setProperty(v11, String.valueOf(q));
        s();

        for (er s : oqz) {
            s.ch(q);
        }
    }

    public void is(String i) {
        eq.setProperty(v33, new String(i.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));
        s();
    }


    public String us() {
        return eq.getProperty(v33);
    }

    public interface er {
        void ch(int jq);
    }

    public void zd(er w) {
        oqz.add(w);
    }
}
