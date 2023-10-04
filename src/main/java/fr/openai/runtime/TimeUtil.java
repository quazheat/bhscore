package fr.openai.runtime;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {
    public static String getCurrentTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("ddHH:mm");
        Date now = new Date();
        return dateFormat.format(now);
    }
}
