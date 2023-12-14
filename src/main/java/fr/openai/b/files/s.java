package fr.openai.b.files;

import fr.openai.ff.Bst;
import fr.openai.n.W;

import java.awt.*;

public class s {
    private final TrayIcon a;
    private final Image u;
    private final Image uU;
    private final Image aU;

    public s(TrayIcon a, Image b, Image c, Image v) {
        this.a = a;
        this.u = b;
        this.uU = c;
        this.aU = v;
    }

    public void xc(CheckboxMenuItem pP, CheckboxMenuItem vP) {
        pP.addItemListener(e -> {
            boolean nS = pP.getState();
            Bst.est(nS);

            if (nS) {
                W.dqps();
                a.setImage(u);
                vP.setEnabled(false);

                return;
            }
            W.sdq();
            a.setImage(aU);
            vP.setEnabled(true);

        });

        vP.addItemListener(e -> {
            boolean x = vP.getState();
            Bst.esz(x);

            if (x) {
                W.dqps();
                a.setImage(uU);
                pP.setEnabled(false);

                return;
            }
            W.sdq();
            if (Bst.efz()) {
                a.setImage(u);

                return;
            }
            a.setImage(aU);
            pP.setEnabled(true);
        });
    }

}
