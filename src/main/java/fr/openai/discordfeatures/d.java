package fr.openai.discordfeatures;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class d {
    Random random = new Random();
    protected String e;

    public d() {
        List<String> sp = new ArrayList<>();
        sp.add("The shadows are whispering your name.");
        sp.add("You're never truly alone.");
        sp.add("Beware the eyes that see in the dark.");
        sp.add("Something wicked this way comes.");
        sp.add("Dread creeps in the silence.");
        sp.add("The walls have ears.");
        sp.add("Your secrets are not your own.");
        sp.add("Paranoia is your only friend.");
        sp.add("The night conceals more than you know.");
        sp.add("Malevolence lurks around every corner.");
        sp.add("Darkness is a veil for sinister intent.");
        sp.add("Big brother watching you.");
        Collections.shuffle(sp);

        int i = random.nextInt(sp.size());
        e = sp.get(i);
    }

    public String getE() {
        return e;
    }
}
