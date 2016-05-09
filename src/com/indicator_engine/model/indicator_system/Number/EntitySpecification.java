/*
 * Open Platform Learning Analytics : Indicator Engine
 * Copyright (C) 2015  Learning Technologies Group, RWTH
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package com.indicator_engine.model.indicator_system.Number;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tanmaya Mahapatra on 10-06-2015.
 */

public class EntitySpecification implements Serializable {

    private String questionName;
    private String indicatorName;
    private String hql;

    private List<String> selectedSource;
    private String selectedPlatform;
    private String selectedAction;

    private String selectedMinor;
    private String selectedMajor;
    private String selectedType;


    private List<EntityValues>  entityValues = new ArrayList<EntityValues>();
    private List<UserSearchSpecifications>  userSpecifications = new ArrayList<UserSearchSpecifications>();
    private List<SessionSpecifications>  sessionSpecifications = new ArrayList<SessionSpecifications>();
    private List<TimeSearchSpecifications>  timeSpecifications = new ArrayList<TimeSearchSpecifications>();

    private String selectedChartType;
    private String selectedChartEngine;

    private boolean isComposite;
    private long analyticsMethodId;

    private final String  persistenceObject;
    private final String filteringType;
    private final String retrievableObjects;

    private Questions questionsContainer = new Questions();

    public EntitySpecification() {
        this.persistenceObject = "GLAEntity";
        this.filteringType  ="AND";
        this.retrievableObjects = " COUNT(*) ";

    }

    public void reset() {

        this.indicatorName = null;
        this.hql = null;

        this.selectedAction = null;
        this.selectedPlatform = null;
        this.selectedSource = null;

        this.selectedMajor = null;
        this.selectedMinor = null;
        this.selectedType = null;

        this.selectedChartEngine = null;
        this.selectedChartType = null;
        this.isComposite = false;

        this.entityValues.clear();
        this.userSpecifications.clear();
        this.timeSpecifications.clear();
        this.sessionSpecifications.clear();
    }

    public void completeReset() {

        this.questionName = null;
        reset();
        this.questionsContainer = null;
    }


    public List<String> getSelectedSource() {
        return selectedSource;
    }

    public void setSelectedSource(List<String> selectedSource) {
        this.selectedSource = selectedSource;
    }

    public String getSelectedPlatform() {
        return selectedPlatform;
    }

    public void setSelectedPlatform(String selectedPlatform) {
        this.selectedPlatform = selectedPlatform;
    }

    public String getSelectedAction() {
        return selectedAction;
    }

    public void setSelectedAction(String selectedAction) {
        this.selectedAction = selectedAction;
    }

    public String getSelectedMinor() {
        return selectedMinor;
    }

    public void setSelectedMinor(String selectedMinor) {
        this.selectedMinor = selectedMinor;
    }

    public String getSelectedMajor() {
        return selectedMajor;
    }

    public void setSelectedMajor(String selectedMajor) {
        this.selectedMajor = selectedMajor;
    }

    public String getSelectedType() {
        return selectedType;
    }

    public void setSelectedType(String selectedType) {
        this.selectedType = selectedType;
    }

    public List<EntityValues> getEntityValues() {
        return entityValues;
    }

    public void setEntityValues(List<EntityValues> entityValues) {
        this.entityValues = entityValues;
    }

    public List<UserSearchSpecifications> getUserSpecifications() {
        return userSpecifications;
    }

    public void setUserSpecifications(List<UserSearchSpecifications> userSpecifications) {
        this.userSpecifications = userSpecifications;
    }

    public List<SessionSpecifications> getSessionSpecifications() {
        return sessionSpecifications;
    }

    public void setSessionSpecifications(List<SessionSpecifications> sessionSpecifications) {
        this.sessionSpecifications = sessionSpecifications;
    }

    public List<TimeSearchSpecifications> getTimeSpecifications() {
        return timeSpecifications;
    }

    public void setTimeSpecifications(List<TimeSearchSpecifications> timeSpecifications) {
        this.timeSpecifications = timeSpecifications;
    }

    public String getSelectedChartType() {
        return selectedChartType;
    }

    public void setSelectedChartType(String selectedChartType) {
        this.selectedChartType = selectedChartType;
    }

    public String getSelectedChartEngine() {
        return selectedChartEngine;
    }

    public void setSelectedChartEngine(String selectedChartEngine) {
        this.selectedChartEngine = selectedChartEngine;
    }

    public String getQuestionName() {
        return questionName;
    }

    public void setQuestionName(String questionName) {
        this.questionName = questionName;
    }

    public String getIndicatorName() {
        return indicatorName;
    }

    public void setIndicatorName(String indicatorName) {
        this.indicatorName = indicatorName;
    }

    public String getHql() {
        return hql;
    }

    public void setHql(String hql) {
        this.hql = hql;
    }

    public Questions getQuestionsContainer() {
        return questionsContainer;
    }

    public void setQuestionsContainer(Questions questionsContainer) {
        this.questionsContainer = questionsContainer;
    }

    public String getPersistenceObject() {
        return persistenceObject;
    }

    public String getFilteringType() {
        return filteringType;
    }

    public String getRetrievableObjects() {
        return retrievableObjects;
    }

    public boolean isComposite() {
        return isComposite;
    }

    public void setComposite(boolean isComposite) {
        this.isComposite = isComposite;
    }

    public long getAnalyticsMethodId() { return analyticsMethodId; }

    public void setAnalyticsMethodId(long analyticsMethodId) { this.analyticsMethodId = analyticsMethodId; }
}