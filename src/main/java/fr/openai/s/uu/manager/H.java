package fr.openai.s.uu.manager;

import fr.openai.s.uu.ud;

import java.util.UUID;

public class H {
    public static String gh(ud ud) {
        UUID i = ud.a();

        return i != null ? i.toString().toLowerCase() : null;
    }
}
