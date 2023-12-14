package fr.openai.n;

import fr.openai.ui.ny.cs;
import fr.openai.ui.ny.cd;
import fr.openai.ui.ny.cvf;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ae {
    private final List<ex> ax = new java.util.ArrayList<>();
    protected static final int a1 = 10;
    private final NN a2 = new NN();

    public void e(String ep, String o) {
        if (ax.size() < a1) {
            ex ax = new ex(ep, o);
            this.ax.add(ax);
            SwingUtilities.invokeLater(() -> a(ax));
        }
    }

    private void a(ex xc) {
        int cY = a2.zxcq();

        cd cy1 = new cd();
        Dimension sZ = Toolkit.getDefaultToolkit().getScreenSize();

        int x = (int) (sZ.getWidth() / 2 - cy1.getWidth() / 2);
        cy1.setLocation(x, cY);

        JPanel nfP = new JPanel();
        nfP.setLayout(new BorderLayout());
        nfP.setBackground(Color.DARK_GRAY);

        JLabel ttL = new JLabel(xc.t(), SwingConstants.CENTER);
        ttL.setFont(new Font("Serif", Font.BOLD, 18));
        ttL.setForeground(Color.BLACK);

        cvf ms = new cvf(xc.var());
        cs cs = new cs("x", cy1, a2);

        cs.addActionListener(e -> {
            cy1.dispose();
            ax.remove(xc);
        });

        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setBackground(Color.DARK_GRAY);
        cs.setPreferredSize(new Dimension((int) (0.14 * cy1.getWidth()), 18));

        buttonPanel.add(cs, BorderLayout.EAST);
        nfP.add(ttL, BorderLayout.NORTH);
        nfP.add(ms, BorderLayout.CENTER);
        nfP.add(buttonPanel, BorderLayout.SOUTH);
        cy1.add(nfP);
        cy1.setVisible(true);

        a2.xcz(20);
    }

    private record ex(String a1, String var) {
        public String t() {
            return a1;
        }
    }

}
