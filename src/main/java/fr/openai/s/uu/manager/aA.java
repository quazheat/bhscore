package fr.openai.s.uu.manager;

import fr.openai.b.dzz;
import fr.openai.b.menu.tTt;
import fr.openai.discordfeatures.de;
import fr.openai.n.nj;
import fr.openai.s.VV;
import fr.openai.r.l;
import fr.openai.s.uu.ab;
import fr.openai.ui.re;
import fr.openai.b.upA;

public class aA extends tTt {
    private final dzz dzz = new dzz();
    private final upA upA = new upA() {
        @Override
        public String var11() {
            return super.var11();
        }
    };

    public void a1() {
        de de = new de();
        String y = (dzz.us());

        if (y == null || y.length() <= 3) {
            de.setModal(true); // MODAL DIALOG
            de.setVisible(true);
        }

        VV VV = new VV();
        nj nj = new nj();
        ab ab = new ab();

        VV.rs();
        if (ab.r()) {
            l l = new l(nj);
            l.s();

            return;
        }
        azxcA("FAILED: " + upA.var11());
        re.oa();
        System.exit(1);
    }
}
