package fr.openai.s.uu;

import fr.openai.b.menu.uu;
import fr.openai.s.lg.u1;
import fr.openai.s.uu.manager.H;

import java.io.IOException;
import java.util.List;

public class ab {
    private final ud ud = new ud();
    private final u1 u1 = new u1();
    private final uu uu = new uu();

    public boolean r() {
        String r = H.gh(ud);
        return r != null && a(r);
    }

    private boolean a(String main) {
        try {
            List<String> o1 = uu.gG();

            boolean a1 = o1.contains(main);
            u1.a(a1);

            return a1;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}
