/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package challenge.db;

import challenge.entities.Entity;
import challenge.entities.collections.Pools;
import challenge.entities.collections.Questions;
import challenge.entities.collections.Users;
import challenge.entities.collections.Answers;
import java.io.Closeable;

/**
 *
 * @author Sérgio
 */
public interface DBSession extends Closeable {
    public Users getUsers();
    public Pools getPools();
    public Questions getQuestions();
    public Answers getAnswers();
    public void needFetch(Entity entity);
    public void fetch(Entity entity);
}
