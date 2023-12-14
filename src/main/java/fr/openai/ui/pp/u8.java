package fr.openai.ui.pp;

import fr.openai.b.menu.aW;
import fr.openai.b.menu.aNw;
import fr.openai.b.menu.r;
import fr.openai.b.menu.ds;
import fr.openai.s.VV;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class u8 {
    private final JFrame f;
    private final JLabel a;


    public u8(TrayIcon i) {
        VV VV = new VV();
        String c = VV.aex();

        f = new JFrame("BHScore " + c);
        f.setIconImage(i.getImage());
        f.setResizable(false);
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setSize(400, 160);

        JTabbedPane ta = new JTabbedPane();

        aNw aNw = new aNw(this);
        ds ds = new ds(this);
        b b = new b(aNw, ds);
        aW aW = new aW(this);
        r r = new r(this);
        ae ae = new ae(aW, r);
        o3 bb = new o3(f);
        aez aa = new aez();
        ow yu = new ow();

        ta.addTab("Main", aa);
        ta.addTab("Words", b);
        ta.addTab("Whitelist", ae);
        ta.addTab("Reports", bb);
        ta.addTab("Playground",yu);
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                System.out.println("ced");
            }
        });

        f.getContentPane().add(ta);
        a = new JLabel();
        a.setHorizontalAlignment(JLabel.CENTER);
        a.setForeground(Color.BLACK);
        f.add(a, BorderLayout.SOUTH);
        ta.addChangeListener(e -> {
            int a = ta.getSelectedIndex();
            if (a == 0 || a == 3) {
                cl();
            }
        });

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - f.getWidth()) / 2;
        int y = (screenSize.height - f.getHeight()) / 2;
        f.setLocation(x, y);

        f.setVisible(true);

    }

    public void a1(boolean var) {
        f.setVisible(var);
    }

    public void dpO(String message) {
        a.setText(message);
    }

    public void cl() {
        a.setText("");
    }


}
