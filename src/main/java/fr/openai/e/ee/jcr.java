package fr.openai.e.ee;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class jcr {

    public void cr() {
        String n = "ignore.json";
        if (!es(n)) {
            ob(n);
        }
    }

    public JsonObject rdL(String main) {
        try (FileReader re = new FileReader(main)) {
            return JsonParser.parseReader(re).getAsJsonObject();
        } catch (IOException e) {
            e.printStackTrace();
            return new JsonObject();
        }
    }

    public void wrL(String main, JsonObject ob) {
        try (FileWriter wr = new FileWriter(main)) {
            wr.write(ob.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public JsonObject ae() {
        String w = "ignore.json";
        return rdL(w);
    }

    private boolean es(String main) {
        return java.nio.file.Files.exists(java.nio.file.Paths.get(main));
    }

    private void ob(String kr9) {
        JsonObject o = new JsonObject();
        JsonArray w = new JsonArray();
        w.add("НИКНЕЙМ");
        w.add("никнеймА");
        w.add("никнеймВ");
        o.add("ignore_words", w);

        wrL(kr9, o);
    }

}
