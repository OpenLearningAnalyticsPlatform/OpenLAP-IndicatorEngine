package com.indicator_engine.model.indicator_system.Number;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tanmaya Mahapatra on 31-03-2015.
 */
@Component
@Scope("session")
@SuppressWarnings({"unused", "unchecked"})
public class Questions implements Serializable{


    private String questionName;
    private long questionId ;
    private Timestamp last_executionTime;
    private int totalExecutions;

    private List<GenQuery> genQueries = new ArrayList<>();

    public Questions(){}

    public Timestamp getLast_executionTime() {
        return last_executionTime;
    }

    public void setLast_executionTime(Timestamp last_executionTime) {
        this.last_executionTime = last_executionTime;
    }

    public int getTotalExecutions() {
        return totalExecutions;
    }

    public void setTotalExecutions(int totalExecutions) {
        this.totalExecutions = totalExecutions;
    }

    public void reset(){
        // Reset everything to your default values
        this.questionName = null;
        this.genQueries.clear();
    }

    public long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(long questionId) {
        this.questionId = questionId;
    }

    public void setGenQueries(List<GenQuery> genQueries) {
        this.genQueries = genQueries;
    }


    public List<GenQuery> getGenQueries() {
        return genQueries;
    }

    public String getQuestionName() {
        return questionName;
    }

    public void setQuestionName(String questionName) {
        this.questionName = questionName;
    }


}
