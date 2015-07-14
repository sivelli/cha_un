/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package challenge.db.mongodb.codecs;

import challenge.db.mongodb.SessionMongoDB;
import challenge.entities.Question;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
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

import static com.mongodb.client.model.Filters.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
/**
 *
 * @author sergio.sivelli
 */
public class QuestionCodec implements CollectibleCodec<Question> {

    private final Codec<Document> documentCodec;
    private final SessionMongoDB session;

    public QuestionCodec(SessionMongoDB session) {
        this.documentCodec = new DocumentCodec();
        this.session = session;
    }

    public QuestionCodec(Codec<Document> documentCodec, SessionMongoDB session) {
        this.documentCodec = documentCodec;
        this.session = session;
    }
 
    @Override
    public Question generateIdIfAbsentFromDocument(Question q) {
        q.setId(new ObjectId().toHexString());
        return q;
    }

    @Override
    public boolean documentHasId(Question q) {
        return q.getId() != null;
    }

    @Override
    public BsonValue getDocumentId(Question q) {
        return new BsonObjectId(new ObjectId(q.getId()));
    }

    @Override
    public void encode(BsonWriter writer, Question q, EncoderContext ec) {
        Document document = new Document();
 
        String id = q.getId();
        String question = q.getQuestion();
        List<String> alternatives = q.getAlternatives();
        if (id != null) {
            document.put("_id", new ObjectId(id));
        }
        if (question != null) {
            document.put("question", question);
        }
        if (alternatives != null) {
            document.put("alternatives", alternatives);
        }
        documentCodec.encode(writer, document, ec);
    }

    @Override
    public Class<Question> getEncoderClass() {
        return Question.class;
    }

    @Override
    public Question decode(BsonReader reader, DecoderContext dc) {
        Document document = documentCodec.decode(reader, dc);
        Question q = new Question();
        ObjectId id = (ObjectId) document.get("_id");
        q.setId(id.toHexString());
        String question = (String) document.get("question");
        q.setQuestion(question);
        List<String> listAlternatives = (List<String>) document.get("alternatives");
        q.setAlternatives(listAlternatives);
        return q;
    }
    
    public static boolean getById(Question question, MongoCollection<Question> col) {
        if (question.isById()) {
            Question q = col.find(eq("_id", new ObjectId(question.getId()))).first();
            if (q != null) {
                question.load(q);
            }
            else {
                return false;
            }
        }
        return true;
    }

    public static int getById(List<Question> questions, MongoCollection<Question> col) {
        int num = 0;
        if (questions != null && !questions.isEmpty()) {
            List<ObjectId> listId = new ArrayList<>(questions.size());
            Map<ObjectId, Question> map = new HashMap<>(questions.size());
            for (Question q: questions) {
                if (q.isById()) {
                    ObjectId id = new ObjectId(q.getId());
                    listId.add(id);
                    map.put(id, q);
                }
            }
            if (!listId.isEmpty()) {
                FindIterable<Question> itQ = col.find(in("_id", listId));
                for(Question q : itQ) {
                    ObjectId id = new ObjectId(q.getId());
                    Question question = map.get(id);
                    if (question != null) {
                        question.load(q);
                        num++;
                    }
                }
            }
        }
        return num;
    }
}
