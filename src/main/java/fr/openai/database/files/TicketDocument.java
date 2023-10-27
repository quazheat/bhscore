package fr.openai.database.files;

import java.util.Date;
import org.bson.Document;

public record TicketDocument(Date timestamp, String problemText, String senderUuid, String ipAddress) {

    public Document toDocument() {
        return new Document()
                .append("timestamp", timestamp)
                .append("problem_text", problemText)
                .append("sender_uuid", senderUuid)
                .append("ip_address", ipAddress);
    }
}
