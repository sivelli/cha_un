/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package challenge.db.mongodb;

import challenge.entities.Question;
import challenge.entities.collections.Questions;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.types.ObjectId;

/**
 *
 * @author Sérgio
 */
public class QuestionsMongoDB extends CollectionMongoDB<Question> implements Questions{

    public QuestionsMongoDB(MongoDatabase db) {
        super(db.getCollection("cha_questions", Question.class));
    }
    @Override
    public Object getIdMongoDB(Question question) {
        return question == null? null : new ObjectId(question.getId());
    }
}
