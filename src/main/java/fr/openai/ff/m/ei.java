package fr.openai.ff.m;

import fr.openai.e.ee.o4;
import fr.openai.e.ee.yt;
import fr.openai.ff.Bst;
import fr.openai.n.nj;
import fr.openai.n.W;
import fr.openai.o.YY;

import static java.awt.TrayIcon.MessageType.ERROR;
import static java.awt.TrayIcon.MessageType.INFO;

public abstract class ei {
    protected final yt yt = new yt() {
        @Override
        public void eu() {
            super.eu();
        }
    };
    protected final nj nj;

    public ei(nj nj) {
        this.nj = nj;
    }

    void o1(String a, String a1, String b, String b1, String r1) {
        System.out.println(YY.h);

        if (ei.gc && !YY.h) {
            r1 = "gc+" + r1;

        }

        if (ei.gc && b.contains("+") && !YY.h) {
            b = b + "+gc";
        }


        if (YY.h) {
            r1 = "vc+" + r1;

        }

        if (YY.h && b.contains("+")) {
            b = b + "+vc";
        }

        if (Bst.ao()) {
            aee(b1);

            e1(b);
            yt.eu();
            return;
        }

        if (!Bst.efz()) {
            nj.ft(a, b1);
            System.out.println(a);
            return;
        }

        op(a1);
        e1("/mute " + a + " " + r1);
        yt.eu();
    }

    private String s() {
        if (Bst.ao()) {
            return "LOYAL";
        } else if (Bst.efz()) {
            return "RAGE";
        } else {
            return "";
        }
    }

    private void aee(String s) {
        W.sW(s(), s, INFO);
    }

    private void op(String s) {
        W.sW(s(), s, ERROR);
    }

    private void e1(String s) {
        o4.a4(s);
    }

    public static boolean gc = false;

    public static void setE(boolean eba) {
        gc = eba;
    }

}


