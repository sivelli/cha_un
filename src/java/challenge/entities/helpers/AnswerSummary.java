package challenge.entities.helpers;

import java.util.Date;
import challenge.entities.Question;
import java.util.List;

public class AnswerSummary {
    private final Question question;
    private final Date dateStart;
    private final Date dateEnd;
    private int[] qtdeAnswers;
    
    public AnswerSummary(Question question, Date dateStart, Date dateEnd) {
        this.question = question;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        List<String> list = this.question.getAlternatives();
        if (list != null) {
            this.qtdeAnswers = new int[list.size()];
        }
    }

    public Question getQuestion() {
        return question;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public int[] getQtdeAnswers() {
        return qtdeAnswers;
    }

    public void setQtdeAnswers(int[] qtdeAnswers) {
        this.qtdeAnswers = qtdeAnswers;
    }
    
}
