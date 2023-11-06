package fr.openai.database.menu;

import com.mongodb.client.MongoCollection;
import fr.openai.database.IpAddressUtil;
import fr.openai.database.ConnectDb;
import fr.openai.database.files.TicketDocument;
import org.bson.Document;
import java.util.Date;  

public abstract class SendTicket {
    protected void sendTicket(String ticketText) {
        TicketDocument ticketDocument = new TicketDocument(new Date(), ticketText, "UNKNOWN", IpAddressUtil.getUserPublicIpAddress());
        MongoCollection<Document> ticketCollection = ConnectDb.getMongoCollection("tickets");
        ticketCollection.insertOne(ticketDocument.toDocument());
    }
}
