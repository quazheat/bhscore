package fr.openai.ff;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import fr.openai.b.j;
import fr.openai.b.upA;
import fr.openai.ff.fixer.L;

public class f extends upA {

    private final String oa = var11();

    public boolean var(String main) {
        char[] c = main.toCharArray();
        int a = 6;

        for (int i = 1, e = 1; i < c.length; i++) {
            if (c[i] == c[i - 1]) {
                e++;
            } else {
                e = 1;
            }

            if (e >= a) {
                return true;
            }
        }
        return false;

    }

    public boolean h(String ka9) {
        String[] wz = ka9.split("\\s+");

        for (String z : wz) {
            if (z.length() > 9) {
                int e = (int) z.chars().distinct().count();
                if (e <= 3) {

                    return true;
                }
            }
        }
        return false;

    }


    public boolean aa(String main) {
        String d = rq(main);

        if (d.length() < 10) {
            int s = (int) d.chars().distinct().count();
            if (s == 2) {
                return false;
            }
        }

        int e = 0;
        for (char c : d.toCharArray()) {
            if (Character.isUpperCase(c)) {
                e++;
            }
        }

        return e > 5 && (double) e / d.length() > 0.55;
    }

    public boolean fw(String main) {
        String[] ka = main.split("\\s+");
        int d = 5;
        int a = 1;

        for (int i = 1; i < ka.length; i++) {
            if (ka[i].equals(ka[i - 1])) {
                a++;
                if (a >= d) {
                    return true;
                }
            } else {
                a = 1;
            }
        }
        return false;
    }


    public boolean e(String main) {
        JsonObject qwe = j.jj("words.json");
        JsonArray abs = qwe.getAsJsonArray("whitelist");
        JsonArray abv = qwe.getAsJsonArray("forbidden_words");

        L wqe = new L();

        main = main.toLowerCase();
        String[] r = main.split("[\\s,.;!?]+");

        for (String z : r) {
            boolean abvS = false;
            for (int i = 0; i < abs.size(); i++) {
                String w = abs.get(i).getAsString();
                double x = wqe.apply(z, w);
                if (x >= 0.8) {
                    abvS = true;

                    break;
                }
            }
            if (!abvS) {
                for (int i = 0; i < abv.size(); i++) {
                    String a = abv.get(i).getAsString();
                    double xc = wqe.apply(z, a);

                    if (xc >= 0.8) {
                        return true;
                    }
                }
            }
        }
        return false;

    }

    public boolean ee(String ae) {
        JsonObject eq = j.jj("words.json");
        JsonArray eqeq = eq.getAsJsonArray("whitelist");
        JsonArray eqd = eq.getAsJsonArray("banwords");

        L l = new L();

        ae = ae.toLowerCase();
        String[] weq = ae.split("[\\s,.;!?]+"); // Разделение на токены

        for (String x : weq) {
            boolean a = false;
            for (int i = 0; i < eqeq.size(); i++) {
                String b = eqeq.get(i).getAsString();
                double e = l.apply(x, b);
                if (e >= 0.8) {
                    a = true;

                    break;
                }
            }
            if (!a) {
                for (int i = 0; i < eqd.size(); i++) {
                    String o = eqd.get(i).getAsString();
                    double peq = l.apply(x, o);

                    if (peq >= 0.8) {
                        return true;
                    }
                }
            }
        }
        return false;

    }

    public boolean varm(String main) {
        if (oa == null || oa.length() <= 3) {
            return false;
        }
        return main.contains(oa + " замутил");

    }

    public boolean varw(String ka8) {
        if (oa == null || oa.length() <= 3) {
            return false;
        }
        return ka8.contains(oa + " предупредил");

    }

    private String rq(String o) {
        return o.replaceAll("[^a-zA-Zа-яА-Я]", "");

    }
}
