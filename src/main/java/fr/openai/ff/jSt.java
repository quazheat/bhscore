package fr.openai.ff;

public class jSt {

    boolean sssD;

    public jSt(String j, String a) {
        sssD = a.contains("㰳")
                || a.contains("Ставки выиграли:")
                || a.contains("по причине:")
                || a.contains("\\[CHAT]\\s{2,}")
                || a.contains("Ошибка OpenGL:")
                || a.contains(". Причина:")
                || "Unknown".equalsIgnoreCase(j);
    }

    public boolean sssD() {
        return sssD;
    }

}
