package fr.openai.b.files;

import java.util.Date;
import org.bson.Document;

public record w(Date x, String c, String zQ, String zW) {
    public Document ww() {
        return new Document()
                .append("a22", x)
                .append("problem_text", c)
                .append("sender_uuid", zQ)
                .append("ip_address", zW);
    }
}
