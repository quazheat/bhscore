package fr.openai.reader;

public class Readable {
    public static boolean check(String line) {
        return line.contains("[Client thread/INFO]: [CHAT]");
    }
    public static boolean checkServer(String line) {
        return line.contains("[Client thread/INFO]: [STDERR]: Join to server");
    }
}
