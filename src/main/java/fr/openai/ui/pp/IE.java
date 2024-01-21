package fr.openai.ui.pp;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import fr.openai.e.SP;
import fr.openai.e.ee.jcr;
import fr.openai.ui.ny.cui;

import javax.swing.*;
import java.awt.*;

public class IE extends JFrame {

    private final DefaultListModel<String> il;
    private final JList<String> iL;
    private final JTextField f;

    public IE() {
        setTitle("Ignore List Editor");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        ImageIcon icon = new ImageIcon("tray_icon.png");
        Image image = icon.getImage();
        setIconImage(image);

        il = new DefaultListModel<>();
        iL = new JList<>(il);

        JScrollPane scrollPane = new JScrollPane(iL);

        f = new JTextField(15);
        JButton a = new JButton("Add name");
        cui.cios(a);
        a.setToolTipText("Строго соблюдая регистр");
        a.addActionListener(e -> {
            String x = f.getText();
            if (x != null && !x.isEmpty()) {
                il.addElement(x);
                f.setText("");
                upIl();
            }
        });


        JButton c = new JButton("Remove name");
        cui.cios(c);
        c.addActionListener(e -> {
            int selectedIndex = iL.getSelectedIndex();
            if (selectedIndex != -1) {
                il.remove(selectedIndex);
                upIl();
            }
        });

        JButton s = new JButton("Save");
        cui.cios(s);
        s.addActionListener(e -> {
            saveIgnoreList();
            dispose();
        });

        JPanel ae = new JPanel();
        ae.setLayout(new FlowLayout());
        ae.add(new JLabel("Enter a new name: "));
        ae.add(f);

        JPanel be = new JPanel();
        be.setLayout(new FlowLayout());
        be.add(a);
        be.add(c);
        be.add(s);

        JPanel mp = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        mp.add(scrollPane, gbc);

        gbc.gridy = 1;
        gbc.weighty = 0.0;
        mp.add(ae, gbc);

        gbc.gridy = 2;
        gbc.weighty = 0.0;
        mp.add(be, gbc);

        add(mp);

        lil();
    }

    private void upIl() {
        JsonArray w = new JsonArray();
        for (int i = 0; i < il.size(); i++) {
            w.add(il.getElementAt(i));
        }

        JsonObject ob = new JsonObject();
        ob.add("ignore_words", w);

        jcr ic = new jcr();
        ic.wrL("ignore.json", ob);

        SP.up(ob.getAsJsonArray("ignore_words"));
    }

    private void lil() {
        jcr ic = new jcr();
        ic.cr();

        JsonObject ob = ic.ae();
        JsonArray w = ob.getAsJsonArray("ignore_words");

        for (int i = 0; i < w.size(); i++) {
            il.addElement(w.get(i).getAsString());
        }
    }

    private void saveIgnoreList() {
        JsonObject ignoreObject = new JsonObject();
        JsonArray ignoreWords = new JsonArray();

        for (int i = 0; i < il.size(); i++) {
            ignoreWords.add(il.getElementAt(i));
        }

        ignoreObject.add("ignore_words", ignoreWords);

        jcr jcr = new jcr();
        jcr.wrL("ignore.json", ignoreObject);
    }

}
