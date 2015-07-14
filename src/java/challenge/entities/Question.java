/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package challenge.entities;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author sergio.sivelli
 */
public class Question extends Entity {
    private String id;
    private String question;
    private List<String> alternatives;

    public Question() {
    }

    public Question(String id) {
        this.id = id;
    }
    
    public Question(String id, String question, List<String> alternatives) {
        this.id = id;
        this.question = question;
        this.alternatives = alternatives;
    }

    public Question(String id, String question, String[] alternatives) {
        this.id = id;
        this.question = question;
        this.alternatives = Arrays.asList(alternatives);
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<String> getAlternatives() {
        return alternatives;
    }

    public void setAlternatives(List<String> alternatives) {
        this.alternatives = alternatives;
    }

    @Override
    public void load(Entity question) {
        if (question.getClass() == Question.class) {
            setAlternatives(((Question)question).getAlternatives());
            setQuestion(((Question)question).getQuestion());
            setById(false);
        }
        else {
            throw new EntityException("Cannot load " + question.getClass() + " in " + Question.class);
        }
    }

    @Override
    public String toString() {
        return "Question{" + "id=" + id + ", question=" + question + ", alternatives=" + alternatives + '}';
    }
}
