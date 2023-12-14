package fr.openai.rr;

import com.mongodb.client.MongoCollection;
import fr.openai.b.sss;
import fr.openai.b.k;
import fr.openai.b.upA;
import fr.openai.b.files.aa;
import fr.openai.b.files.s;
import fr.openai.b.menu.uu;
import fr.openai.e.ee.DD;
import fr.openai.ui.ik;
import fr.openai.ui.pp.u8;
import org.bson.Document;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ao extends upA {
    private u8 m;
    public final String a = "online";
    private final k k = new k();
    private final DD DD = new DD();
    aa oo = new aa();
    private final String eea = var11();
    private final Image erq = oo.aA();

    public void aur() {

        if (SystemTray.isSupported()) {
            MenuItem mn = new MenuItem("menu");
            MenuItem ms = new MenuItem("my stats");
            MenuItem cs = new MenuItem("hide");
            MenuItem ex = new MenuItem("exit program");

            java.awt.Menu ae1 = new java.awt.Menu("Modes");
            CheckboxMenuItem ae2 = new CheckboxMenuItem("Rage Mode");
            CheckboxMenuItem ae3 = new CheckboxMenuItem("Loyal Mode");
            ae1.add(ae2);
            ae1.add(ae3);
            ae1.add(ae2);
            ae1.add(ae3);

            PopupMenu ozxc = new PopupMenu();
            ozxc.add(ae1);
            ozxc.add(mn);
            ozxc.add(ms);
            ozxc.addSeparator();
            ozxc.add(cs);
            ozxc.add(ex);

            TrayIcon i = new TrayIcon(erq, "BHScore", ozxc);
            SystemTray t = SystemTray.getSystemTray();

            Image s1 = oo.dAA();
            Image s2 = oo.zxc();
            Image s4 = oo.aA();

            try {
                t.add(i);
            } catch (AWTException e) {
                e.printStackTrace();
            }

            if (!uu.m) {
                ae1.setEnabled(false);
                ozxc.remove(ae1);
            }

            if (eea == null || eea.length() <= 3) {
                ozxc.remove(ms);
            }
            ms.addActionListener(e -> {
                ik var = new ik();
                var.setModal(true);
                var.setVisible(true);

            });


            mn.addActionListener(e -> {
                if (m == null) {
                    m = new u8(i);
                    return;
                }
                m.a1(true);
            });

            i.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() == 2) {
                        if (m == null) {
                            m = new u8(i);
                        }
                        m.a1(true);
                    }
                }
            });

            ex.addActionListener(e -> {
                k.Zi();
                try {
                    Thread.sleep(1250);
                } catch (InterruptedException exception) {
                    exception.printStackTrace();
                    System.exit(1);
                }

                MongoCollection<Document> c = fr.openai.b.k.Zxc(a);

                ao.sa(eea);

                DD.dqI(c, new Document("username", eea));
                k.dqzxc();
                System.exit(0);
            });

            s ix = new s(i, s1, s2, s4);
            ix.xc(ae2, ae3);
        }
        oo.asZd(true);
    }

    private static void sa(String var) {
        sss sss = new sss();
        sss.ap(var);
    }
}
