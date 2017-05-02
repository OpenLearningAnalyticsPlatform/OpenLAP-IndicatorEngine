/*
 * Open Learning Analytics Platform (OpenLAP) : Indicator Engine

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

import de.rwthaachen.openlap.dataset.OpenLAPPortConfig;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Tanmaya Mahapatra on 10-06-2015.
 */

public class EntitySpecification implements Serializable {

    private long goalId;
    private String questionName;

    private SessionIndicator currentIndicator;

    private SessionQuestion currentQuestion;

    public EntitySpecification() {
        completeReset();
    }

    public void reset() {
        this.currentIndicator = new SessionIndicator();
    }

    public void completeReset() {
        this.questionName = null;
        this.goalId = 0L;
        this.currentQuestion = new SessionQuestion();
        reset();
    }

    public long getGoalId() {
        return goalId;
    }

    public void setGoalId(long goalId) {
        this.goalId = goalId;
    }

    public String getQuestionName() {
        return questionName;
    }

    public void setQuestionName(String questionName) {
        this.questionName = questionName;
    }

    public SessionIndicator getCurrentIndicator() {
        return currentIndicator;
    }

    public void setCurrentIndicator(SessionIndicator currentIndicator) {
        this.currentIndicator = currentIndicator;
    }

    public SessionQuestion getCurrentQuestion() {
        return currentQuestion;
    }

    public void setCurrentQuestion(SessionQuestion currentQuestion) {
        this.currentQuestion = currentQuestion;
    }

    //    public List<String> getSelectedSource() {
//        return selectedSource;
//    }
//
//    public void setSelectedSource(List<String> selectedSource) {
//        this.selectedSource = selectedSource;
//    }
//
//
//    public List<String> getSelectedPlatform() {
//        return selectedPlatform;
//    }
//
//    public void setSelectedPlatform(List<String> selectedPlatform) {
//        this.selectedPlatform = selectedPlatform;
//    }
//
//    public List<String> getSelectedAction() {
//        return selectedAction;
//    }
//
//    public void setSelectedAction(List<String> selectedAction) {
//        this.selectedAction = selectedAction;
//    }
//
//    public List<Integer> getSelectedMinor() {
//        return selectedMinor;
//    }
//
//    public void setSelectedMinor(List<Integer> selectedMinor) {
//        this.selectedMinor = selectedMinor;
//    }
//
//    public String getSelectedMajor() {
//        return selectedMajor;
//    }
//
//    public void setSelectedMajor(String selectedMajor) {
//        this.selectedMajor = selectedMajor;
//    }
//
//    public String getSelectedType() {
//        return selectedType;
//    }
//
//    public void setSelectedType(String selectedType) {
//        this.selectedType = selectedType;
//    }
//
//    public List<EntityValues> getEntityValues() {
//        return entityValues;
//    }
//
//    public void setEntityValues(List<EntityValues> entityValues) {
//        this.entityValues = entityValues;
//    }
//
//    public List<UserSearchSpecifications> getUserSpecifications() {
//        return userSpecifications;
//    }
//
//    public void setUserSpecifications(List<UserSearchSpecifications> userSpecifications) {
//        this.userSpecifications = userSpecifications;
//    }
//
//    public List<SessionSpecifications> getSessionSpecifications() {
//        return sessionSpecifications;
//    }
//
//    public void setSessionSpecifications(List<SessionSpecifications> sessionSpecifications) {
//        this.sessionSpecifications = sessionSpecifications;
//    }
//
//    public List<TimeSearchSpecifications> getTimeSpecifications() {
//        return timeSpecifications;
//    }
//
//    public void setTimeSpecifications(List<TimeSearchSpecifications> timeSpecifications) {
//        this.timeSpecifications = timeSpecifications;
//    }

//    public String getSelectedChartType() {
//        return selectedChartType;
//    }
//
//    public void setSelectedChartType(String selectedChartType) {
//        this.selectedChartType = selectedChartType;
//    }
//
//    public String getSelectedChartEngine() {
//        return selectedChartEngine;
//    }
//
//    public void setSelectedChartEngine(String selectedChartEngine) {
//        this.selectedChartEngine = selectedChartEngine;
//    }

//    public String getIndicatorName() {
//        return indicatorName;
//    }
//
//    public void setIndicatorName(String indicatorName) {
//        this.indicatorName = indicatorName;
//    }

//    public String getHql() {
//        return hql;
//    }
//
//    public void setHql(String hql) {
//        this.hql = hql;
//    }

//    public Map<String, String> getHqlQuery() {
//        return hqlQuery;
//    }
//
//    public void setHqlQuery(Map<String, String> hqlQuery) {
//        this.hqlQuery = hqlQuery;
//    }

    //    public String getPersistenceObject() {
//        return persistenceObject;
//    }

//    public String getFilteringType() {
//        return filteringType;
//    }

//    public String getRetrievableObjects() {
//        return retrievableObjects;
//    }
//
//    public void setRetrievableObjects(String retrievableObjects) {
//        this.retrievableObjects = retrievableObjects;
//    }
//
//    public List<String> getEntityDisplayObjects() {
//        return entityDisplayObjects;
//    }
//
//    public void setEntityDisplayObjects(List<String> entityDisplayObjects) {
//        this.entityDisplayObjects = entityDisplayObjects;
//    }
//
//    public boolean isComposite() {
//        return isComposite;
//    }
//
//    public void setComposite(boolean isComposite) {
//        this.isComposite = isComposite;
//    }

//    public long getAnalyticsMethodId() {
//        return analyticsMethodId;
//    }
//
//    public void setAnalyticsMethodId(long analyticsMethodId) {
//        this.analyticsMethodId = analyticsMethodId;
//    }
//
//    public OpenLAPPortConfig getQueryToMethodConfig() {
//        return queryToMethodConfig;
//    }
//
//    public void setQueryToMethodConfig(OpenLAPPortConfig queryToMethodConfig) { this.queryToMethodConfig = queryToMethodConfig; }


//    public Map<String, IndicatorDataset> getIndicatorDataset() {
//        return indicatorDataset;
//    }
//
//    public void setIndicatorDataset(Map<String, IndicatorDataset> indicatorDataset) {
//        this.indicatorDataset = indicatorDataset;
//    }
//
//    public Map<String, Long> getAnalyticsMethodId() {
//        return analyticsMethodId;
//    }
//
//    public void setAnalyticsMethodId(Map<String, Long> analyticsMethodId) {
//        this.analyticsMethodId = analyticsMethodId;
//    }
//
//    public Map<String, OpenLAPPortConfig> getQueryToMethodConfig() {
//        return queryToMethodConfig;
//    }
//
//    public void setQueryToMethodConfig(Map<String, OpenLAPPortConfig> queryToMethodConfig) {
//        this.queryToMethodConfig = queryToMethodConfig;
//    }
//
//    public OpenLAPPortConfig getMethodToVisualizationConfig() {
//        return methodToVisualizationConfig;
//    }
//
//    public void setMethodToVisualizationConfig(OpenLAPPortConfig methodToVisualizationConfig) { this.methodToVisualizationConfig = methodToVisualizationConfig; }
//
//    public String getPreviewVisualization() {
//        return previewVisualization;
//    }
//
//    public void setPreviewVisualization(String previewVisualization) {
//        this.previewVisualization = previewVisualization;
//    }
}