package fr.openai.e.ee;

import fr.openai.b.menu.uu;
import fr.openai.ui.pp.uU;

import javax.swing.*;

public class yq {

    private final uu uu = new uu();

    public void a(JFrame e, String a, String o, boolean ad, boolean md) {
        if (!a.isEmpty() && !o.isEmpty()) {
            uu.zxc(a, o, ad, md);
            JOptionPane.showMessageDialog(e, String.format("%s added successfully.", a));
            s(e);
        } else {
            JOptionPane.showMessageDialog(e, "Enter username and UUID.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void aa(JFrame f, String r, String a) {
        if (!r.isEmpty()) {
            if (uu.usd(r)) {
                JOptionPane.showMessageDialog(f, String.format("%s removed successfully.", r));
                s(f);
            }
        } else if (!a.isEmpty()) {
            if (uu.awd(a)) {
                JOptionPane.showMessageDialog(f, String.format("%s removed successfully.", a));
                s(f);
            }
        } else {
            JOptionPane.showMessageDialog(f, "username or UUID mandatory", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void s(JFrame a) {
        ((uU) a).a();
    }
}