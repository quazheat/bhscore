package fr.openai.o;

import com.mongodb.client.MongoCollection;
import fr.openai.b.k;
import fr.openai.e.ee.DD;
import org.bson.Document;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class oi {
    public final String c = "online";
    private final DD DD = new DD();
    private final JLabel ae = new JLabel();
    protected final ImageIcon a = new ImageIcon("tray_icon.png");
    public void a(ArrayList<String> i, String y) {
        JFrame az = new JFrame("Онлайн");
        az.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        az.setResizable(false);
        az.setIconImage(a.getImage());

        JPanel oz = new JPanel();
        oz.setLayout(new BoxLayout(oz, BoxLayout.Y_AXIS));

        for (String o : i) {
            JPanel ed = new JPanel();
            ed.setLayout(new FlowLayout());
            ed.setBorder(BorderFactory.createLineBorder(Color.BLACK));

            JLabel ezxc = new JLabel(o);
            ed.add(ezxc);

            if (y.length() >3 && o.contains(y)) {
                JButton ob = new JButton("X");
                ob.setFocusPainted(false);
                ob.setBackground(Color.LIGHT_GRAY);
                ob.setForeground(Color.RED);
                ob.setFont(new Font("Serif", Font.BOLD, 15));

                final JPanel fu = ed;
                ob.addActionListener(e -> {
                    MongoCollection<Document> collection = k.Zxc(c);
                    Document filter = new Document("username", y);
                    DD.dqI(collection, filter);
                    fu.setVisible(false);
                    az.revalidate();
                    az.repaint();
                });

                ed.add(ob);
            }

            oz.add(ed);
        }

        JScrollPane fe = new JScrollPane(oz);
        fe.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        az.add(fe);
        az.add(ae, BorderLayout.NORTH);
        az.pack();
        az.setLocationRelativeTo(null);
        az.setVisible(true);
    }
}
