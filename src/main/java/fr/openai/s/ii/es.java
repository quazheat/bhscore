package fr.openai.s.ii;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;

public class es {
    public boolean es1(String a) {
        HttpURLConnection b = null;
        try {
            URI er = new URI(a);
            b = (HttpURLConnection) er.toURL().openConnection();
            b.setRequestMethod("HEAD");
            b.setConnectTimeout(5000);
            int oz = b.getResponseCode();
            return oz == HttpURLConnection.HTTP_OK;
        } catch (IOException | URISyntaxException e) {
            return false;
        } finally {
            if (b != null) {
                b.disconnect();
            }}}


}
