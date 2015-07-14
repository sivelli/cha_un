/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package challenge.entities.collections;

import challenge.entities.Answer;

/**
 *
 * @author Sérgio
 */
public interface Answers extends EntityCollection<Answer>{
    public List<Answer> get(Question question, Date initial, Date end);
    public AnswerSummary getSummary(Question question, Date initial, Date end);
}
