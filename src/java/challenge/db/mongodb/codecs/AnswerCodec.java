/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package challenge.db.mongodb.codecs;

import challenge.db.mongodb.SessionMongoDB;
import challenge.entities.Answer;
import challenge.entities.Question;
import challenge.entities.User;
import java.util.Date;
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
public class AnswerCodec implements CollectibleCodec<Answer> {

    private final Codec<Document> documentCodec;
    private final SessionMongoDB session;

    public AnswerCodec(SessionMongoDB session) {
        this.documentCodec = new DocumentCodec();
        this.session = session;
    }

    public AnswerCodec(Codec<Document> documentCodec, SessionMongoDB session) {
        this.documentCodec = documentCodec;
        this.session = session;
    }

    @Override
    public Answer generateIdIfAbsentFromDocument(Answer answer) {
        answer.setId(new ObjectId().toHexString());
        return answer;
    }

    @Override
    public boolean documentHasId(Answer answer) {
        return answer.getId() != null;
    }

    @Override
    public BsonValue getDocumentId(Answer answer) {
        return new BsonObjectId(new ObjectId(answer.getId()));
    }

    @Override
    public void encode(BsonWriter writer, Answer answer, EncoderContext ec) {
        Document document = new Document();
 
        String id = answer.getId();
        Date date = answer.getDate();
        User user = answer.getUser();
        Question question = answer.getQuestion();
        Integer ans = answer.getAnswer();
        if (id != null) {
            document.put("_id", new ObjectId(id));
        }
        if (date != null) {
            document.put("date", date);
        }
        if (user != null) {
            document.put("user_id", new ObjectId(user.getId()));
        }
        if (question != null) {
            document.put("question_id", new ObjectId(question.getId()));
        }
        if (ans != null) {
            document.put("answer", ans);
        }
        documentCodec.encode(writer, document, ec);
    }

    @Override
    public Class<Answer> getEncoderClass() {
        return Answer.class;
    }

    @Override
    public Answer decode(BsonReader reader, DecoderContext dc) {
        Document document = documentCodec.decode(reader, dc);
        Answer answer = new Answer();
        ObjectId id = (ObjectId) document.get("_id");
        answer.setId(id.toHexString());
        Date date = (Date) document.get("date");
        answer.setDate(date);
        ObjectId userId = (ObjectId) document.get("user_id");
        if (userId != null) {
            User user = new User(userId.toHexString());
            answer.setUser(user);
            user.needFetch(session);
        }
        ObjectId questionId = (ObjectId) document.get("question_id");
        if (questionId != null) {
            Question question = new Question(questionId.toHexString());
            answer.setQuestion(question);
            question.needFetch(session);
        }
        Integer ans = (Integer) document.get("answer");
        answer.setAnswer(ans);
        return answer;
    }
}
