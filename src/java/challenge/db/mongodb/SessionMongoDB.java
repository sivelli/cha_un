/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package challenge.db.mongodb;

import challenge.db.DBSession;
import challenge.entities.Entity;
import challenge.entities.Pool;
import challenge.entities.Question;
import challenge.entities.User;
import challenge.entities.collections.Answers;
import challenge.entities.collections.Pools;
import challenge.entities.collections.Questions;
import challenge.entities.collections.Users;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import java.io.IOException;

/**
 *
 * @author Sérgio
 */
public class SessionMongoDB implements DBSession {
    private MongoClient client;
    private String database;
    private MongoDatabase db;
    private Users users;
    private Pools pools;
    private Questions questions;
    private Answers answers;
    
    public SessionMongoDB() {
    }

    public SessionMongoDB(MongoClient client, String database) {
        this.database = database;
        this.client = client;
        this.db = client.getDatabase(database);
    }
    
    public MongoClient getClient() {
        return client;
    }
    
    public void load(MongoClient client, String database) {
        this.database = database;
        this.client = client;
        this.db = client.getDatabase(database);
    }

    public MongoDatabase getDb() {
        return db;
    }

    @Override
    public void close() throws IOException {
        if (client != null) {
            client.close();
        }
    }

    @Override
    public Users getUsers() {
        if (users == null) {
            users = new UsersMongoDB(db);
        }
        return users;
    }

    @Override
    public Pools getPools() {
        if (pools == null) {
            pools = new PoolsMongoDB(db);
        }
        return pools;
    }

    @Override
    public Questions getQuestions() {
        if (questions == null) {
            questions = new QuestionsMongoDB(db);
        }
        return questions;
    }

    @Override
    public void needFetch(Entity entity) {
        if (entity.getClass() == User.class) {
            users.needFetch((User)entity);
        }
        else if (entity.getClass() == Question.class) {
            questions.needFetch((Question)entity);
        }
        else if (entity.getClass() == Pool.class) {
            pools.needFetch((Pool)entity);
        }
    }

    @Override
    public void fetch(Entity entity) {
        if (entity.getClass() == User.class) {
            users.fetch((User)entity);
        }
        else if (entity.getClass() == Question.class) {
            questions.fetch((Question)entity);
        }
        else if (entity.getClass() == Pool.class) {
            pools.fetch((Pool)entity);
        }
    }

    @Override
    public Answers getAnswers() {
        if (answers == null) {
            answers = new AnswersMongoDB(db);
        }
        return answers;
    }
}
