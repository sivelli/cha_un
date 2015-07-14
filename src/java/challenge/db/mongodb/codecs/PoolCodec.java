/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package challenge.db.mongodb.codecs;

import challenge.db.mongodb.SessionMongoDB;
import challenge.entities.Pool;
import challenge.entities.Question;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.bson.BsonObjectId;
import org.bson.BsonReader;
import org.bson.BsonValue;
import org.bson.BsonWriter;
import org.bson.Document;
import org.bson.codecs.Codec;
import org.bson.codecs.CollectibleCodec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.DocumentCodec;
import org.bson.codecs.EncoderContext;
import org.bson.types.ObjectId;

/**
 *
 * @author sergio.sivelli
 */
public class PoolCodec implements CollectibleCodec<Pool> {

    private final Codec<Document> documentCodec;
    private final SessionMongoDB session;

    public PoolCodec(SessionMongoDB session) {
        this.documentCodec = new DocumentCodec();
        this.session = session;
    }

    public PoolCodec(Codec<Document> documentCodec, SessionMongoDB session) {
        this.documentCodec = documentCodec;
        this.session = session;
    }

    @Override
    public Pool generateIdIfAbsentFromDocument(Pool pool) {
        pool.setId(new ObjectId().toHexString());
        return pool;
    }

    @Override
    public boolean documentHasId(Pool pool) {
        return pool.getId() != null;
    }

    @Override
    public BsonValue getDocumentId(Pool pool) {
        return new BsonObjectId(new ObjectId(pool.getId()));
    }

    @Override
    public void encode(BsonWriter writer, Pool pool, EncoderContext ec) {
        Document document = new Document();
 
        String id = pool.getId();
        Date date = pool.getDate();
        Boolean active = pool.isActive();
        List<Question> questions = pool.getQuestions();
        if (id != null) {
            document.put("_id", new ObjectId(id));
        }
        if (date != null) {
            document.put("date", date);
        }
        document.put("active", active);
        if (questions != null) {
            List<ObjectId> listIds = new ArrayList<>(questions.size());
            for (Question q: questions) {
                listIds.add(new ObjectId(q.getId()));
            }
            document.put("questions", listIds);
        }
        documentCodec.encode(writer, document, ec);
    }

    @Override
    public Class<Pool> getEncoderClass() {
        return Pool.class;
    }

    @Override
    public Pool decode(BsonReader reader, DecoderContext dc) {
        Document document = documentCodec.decode(reader, dc);
        Pool pool = new Pool();
        ObjectId id = (ObjectId) document.get("_id");
        pool.setId(id.toHexString());
        Date date = (Date) document.get("date");
        pool.setDate(date);
        Boolean active = (Boolean) document.get("active");
        pool.setActive(active == null? false : active);
        List<ObjectId> listIds = (List<ObjectId>) document.get("questions");
        List<Question> questions = null;
        if (listIds != null && !listIds.isEmpty()) {
            questions = new ArrayList<>(listIds.size());
            for (ObjectId idQ: listIds) {
                Question newQuestion = new Question(idQ.toHexString());
                questions.add(newQuestion);
                newQuestion.needFetch(session);
            }
        }
        pool.setQuestions(questions);
        return pool;
    }
}
