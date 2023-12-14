package fr.openai.ui.pp;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import fr.openai.e.ee.ii;
import fr.openai.ui.ny.cui;
import fr.openai.ui.y1;
import fr.openai.b.k;
import fr.openai.b.files.w;
import fr.openai.s.logs.u1;
import fr.openai.s.uu.manager.H;
import fr.openai.s.uu.ab;
import fr.openai.s.uu.ud;
import org.bson.Document;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;

public class o3 extends JPanel {
    private final u1 u1 = new u1();
    private final ud ud = new ud();
    private MongoClient b;
    private final JTextArea s;
    private final JButton s2;
    k k = new k();
    private final JFrame ff;

    public o3(JFrame o) {
        this.ff = o;

        setLayout(new BorderLayout());

        s = new JTextArea();
        add(new JScrollPane(s), BorderLayout.CENTER);

        s2 = new JButton("Отправить");
        s2.setEnabled(false);
        add(s2, BorderLayout.SOUTH);
        cui.cios(s2);
        s.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                a();
            }
        });

        s2.addActionListener(e -> {
            try {
                ow1();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    private void a() {
        String e = s.getText().trim();
        boolean o = e.length() >= 1;
        s2.setEnabled(o);
    }

    private void ow1() throws IOException {
        if (b == null) {
            b = k.Zg();
        }

        ab eq = new ab();
        boolean a = eq.r();

        u1.a(a);

        if (!a) {
            System.exit(1);
        }

        String tq = s.getText();
        Date oi = new Date();
        String a1 = ii.ai();

        String qwe = "BHScore";
        String qweqwe = "tickets";

        AtomicBoolean w1 = new AtomicBoolean(false);

        JButton weq = s2;
        weq.setEnabled(false);

        Thread a56 = new Thread(() -> {
            while (!w1.get()) {
                try {
                    MongoDatabase b = this.b.getDatabase(qwe);
                    MongoCollection<Document> c = b.getCollection(qweqwe);

                    w w = new w(oi, tq, H.gh(ud), a1);

                    c.insertOne(w.ww());

                    SwingUtilities.invokeLater(() -> {
                        JOptionPane.showMessageDialog(ff, "Тикет успешно создан.");
                        ff.dispose();
                        s.setText("");
                    });
                    w1.set(true);
                } catch (Exception ex) {
                    SwingUtilities.invokeLater(() -> {
                        b = k.Zg();
                        k.Zi();
                        y1 bq = new y1(ff);
                        bq.a();
                        if (bq.isB()) {
                            w1.set(true);
                        }
                    });
                }
            }
            SwingUtilities.invokeLater(() -> weq.setEnabled(true));
        });

        a56.start();
    }
}
