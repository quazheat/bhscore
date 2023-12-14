package fr.openai.ff.m;

import fr.openai.discordfeatures.da;
import fr.openai.discordfeatures.sda;
import fr.openai.e.o13;
import fr.openai.e.Nes;
import fr.openai.ff.f;
import fr.openai.ff.jSt;
import fr.openai.n.nj;

public class xc extends ei {
    private final da da;
    public static int a = 0;
    public static int nea = 0;
    private final f f;
    sda sda;
    private final Nes nes;


    public xc(nj nj) {
        super(nj);
        this.da = new da();
        this.nes = new Nes();
        this.f = new f();
    }

    public void ok(String abs, String e111) {
        final String var = "/warn ";
        final String var1 = "/mute ";
        final String var3 = "+";
        final String var2 = "2.";
        final String var12 = "2.12";
        final String var232 = "2.10";
        final String bw = "2.4";
        final String sp = " ";
        final String specText = "Spectating ";
        String a = o13.gm(e111);
        String nea = nes.e1(abs);
        if (a == null) {
            return;
        }

        o(a);
        jSt jSt = new jSt(abs, a);
        if (jSt.sssD()) {
            return;
        }

        boolean e = (f.var(a) || f.h(a) || f.fw(a));

        if (e || f.aa(a) || f.e(a) || f.ee(a)) {
            String e1 = specText + nea;

            da.iz(e1);
        }

        if (e && f.aa(a) && f.e(a) && f.ee(a)) {
            o1(nea, a, var1 + nea + " 3.7", a,
                    "3.7");
            sda.sdaP();
            return;
        }

        if (e && f.aa(a) && f.e(a)) {
            o1(nea, a, var1 + nea + sp + var12 + var3 + var232 + var3 + var2, a,
                    var12 + var3 + var232 + var3 + var2);
            sda.sdaP();
            return;
        }

        if (e && f.aa(a) && f.ee(a)) {
            o1(nea, a, var1 + nea + sp + var232 + var3 + var12 + var3 + bw, a,
                    var232 + var3 + var12 + var3 + bw + var3);
            return;
        }

        if (f.ee(a) && f.e(a)) {
            o1(nea, a, var1 + nea + sp + bw + var232 + var2, a,
                    bw + var3 + var2);
            return;
        }

        if (e && f.e(a)) {
            o1(nea, a, var1 + nea + sp + var232 + var3 + var2, a,
                    var232 + var3 + var2);
            return;
        }

        if (f.aa(a) && f.e(a)) {
            o1(nea, a, var1 + nea + sp + var12 + var3 + var2, a,
                    var12 + var3 + var2);
            return;
        }

        if (e && f.ee(a)) {
            o1(nea, a, var1 + nea + sp + var232 + var3 + bw, a,
                    var232 + var3 + bw + var3);
            return;
        }

        if (f.aa(a) && f.ee(a)) {
            o1(nea, a, var1 + nea + sp + var12 + var3 + bw, a,
                    var12 + var3 + bw + var3);
            return;
        }

        if (e && f.aa(a)) {
            o1(nea, a, var1 + nea + sp + var12 + var3 + var232, a,
                    var12 + var3 + var232 + var3);
            return;
        }

        if (f.e(a)) {
            o1(nea, a, var + nea + " Не ругайся", a, var2);
            return;
        }

        if (f.ee(a)) {
            o1(nea, a, var + nea + " Без банвордов", a,
                    bw + var3);
            return;
        }

        if (f.var(a) || f.h(a) || f.fw(a)) {
            o1(nea, a, var + nea + " Не флуди", a,
                    var232 + var3);
            return;
        }

        if (f.aa(a)) {
            o1(nea, a, var + nea + " Не капси", a,
                    var12 + var3);
        }

    }

    private void o(String o52) {
        if (f.varm(o52)) {
            nea++;
        }

        if (f.varw(o52)) {
            a++;
        }
    }

    public static int gv() {
        return a;
    }

    public static int gk() {
        return nea;
    }

}
