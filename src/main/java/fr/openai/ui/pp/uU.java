package fr.openai.ui.pp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import fr.openai.b.menu.uu;
import fr.openai.e.ee.yq;
import fr.openai.ui.ap2;
import fr.openai.ui.ny.cui;
import org.bson.Document;

public class uU extends JFrame {

    private final uu uu = new uu();
    private final yq var = new yq();
    private final JTextField var2;
    private final JTextField var1;
    private final JCheckBox var3;
    private final JList<String> varvar;

    private final JPopupMenu v12;
    private final JCheckBox ao;

    public uU() {
        setTitle("User Manager");
        setSize(700, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        ImageIcon i = new ImageIcon("tray_icon.png");
        setIconImage(i.getImage());

        JPanel ia = new JPanel();
        ia.setLayout(new BorderLayout());
        setResizable(false);

        JPanel ip = new JPanel();
        ip.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JLabel rq = new JLabel("Username:");
        var2 = new JTextField();
        var2.setColumns(15);
        JLabel rqs = new JLabel("UUID:");
        var1 = new JTextField();
        var1.setColumns(25);
        var3 = new JCheckBox("Admin");
        var3.setFocusPainted(false);
        ao = new JCheckBox("Mod");
        ao.setFocusPainted(false);

        JButton oa = new JButton("Add User");
        cui.cios(oa);

        JButton wq = new JButton("Remove User");
        cui.cios(wq);
        wq.addActionListener(e -> var.aa(this, var2.getText(), var1.getText()));

        oa.addActionListener(e -> {
            boolean aT = var3.isSelected();
            boolean mD = ao.isSelected();
            var.a(this, var2.getText(), var1.getText(), aT, mD);
        });

        ip.add(rq);
        ip.add(var2);
        ip.add(rqs);
        ip.add(var1);
        ip.add(var3);
        ip.add(ao);

        JPanel a = new JPanel();
        a.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        a.add(oa);
        a.add(wq);

        varvar = new JList<>();
        varvar.setCellRenderer(new ce());
        varvar.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        varvar.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                x();
            }
        });

        v12 = new JPopupMenu();
        JMenuItem sam = new JMenuItem("Send Message");
        sam.addActionListener(e -> t());
        v12.add(sam);

        varvar.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                b(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                b(e);
            }
        });

        JScrollPane pa = new JScrollPane(varvar);
        pa.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        pa.setPreferredSize(new Dimension(400, 150));

        ia.add(ip, BorderLayout.CENTER);
        ia.add(a, BorderLayout.SOUTH);
        ia.add(pa, BorderLayout.NORTH);

        a();

        add(ia);
        setVisible(true);
    }

    private void b(MouseEvent e) {
        if (e.isPopupTrigger() && varvar.getSelectedIndex() > 0) {
            v12.show(e.getComponent(), e.getX(), e.getY());
        }
    }

    public void a() {
        DefaultListModel<String> a = new DefaultListModel<>();
        List<Document> b = uu.g();

        if (b.isEmpty()) {
            a.addElement("No users found.");
            return;
        }
        a.addElement("User List:");

        for (Document uy : b) {
            String c = uy.getString("username");
            String eq = uy.getString("uuid");

            boolean aT = uy.getBoolean("admin", false);
            boolean mD = uy.getBoolean("mod", false);


            String az = "Username: " + c +
                    ", UUID: " + eq +
                    (aT ? ", Admin" : "") +
                    (mD ? ", Mod" : "");

            var3.setSelected(aT);

            ao.setSelected(mD);
            a.addElement(az);
        }

        varvar.setModel(a);
    }



    private void t() {
        int var = varvar.getSelectedIndex();
        if (var > 0) {
            String var1 = varvar.getSelectedValue();
            if (var1 != null) {
                String var0 = a2(var1, "UUID");

                SwingUtilities.invokeLater(() -> new ap2(var0));
            }
        }
    }


    private void x() {
        int o = varvar.getSelectedIndex();
        if (o == 0) {
            varvar.clearSelection();
        } else {
            String w = varvar.getSelectedValue();
            if (w != null) {
                String[] w1 = w.split(", ");
                a1(w1);
            }
        }
    }


    private void a1(String[] a) {
        if (a.length >= 2) {
            String e = a2(a[0], "Username");
            String i = a2(a[1], "UUID");

            var2.setText(e);
            var1.setText(i);

            if (a.length == 3) {
                boolean aT = Boolean.parseBoolean(a2(a[2], "Admin"));
                var3.setSelected(aT);
            } else {
                var3.setSelected(false);
            }
        }
    }


    private String a2(String eq, String e) {
        String[] a = eq.split(", ");
        for (String b : a) {
            if (b.startsWith(e)) {
                return b.substring(b.indexOf(":") + 2);
            }
        }
        return "";
    }


    private static class ce extends DefaultListCellRenderer {
        private final DefaultListCellRenderer req = new DefaultListCellRenderer();

        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value1, int value2, boolean value13, boolean value12) {
            JLabel r = (JLabel) req.getListCellRendererComponent(list, value1, value2, value13, value12);

            if (value2 == 0) {
                r.setHorizontalAlignment(SwingConstants.CENTER);
            }
            if (value2 != 0) {
                r.setHorizontalAlignment(SwingConstants.LEFT);
            }
            r.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));
            return r;
        }
    }

}
