package fr.openai.e;

import fr.openai.ff.m.xc;
import fr.openai.ff.m.bb;
import fr.openai.ff.vV;
import fr.openai.n.nj;

import java.util.concurrent.*;

public class o5 {

    private final xc e5;
    private final ScheduledExecutorService a;

    private final bb b;

    public o5(nj nj) {
        this.e5 = new xc(nj);
        this.b = new bb(nj);
        this.a = Executors.newScheduledThreadPool(10);
    }

    public void e3(String ma, Nes nes) {
        if (vV.vv(ma)) {
            return;
        }

        String varP = nes.gp(ma);
        String varM = o13.gm(ma);
        if (varM == null) {
            return;
        }
        System.out.println(varM);
        a.submit(() -> {
            e5.ok(varP, varM);
            b.pc(varP, varM);
        });
    }
}
