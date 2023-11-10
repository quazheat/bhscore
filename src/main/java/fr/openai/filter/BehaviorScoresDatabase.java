package fr.openai.filter;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import org.bson.Document;

public class BehaviorScoresDatabase {
    private final MongoCollection<Document> collection;

    public BehaviorScoresDatabase(MongoCollection<Document> collection) {
        this.collection = collection;
    }

    public void addOrUpdateScore(String offenderName) {
        int currentScore = getScore(offenderName);
        currentScore = Math.max(currentScore - 150, 0);

        collection.updateOne(Filters.eq("name", offenderName),
                new Document("$set", new Document("score", currentScore)),
                new UpdateOptions().upsert(true));
    }

    public int getScore(String offenderName) {
        Document document = collection.find(Filters.eq("name", offenderName)).first();
        return (document != null) ? document.getInteger("score", 10000) : 10000;
    }
}
