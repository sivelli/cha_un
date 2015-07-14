package challenge.services;

public class PoolStatistics {
    public PoolSummary getPoolSummary(DBSession session, Pool pool, Date initial, Date end) {
        PoolSummary sum = null;
        if (pool != null) {
            sum = new PoolSummary(pool, initial, end);
            Answers ansCol = session.getAnswers();
            for (Question q: pool.getQuestions()) {
                AnswerSummary ansSum = ansCol.getSummary(q, initial, end);
                if (ansSum != null) {
                    sum.addAnswerSummary(ansSum);
                }
            }
        }
        return sum;
    }
}
