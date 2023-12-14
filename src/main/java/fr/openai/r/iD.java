package fr.openai.r;

public class iD {
    public static boolean id(String main) {
        return main.contains("[Client thread/INFO]: [CHAT]");
    }
    public static boolean var1(String var) {
        return var.contains("[Client thread/INFO]: [STDERR]: Join to server");
    }
}
