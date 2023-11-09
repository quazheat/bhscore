package fr.openai.database.menu;

import com.mongodb.client.MongoCollection;
import fr.openai.exec.utils.IpAddressUtil;
import fr.openai.database.ConnectDb;
import fr.openai.database.files.TicketDocument;
import fr.openai.starter.uuid.UuidProvider;
import fr.openai.starter.uuid.manager.HwidManager;
import org.bson.Document;

import java.util.Date;

public abstract class SendTicket {
    UuidProvider uuidProvider = new UuidProvider();

    protected void sendTicket(String ticketText) {
        TicketDocument ticketDocument = new TicketDocument(new Date(), ticketText, HwidManager.getHwid(uuidProvider), IpAddressUtil.getUserPublicIpAddress());
        MongoCollection<Document> ticketCollection = ConnectDb.getMongoCollection("tickets");
        ticketCollection.insertOne(ticketDocument.toDocument());
    }
}
