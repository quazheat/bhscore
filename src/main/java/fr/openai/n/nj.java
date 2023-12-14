package fr.openai.n;

import fr.openai.e.ee.yt;
import fr.openai.ui.ny.cs;
import fr.openai.ui.ny.cd;
import fr.openai.ui.ny.cvf;
import fr.openai.ff.fixer.nf;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.*;
import java.util.List;

public class nj {
    protected final yt io = new yt() {
        @Override
        public void eu() {
            super.eu();
        }
    };

    private final nf f = new nf();
    private final List<vare> vares = new ArrayList<>();
    protected static final int zxc = 10;
    private final NN n = new NN();

    public void ft(String var1, String var2) {
        System.out.println("Trying to ft");
        if (vares.size() < zxc) {
            vare vare = new vare(var1, var2);
            vares.add(vare);
            SwingUtilities.invokeLater(() -> d(vare));
        }
    }

    private void d(vare vare) {
        System.out.println("Nt");
        int a = n.zxcq();

        cd b = new cd();

        Dimension eq = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) (eq.getWidth() - b.getWidth());
        int y = (int) (eq.getHeight() - b.getHeight() - a);
        b.setLocation(x, y);

        JPanel op = new JPanel();
        op.setLayout(new BorderLayout());
        op.setBackground(Color.DARK_GRAY);

        JLabel eqz = new JLabel(vare.vara(), SwingConstants.CENTER);
        eqz.setFont(new Font("Serif", Font.BOLD, 18));
        eqz.setForeground(Color.BLACK);

        cvf eqsd = new cvf(vare.varo());
        cs cb = new cs("x", b, n);

        cb.addActionListener(e -> {
            b.dispose();
            vares.remove(vare);
        });

        JButton vars = new JButton("Mute");
        JButton gar = new JButton("Warn");
        vars.setBackground(new Color(29, 29, 29));
        vars.setForeground(Color.WHITE);
        vars.setFocusPainted(false);

        gar.setBackground(new Color(29, 29, 29));
        gar.setForeground(Color.WHITE);
        gar.setFocusPainted(false);

        vars.addActionListener(e -> {
            String ss = f.cs(eqz.getText());
            String dw8 = "/mute " + ss + "  ";
            n.xcz(-20);
            b.dispose();
            Clipboard es = Toolkit.getDefaultToolkit().getSystemClipboard();
            es.setContents(new StringSelection(dw8), null);
            io.eu();
            vares.remove(vare);
        });

        gar.addActionListener(e -> {
            String ss = f.cs(eqz.getText());
            String aa = "/warn " + ss + "  ";
            n.xcz(-20);
            b.dispose();
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(new StringSelection(aa), null);
            io.eu();
            vares.remove(vare);
        });


        JPanel tq = new JPanel(new BorderLayout());
        vars.setPreferredSize(new Dimension((int) (0.43 * b.getWidth()), 18));
        gar.setPreferredSize(new Dimension((int) (0.43 * b.getWidth()), 18));
        cb.setPreferredSize(new Dimension((int) (0.14 * b.getWidth()), 18));

        tq.add(vars, BorderLayout.WEST);
        tq.add(gar, BorderLayout.CENTER);
        tq.add(cb, BorderLayout.EAST);
        op.add(eqz, BorderLayout.NORTH);
        op.add(eqsd, BorderLayout.CENTER);
        op.add(tq, BorderLayout.SOUTH);
        b.add(op);
        b.setVisible(true);

        n.xcz(20);
    }

}
