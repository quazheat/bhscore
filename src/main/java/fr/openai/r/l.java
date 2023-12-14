package fr.openai.r;

import fr.openai.b.k;
import fr.openai.b.files.a;
import fr.openai.b.menu.uu;
import fr.openai.discordfeatures.da;
import fr.openai.discordfeatures.de;
import fr.openai.e.o5;
import fr.openai.e.Nes;
import fr.openai.n.mm;
import fr.openai.n.nj;
import fr.openai.o.YY;
import fr.openai.rr.ao;
import fr.openai.b.dzz;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class l {
    private final YY YY = new YY();
    private final dzz dzz;
    private final a a = new a();
    private final o5 ex;
    private final Nes nes;
    private long s;

    public l(nj nj) {
        this.dzz = new dzz();
        this.ex = new o5(nj);
        this.nes = new Nes();
    }

    public void s() {
        final k k = new k();
        k.Zi();
        mm mm = new mm();
        uu uu = new uu();
        uu.dqw();
        da da = new da();
        de de = new de();
        String y = (dzz.us());

        if (y == null || y.length() <= 3) {
            de.setModal(true); // MODAL DIALOG
            de.setVisible(true);
        }

        ScheduledExecutorService excz = Executors.newSingleThreadScheduledExecutor();

        a.aa();

        excz.scheduleAtFixedRate(da::p, 0, 1, TimeUnit.SECONDS);

        ao aer = new ao();
        aer.aur();
        Runnable t = this::av;
        int initialDelay = 0; // Start immediately
        int period = dzz.bq(); // Set the period to the desired frequency
        s = g();
        excz.scheduleAtFixedRate(t, initialDelay, period, TimeUnit.MILLISECONDS);
        excz.scheduleAtFixedRate(mm::zx, 0, 60, TimeUnit.SECONDS);
    }

    private void av() {
        String o = dzz.bb();
        long a = g();

        if (a > s) {
            rq(s, o);
            s = a;
        }
    }

    private long g() {
        try {
            String a = dzz.bb();
            Path io = Paths.get(a);
            return Files.size(io);
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }

    private void rq(long a, String b) {

        try (RandomAccessFile raf = new RandomAccessFile(b, "r")) {
            raf.seek(a);
            String main;
            while ((main = raf.readLine()) != null) {
                ae(main);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ae(String var) throws IOException {
        var = new String(var.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);

        if (iD.id(var)) {
            ex.execute(var, nes);
        }

        if (iD.var1(var)) {
            YY.a(var);
        }
    }
}