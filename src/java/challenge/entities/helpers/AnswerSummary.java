package challenge.entities.helpers;

import java.util.Date;
import challenge.entities.Question;

public class AnswerSummary {
    private Question;
    private Date dateStart;
    private Date dateEnd;
    private int[] qtdeAnswers;
    
    public AnswerSummary(Question question, Date dateStart, Date dateEnd) {
        this.question = question;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        List<String> list = this.question.getAlternatives();
        if (list != null) {
            this.qtdeAnswers = new int[this.list.size()];
        }
    }
}
