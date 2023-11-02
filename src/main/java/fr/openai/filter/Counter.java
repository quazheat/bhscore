package fr.openai.filter;

import fr.openai.database.customui.DiscordRPC;
import fr.openai.exec.Messages;

public class Counter {
    private final Filters filters;


    private int mutes = 0;
    private int warns = 0;

    public Counter() {
        this.filters = new Filters();
    }

    public void counter(String line) {

        String message = Messages.getMessage(line);
        assert message != null;
        if (filters.hasMuteCounter(message)) {
            mutes++; // Increment the mutes counter
        } else if (filters.hasWarnCounter(message)) {
            warns++; // Increment the warns counter

        }
        String newState = mutes + " mutes | " + warns + " warns performed.";
        DiscordRPC.updateRPCState(newState);
        DiscordRPC.updateRPC();
    }

    public int getMutes() {
        return mutes;
    }

    public int getWarns() {
        System.out.println(warns);
        return warns;
    }

    public void setMutes(int mutes) {
        this.mutes = mutes;
    }

    public void setWarns(int warns) {
        this.warns = warns;
    }

}
