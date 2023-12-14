package fr.openai.ui;

import com.mongodb.client.MongoCollection;
import fr.openai.b.k;
import fr.openai.b.upA;
import org.bson.Document;
import fr.openai.ui.ny.cui;

import javax.swing.*;
import java.awt.*;

public class ap2 extends JFrame {

    private final upA upA = new upA() {
        @Override
        public String var11() {
            return super.var11();
        }
    };
    private final MongoCollection<Document> c = k.Zxc("messager");

    private final JTextField eq;
    private final JTextField a;

    public ap2(String a) {
        setTitle("Message Sender");
        setSize(440, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.BOTH;

        JLabel aeq = new JLabel("Message Sender");
        aeq.setHorizontalAlignment(JLabel.CENTER);
        aeq.setFont(new Font("Arial", Font.BOLD, 20));
        aeq.setForeground(Color.BLACK);

        JLabel ope = new JLabel("UUID:");
        eq = new JTextField();
        eq.setColumns(24);
        eq.setEditable(false);

        JLabel ppe1 = new JLabel("Message:");
        this.a = new JTextField();
        this.a.setColumns(15);

        JButton d = new JButton("Send");
        d.setUI(new cui());
        d.addActionListener(e -> s());

        gbc.insets = new Insets(15, 15, 15, 15);
        panel.add(aeq, gbc);

        gbc.gridy = 1;
        panel.add(ope, gbc);

        gbc.gridy = 2;
        panel.add(eq, gbc);

        gbc.gridy = 3;
        panel.add(ppe1, gbc);

        gbc.gridy = 4;
        panel.add(this.a, gbc);

        gbc.gridy = 5;
        gbc.insets = new Insets(20, 15, 30, 15);
        panel.add(d, gbc);

        ImageIcon i = new ImageIcon("tray_icon.png");
        setIconImage(i.getImage());

        Font s1 = new Font("Arial", Font.PLAIN, 16);
        aeq.setFont(s1);
        ope.setFont(s1);
        ppe1.setFont(s1);
        eq.setFont(s1);
        this.a.setFont(s1);
        d.setFont(s1);

        if (a != null && !a.isEmpty()) {
            eq.setText(a);
        } else {
            eq.setText("UUID and varM cannot be empty");
        }

        add(panel);
        setVisible(true);
    }

    private void s() {
        String u = eq.getText();
        String e = a.getText();
        String a = upA.var11();

        if (!u.isEmpty() && !e.isEmpty()) {
            Document d = new Document();
            d.put("UUID", u);
            d.put("senderName", a);
            d.put("varM", e);
            d.put("Actual", true);

            c.insertOne(d);

            this.a.setText("");

            dispose();
            return;
        }

        JOptionPane.showMessageDialog(this, "UUID and varM cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);

    }
}
