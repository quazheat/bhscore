package fr.openai.n;

import java.util.ArrayList;
import java.util.List;

import static fr.openai.n.nj.zxc;

public class NN {
    private int x = 50;
    private final List<Integer> hh = new ArrayList<>(zxc);

    public int zxcq() {
        int x = this.x;

        for (int i = hh.size() - 1; i >= 0; i--) {
            if (hh.get(i) > 0) {
                x -= hh.get(i);
                break;
            }
        }

        return x;
    }

    public void xcz(int ae) {
        x += ae;
    }

    public void sda(int i, int q) {
        if (i >= 0 && i < zxc) {
            if (i >= hh.size()) {
                hh.add(q);

                return;
            }

            hh.set(i, q);
        }
    }

    public void c(int y) {
        x = y;
    }
}
