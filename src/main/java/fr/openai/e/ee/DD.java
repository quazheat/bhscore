package fr.openai.e.ee;

import com.mongodb.client.MongoCollection;
import org.bson.Document;

public class DD {
    public void dqI(MongoCollection<Document> op, Document f) {
        op.deleteMany(f);
    }
}
