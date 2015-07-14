/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package challenge.entities;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Sérgio
 */
public class Pool extends Entity {
    private String id;
    private Date date;
    private boolean active;
    private List<Question> questions;

    public Pool() {
    }

    public Pool(String id, Date date, boolean active, Question[] questions) {
        this(id, date, active, Arrays.asList(questions));
    }

    public Pool(String id, Date date, boolean active, List<Question> questions) {
        this.id = id;
        this.date = date;
        this.active = active;
        this.questions = questions;
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    @Override
    public void load(Entity pool) {
        if (pool.getClass() == Pool.class) {
            setDate(((Pool)pool).getDate());
            setActive(((Pool)pool).isActive());
            setQuestions(((Pool)pool).getQuestions());
            setById(false);
        }
        else {
            throw new EntityException("Cannot load " + pool.getClass() + " in " + Pool.class);
        }
    }

    @Override
    public String toString() {
        return "Pool{" + "id=" + id + ", date=" + date + ", active=" + active + ", questions=" + questions + '}';
    }
}
