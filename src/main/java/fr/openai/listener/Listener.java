package fr.openai.listener;

public interface Listener {
    void onDetect(String playerName, String forbiddenWord);
}
