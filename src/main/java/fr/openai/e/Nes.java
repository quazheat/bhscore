package fr.openai.e;

import fr.openai.o.YY;

import java.util.regex.Pattern;

public class Nes {
    public static boolean s = false;
    private String f;
    private final Pattern SQUARE_BRACKETS_PATTERN = Pattern.compile("\\[.+?]");
    private final Pattern BRACKETS_PATTERN = Pattern.compile("\\(.+?\\)");
    private final Pattern HASHTAG_PATTERN = Pattern.compile("#.*");
    private final Pattern PIPE_PATTERN = Pattern.compile(".+?┃");

    public String e1(String varP) {
        String[] a = varP.split(" ");
        if (a.length >= 2) {
            if (!s && !YY.cs &&  a.length >= 3) {
                a[1] = a[2]; // 2=3
            } else {
                a[1] = ""; // 2=0
            }
            varP = String.join(" ", a).trim(); // all = 1
        }
        if (s && a.length >= 2) {
            if (a.length >= 3) {
                a[2] = a[1]; // 3=2
            } else {
                a[1] = ""; // 2=0
            }
            varP = String.join(" ", a).trim(); // all = 1
        }
        if (YY.cs && a.length >= 2) {
            if (a.length >= 3) {
                a[1] = a[2]; //2=3
            } else {
                a[0] = ""; //2=0
            }
            varP = String.join(" ", a).trim(); // all = 1
        }

        System.out.println(varP);
        return varP;
    }


    public String gp(String e) {
        if (e.contains("»")) {
            int a = e.indexOf("[Client thread/INFO]: [CHAT]") + "[Client thread/INFO]: [CHAT]".length();
            int a0 = e.indexOf("»");
            if (a0 != -1) {
                String gN = e.substring(a, a0).trim();
                f = fN(gN);
                return ao();
            }
        } else {
            int i = e.indexOf("[Client thread/INFO]: [CHAT]") + "[Client thread/INFO]: [CHAT]".length();
            int o = e.indexOf(":", i);
            if (o != -1) {
                String e1 = e.substring(i, o).trim();
                f = fN(e1);
                return ao();
            }
        }
        return "Unknown";
    }

    private String fN(String a) {
        a = BRACKETS_PATTERN.matcher(a).replaceAll("");
        a = SQUARE_BRACKETS_PATTERN.matcher(a).replaceAll("");
        a = HASHTAG_PATTERN.matcher(a).replaceAll("");
        a = PIPE_PATTERN.matcher(a).replaceAll("");
        a = a.trim();
        return a;
    }

    private String ao() {
        f = f.replaceAll(".*?\\|", "").trim();
        f = f.replaceAll("(?i)Хаб", "").trim();
        if (f.length() <= 3) {
            return "Unknown";
        }
        return f;
    }


}
