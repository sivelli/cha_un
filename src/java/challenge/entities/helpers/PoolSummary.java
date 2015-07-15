package challenge.entities.helpers;

import java.util.Date;
import challenge.entities.Pool;
import challenge.entities.Question;
import java.util.List;

public class PoolSummary {
    private final Date dateStart;
    private final Date dateEnd;
    private final Pool pool;
    private final AnswerSummary[] summary;
	
    public PoolSummary(Pool pool, Date dateStart, Date dateEnd) {
        this.pool = pool;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        List<Question> listQuestion = this.pool.getQuestions();
        if (listQuestion != null) {
            this.summary = new AnswerSummary[listQuestion.size()];
        }
        else {
            this.summary = null;
        }
    }
    
    public void addAnswerSummary(AnswerSummary answerSum) {
        List<Question> listQuestion = this.pool.getQuestions();
        int iQuestion = -1;
        for (int i = 0; i < listQuestion.size(); i++) {
            if (listQuestion.get(i).equals(answerSum.getQuestion())) {
                iQuestion = i;
                break;
            }
        }
        if (iQuestion >= 0 && iQuestion < this.summary.length) {
            this.summary[iQuestion] = answerSum;
        }
    }
}
