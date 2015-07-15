/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package challenge.web;

import challenge.db.mongodb.SessionFactoryMongoDB;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Sérgio
 */
@ManagedBean
@ViewScoped
public class PoolView {

    static class PoolQuestion {
        public PoolQuestion(String question, String[] alternatives) {
            this.question = question;
            this.alternatives = alternatives;
        }
        private final String question;
        private final String[] alternatives;

        public String getQuestion() {
            return question;
        }

        public String[] getAlternatives() {
            return alternatives;
        }
        
    }
    private PoolQuestion[] vetQuestions;
    private int iQuestion;
    private String answer;

    public int getiQuestion() {
        return iQuestion;
    }

    public void setiQuestion(int iQuestion) {
        this.iQuestion = iQuestion;
    }

    /**
     * Creates a new instance of View
     */
    public PoolView() {
    }

    @PostConstruct
    public void init() {
        vetQuestions = new PoolQuestion[] {
            new PoolQuestion("Question 1", new String[] {"Op1", "Op2", "Op3", "Op4", "Op5"}),
            new PoolQuestion("Question 2", new String[] {"Op1", "Op2", "Op3", "Op4", "Op5"}),
            new PoolQuestion("Question 3", new String[] {"Op1", "Op2", "Op3", "Op4", "Op5"})
        };
        iQuestion = 0;
        setAnswer(null);
    }
    
    public void next() {
        iQuestion++;
        answer = null;
    }
    
    public void finish() {
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
    
    public String getQuestion() {
        return (iQuestion < vetQuestions.length? vetQuestions[iQuestion].getQuestion():null);
    }

    public List<String> getAlternatives() {
        return (iQuestion < vetQuestions.length? Arrays.asList(vetQuestions[iQuestion].getAlternatives()):null);
    }

    public boolean hasMoreQuestions() {
        return (iQuestion + 1 < vetQuestions.length);
    }
}
