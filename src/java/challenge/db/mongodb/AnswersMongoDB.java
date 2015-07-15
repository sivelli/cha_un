/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package challenge.db.mongodb;

import challenge.entities.Answer;
import challenge.entities.Question;
import challenge.entities.collections.Answers;
import challenge.entities.helpers.AnswerSummary;
import com.mongodb.client.MongoDatabase;
import java.util.Date;
import java.util.List;
import org.bson.types.ObjectId;

/**
 *
 * @author Sérgio
 */
public class AnswersMongoDB extends CollectionMongoDB<Answer> implements Answers{

    public AnswersMongoDB(MongoDatabase db) {
        super(db.getCollection("cha_answers", Answer.class));
    }
    @Override
    public Object getIdMongoDB(Answer answer) {
        return answer == null? null : new ObjectId(answer.getId());
    }

    @Override
    public List<Answer> get(Question question, Date initial, Date end) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public AnswerSummary getSummary(Question question, Date initial, Date end) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
