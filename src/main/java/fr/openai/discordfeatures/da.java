package fr.openai.discordfeatures;

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRichPresence;
import fr.openai.b.upA;

public class da extends upA {
    private static final DiscordRichPresence d = new DiscordRichPresence();
    private static String d1 = "Big brother watching you";
    private static String s1 = "Looking for something...";
    private static final long st = System.currentTimeMillis() / 1000;
    boolean e = true;
    private final DiscordEventHandlers j = new DiscordEventHandlers();
    private final String var = var11();

    public void p() {
        if (!e) {
            return;
        }


        club.minnced.discord.rpc.DiscordRPC lib = club.minnced.discord.rpc.DiscordRPC.INSTANCE;
        lib.Discord_Initialize("1058737271341338655", j, true, null);

        d.details = d1;
        d.state = s1;
        d.startTimestamp = st;
        d.largeImageKey = "https://media.tenor.com/22oV0lhc-H8AAAAd/anime-girl-crazy-physco-black-and-white.gif";
        d.largeImageText = "BHScore";
        d.smallImageKey = "minigames";
        //presence.smallImageKey = "https://mc-heads.net/avatar/" + username;
        d.smallImageText = var;
        d.partySize = 0; // Number of players in the party
        d.partyMax = 0; // Maximum size of the party
        d.matchSecret = "match-secret";
        d.spectateSecret = "spectate-secret";
        d.instance = (byte) 1; // For true

        d.partyId = "party-id"; // Уникальный идентификатор группы
        d.joinSecret = "quazheat"; // Секретный ключ для присоединения к группе

        lib.Discord_UpdatePresence(d);
    }

    public void iz(String az) {
        if (!e) {
            return;
        }
        s1 = az;
        club.minnced.discord.rpc.DiscordRPC lib = club.minnced.discord.rpc.DiscordRPC.INSTANCE;
        lib.Discord_UpdatePresence(d);
    }

    public void ez(String pz) {
        if (!e) {
            return;
        }
        d1 = pz;
        club.minnced.discord.rpc.DiscordRPC lib = club.minnced.discord.rpc.DiscordRPC.INSTANCE;
        lib.Discord_UpdatePresence(d);
    }

    public void setE(boolean eba) {
        e = eba;
    }
}
