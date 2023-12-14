package fr.openai.ff.m;

import fr.openai.e.Nes;
import fr.openai.ff.jSt;
import fr.openai.n.nj;

import java.util.ArrayList;
import java.util.List;


public class bb extends ei {
    private final List<ooo> a;
    private final Nes nes = new Nes();

    public bb(nj nj) {
        super(nj);
        this.a = new ArrayList<>();
    }

    public void pc(String e, String a) {
        jSt jSt = new jSt(e, a);
        if (jSt.sssD()) {
            return;
        }
        long b = System.currentTimeMillis() / 1000;
        a(b);

        int var = var5(e, a);
        var2(e, b, a);

        if (var == 2) {
            e5(e, a);
            ok(e, a);
        }
    }

    private void a(long e) {
        a.removeIf(record -> e - record.timestamp() >= 60);
    }

    private int var5(String e, String i) {
        int var = 0;
        for (ooo record : a) {
            if (record.varN().equals(e) && record.varM().equals(i)) {
                var++;
            }
        }

        return var;

    }

    private void var2(String a, long b, String c) {
        ooo o = new ooo(a, b, c);
        this.a.add(o);
    }

    private void e5(String a, String m) {
        if ("Unknown".equalsIgnoreCase(a)) {
            return;
        }
        a = nes.e1(a);
        o1(a, m, "/warn " + a + " Не флуди", "[3msg] " + m, "2.10+");
    }

    private void ok(String a, String main) {
        this.a.removeIf(record -> record.varN().equals(a) && record.varM().equals(main));
    }

}
