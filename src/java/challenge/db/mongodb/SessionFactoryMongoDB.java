/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package challenge.db.mongodb;

import challenge.db.DBSession;
import challenge.db.DBSessionFactory;
import challenge.db.DBSessions;
import challenge.db.mongodb.codecs.PoolCodec;
import challenge.db.mongodb.codecs.QuestionCodec;
import challenge.db.mongodb.codecs.UserCodec;
import challenge.entities.Pool;
import challenge.entities.Question;
import challenge.entities.User;
import challenge.entities.collections.Pools;
import challenge.entities.collections.Questions;
import challenge.entities.collections.Users;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import org.bson.Document;
import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;

/**
 *
 * @author Sérgio
 */
 
public class SessionFactoryMongoDB implements DBSessionFactory {
    private final String uri;
    private String databaseDefault;

    public SessionFactoryMongoDB(String uri) {
        this.uri = uri;
    }
    
    private MongoClient newClient(String uri, SessionMongoDB session) {
        Codec<Document> defaultDocumentCodec = MongoClient.getDefaultCodecRegistry().get(Document.class);
        UserCodec userCodec = new UserCodec(defaultDocumentCodec, session);
        QuestionCodec qCodec = new QuestionCodec(defaultDocumentCodec, session);
        PoolCodec poolCodec = new PoolCodec(defaultDocumentCodec, session);
        CodecRegistry codecRegistry = CodecRegistries.fromRegistries(MongoClient.getDefaultCodecRegistry(), CodecRegistries.fromCodecs(userCodec), CodecRegistries.fromCodecs(qCodec), CodecRegistries.fromCodecs(poolCodec));
        MongoClientOptions.Builder builder = MongoClientOptions.builder()
                .codecRegistry(codecRegistry);
        MongoClientURI mongoUri = new MongoClientURI(uri, builder);
        this.databaseDefault = mongoUri.getDatabase();
        return new MongoClient(mongoUri);
    }

    @Override
    public DBSession getNew(String database) {
        SessionMongoDB session = new SessionMongoDB();
        MongoClient client = newClient(uri, session);
        if (database == null) {
            database = databaseDefault;
        }
        session.load(client, database);
        return session;
    }

    public static void main(String[] args) {
        //putQuestions("Question 1", "option A", "option B", "option C", "option D", "option E");
        DBSessions.registerFactory(new SessionFactoryMongoDB(args[0]));
        try (DBSession session = DBSessions.create(null)) {
            Users users = session.getUsers();
            List<User> listUsers = users.getAll();
            listUsers.stream().forEach(System.out::println);
            Questions questions = session.getQuestions();
            List<Question> allQuestions = questions.getAll();
            allQuestions.stream().forEach((q) -> {
                System.out.println(q);
            });
            if (allQuestions.isEmpty()) {
                putQuestion(questions, "Question 1", "option A", "option B", "option C", "option D", "option E");
                allQuestions = questions.getAll();
            }
            Pools pools = session.getPools();
            Pool pool = pools.getLastActive();
            if (pool == null && !allQuestions.isEmpty()) {
                pool = new Pool(null, new Date(), true, allQuestions);
                pools.add(pool);
                System.out.println("Pool created:" + pool);
            }
            if (pool != null) {
                System.out.println("Active Questions:");
                for(Question q : pool.getQuestions()) {
                    q.fetch();
                    System.out.println(q);
                }
            }
        }
        catch (IOException ex) {
            
        }
    }
    public static void putQuestion(Questions questions, String question, String... alt) {
        Question q = new Question(null, question, alt);
        questions.add(q);
        System.out.println("Question created:" + q);
    }
}   
