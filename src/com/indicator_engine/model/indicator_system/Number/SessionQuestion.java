package com.indicator_engine.model.indicator_system.Number;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tanmaya Mahapatra on 31-03-2015.
 */
@Component
@Scope("session")
@SuppressWarnings({"unused", "unchecked"})
public class SessionQuestion implements Serializable{
    private long goalId;
    private long questionId ;
    private String questionName;
    private String userName;

    private List<SessionIndicator> sessionIndicators;

    public SessionQuestion(){
        reset();
    }


    public void reset(){
        this.goalId = 0;
        this.questionName = null;
        this.sessionIndicators = new ArrayList<>();
        this.userName = null;
    }

    public long getGoalId() {
        return goalId;
    }

    public void setGoalId(long goalId) {
        this.goalId = goalId;
    }

    public long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(long questionId) {
        this.questionId = questionId;
    }

    public String getQuestionName() {
        return questionName;
    }

    public void setQuestionName(String questionName) {
        this.questionName = questionName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<SessionIndicator> getSessionIndicators() {
        return sessionIndicators;
    }

    public void setSessionIndicators(List<SessionIndicator> sessionIndicators) {
        this.sessionIndicators = sessionIndicators;
    }
}
