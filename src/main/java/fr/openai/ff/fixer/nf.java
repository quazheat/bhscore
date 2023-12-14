package fr.openai.ff.fixer;

public class nf {

    public String cs(String ui) {
        String[] a = ui.split(" ");

        if (a.length >= 2) {
            a[1] = (a.length >= 3) ? a[2] : ""; // string 2 = 3 or 2 = 0 (<3)
            ui = String.join(" ", a).trim(); // 1 2 3 = 1
        }

        return ui;
    }

}
