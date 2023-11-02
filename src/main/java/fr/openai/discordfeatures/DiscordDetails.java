package fr.openai.discordfeatures;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class DiscordDetails {
    protected String randomScaryPhrase;

    public DiscordDetails() {
        List<String> scaryPhrases = new ArrayList<>();
        scaryPhrases.add("The shadows are whispering your name.");
        scaryPhrases.add("You're never truly alone.");
        scaryPhrases.add("Beware the eyes that see in the dark.");
        scaryPhrases.add("Something wicked this way comes.");
        scaryPhrases.add("Dread creeps in the silence.");
        scaryPhrases.add("The walls have ears.");
        scaryPhrases.add("Your secrets are not your own.");
        scaryPhrases.add("Paranoia is your only friend.");
        scaryPhrases.add("The night conceals more than you know.");
        scaryPhrases.add("Malevolence lurks around every corner.");
        scaryPhrases.add("Darkness is a veil for sinister intent.");
        scaryPhrases.add("Big brother watching you.");

        Collections.shuffle(scaryPhrases);

        // Get and store a random scary phrase.
        Random random = new Random();
        int index = random.nextInt(scaryPhrases.size());
        randomScaryPhrase = scaryPhrases.get(index);
        System.out.println("Scary Phrase: " + randomScaryPhrase);
    }

    // Getter for the random scary phrase
    public String getRandomScaryPhrase() {
        System.out.println("Scary Phrase: " + randomScaryPhrase);
        return randomScaryPhrase;
    }

}
