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

package com.indicator_engine.indicator_system;

import com.indicator_engine.dao.*;
import com.indicator_engine.datamodel.GLAIndicator;
import com.indicator_engine.datamodel.GLAQuestion;
import com.indicator_engine.datamodel.GLAQuestionProps;
import com.indicator_engine.model.indicator_system.Number.*;
import com.indicator_engine.model.indicator_system.IndicatorDefnOperationForm;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tanmaya Mahapatra on 31-03-2015.
 */
@SuppressWarnings({"unused", "unchecked"})
public class IndicatorPreProcessing implements IndicatorPreProcessingDao {
    static Logger log = Logger.getLogger(IndicatorPreProcessingDao.class.getName());
    @Autowired
    private ApplicationContext appContext;

    @Override
    public String retrieveOperation(IndicatorDefnOperationForm indicatorDefnOperationForm) {
        return indicatorDefnOperationForm.getSelectedOperation();
    }

    @Override
    public SelectNumberParameters initSelectNumberParametersObject() {
        SelectNumberParameters obj = new SelectNumberParameters();
        //obj.getEntityValues().add(new EntityValues());
        GLAEventDao glaEventBean = (GLAEventDao) appContext.getBean("glaEvent");
        obj.setSource(glaEventBean.selectAll("source"));
        obj.setPlatform(glaEventBean.selectAll("platform"));
        obj.setAction(glaEventBean.selectAll("action"));
        return obj;
    }

    @Override
    public List<String> initPopulateMinors(List<String> sources, String action, String platform) {
        log.info("initPopulateMinors : STARTED");
        GLAEventDao glaEventBean = (GLAEventDao) appContext.getBean("glaEvent");
        GLACategoryDao glacategoryBean = (GLACategoryDao) appContext.getBean("glaCategory");
        List<String> minor = new ArrayList<>();
        for(String source : sources) {
            log.info("initPopulateMinors : Selected Source : \t" + source + "\n");
            long category_id = glaEventBean.findCategoryId(action,source,
                    platform);
            log.info("initPopulateMinors : Category ID : \t" + category_id + "\n");
            for(String values : glacategoryBean.findCategoryByID(category_id, "minor")) {
                if(values != null)
                    minor.add(values);
            }
        }
        log.info("initPopulateMinors : Computed Minor : \t" + minor + "\n");
        log.info("initPopulateMinors : ENDED");
        return minor;
    }

    @Override
    public List<String> initPopulateMajors(List<String> sources, String action, String platform) {
        log.info("initPopulateMajors : STARTED");
        GLAEventDao glaEventBean = (GLAEventDao) appContext.getBean("glaEvent");
        GLACategoryDao glacategoryBean = (GLACategoryDao) appContext.getBean("glaCategory");
        List<String> major = new ArrayList<>();
        for(String source : sources) {
            log.info("initPopulateMajors : Selected Source : \t" + source + "\n");
            long category_id = glaEventBean.findCategoryId(action, source,
                    platform);
            log.info("initPopulateMajors : Category ID : \t" + category_id + "\n");
            for(String values : glacategoryBean.findCategoryByID(category_id, "major")){
                if(values != null)
                    major.add(values);
            }
        }
        log.info("initPopulateMajors : Computed Major : \t" + major + "\n");
        log.info("initPopulateMajors : ENDED");
        return major;
    }

    @Override
    public List<String> initPopulateTypes(List<String> sources, String action, String platform) {
        log.info("initPopulateTypes : STARTED");
        GLAEventDao glaEventBean = (GLAEventDao) appContext.getBean("glaEvent");
        GLACategoryDao glacategoryBean = (GLACategoryDao) appContext.getBean("glaCategory");
        List<String> types = new ArrayList<>();
        for(String source : sources) {
            log.info("initPopulateTypes : Selected Source : \t" + source + "\n");
            long category_id = glaEventBean.findCategoryId(action, source,
                    platform);
            log.info("initPopulateTypes : Category ID : \t" + category_id + "\n");
            for(String values : glacategoryBean.findCategoryByID(category_id, "type")){
                log.info("initPopulateTypes : Values for Category ID : \t" + values + "\n");
                if(values != null)
                    types.add(values);
            }
        }
        log.info("initPopulateTypes : Computed Types : \t" + types + "\n");
        log.info("initPopulateTypes : ENDED");
        return types;
    }

    @Override
    public IndicatorDefnOperationForm initAvailableOperations_DB() {
        IndicatorDefnOperationForm obj = new IndicatorDefnOperationForm();
        GLAOperationsDao glaOperationsBean = (GLAOperationsDao) appContext.getBean("glaOperations");
        obj.setOperation(glaOperationsBean.selectAllOperations());
        return obj;

    }

    @Override
    public List<String> initAvailableEntities_DB(String minor) {
        GLACategoryDao glacategoryBean = (GLACategoryDao) appContext.getBean("glaCategory");
        long category_id = glacategoryBean.findCategoryID(minor);
        GLAEntityDao glaEntityBean = (GLAEntityDao) appContext.getBean("glaEntity");
        return glaEntityBean.loadEntitiesByCategoryID(category_id);
    }

    @Override
    public List<String> initAvailableEvents_DB(String minor) {
        GLACategoryDao glacategoryBean = (GLACategoryDao) appContext.getBean("glaCategory");
        GLAEventDao glaEventBean = (GLAEventDao) appContext.getBean("glaEvent");
        long category_id = glacategoryBean.findCategoryID(minor);
        return glaEventBean.loadEventByCategoryID(category_id);
    }

    @Override
    public void addDefaultEValues(SelectNumberParameters selectNumberParameters) {
        if ((selectNumberParameters.getEntityValues().isEmpty()))
            selectNumberParameters.getEntityValues().add(new EntityValues(selectNumberParameters.getSelectedKeys(),
                    selectNumberParameters.getSelectedentityValueTypes(), "ALL"));

    }

    @Override
    public void saveIndicator(Questions questions){
        log.info("Saving Indicator and all its Questions/Queries : STARTED " + questions.getQuestionName());
        GLAIndicatorDao glaIndicatorBean = (GLAIndicatorDao) appContext.getBean("glaIndicator");
        GLAQuestionDao glaQuestionBean = (GLAQuestionDao) appContext.getBean("glaQuestions");
        List<GLAIndicator> glaIndicator = new ArrayList<>();
        GLAQuestion glaQuestion = new GLAQuestion();
        glaQuestion.setQuestion_name(questions.getQuestionName());
        glaQuestion.setIndicators_num(questions.getGenQueries().size());
        glaQuestion.setGlaQuestionProps(new GLAQuestionProps());
        for(int i = 0 ; i < questions.getGenQueries().size(); i++) {
            GLAIndicator gI = new GLAIndicator();
            gI.setHql(questions.getGenQueries().get(i).getQuery());
            gI.setIndicator_name(questions.getGenQueries().get(i).getIndicatorName());
            glaIndicator.add(gI);
        }
        questions.setQuestionId(glaQuestionBean.add(glaQuestion, glaIndicator));
        log.info("Saving Indicator and all its Questions/Queries : ENDED");

    }

    @Override
    public void retrieveQuestion(Questions questions){
        log.info("retrieveQuestion : STARTED \n");
        GLAQuestionDao glaQuestionsBean = (GLAQuestionDao) appContext.getBean("glaQuestions");
        log.info("RetrieveQuestion From DB : STARTED \n");
        GLAQuestion glaQuestion = glaQuestionsBean.loadByQuestionID(questions.getQuestionId());
        questions.reset();
        questions.setQuestionId(glaQuestion.getId());
        questions.setQuestionName(glaQuestion.getQuestion_name());
        questions.setLast_executionTime(glaQuestion.getGlaQuestionProps().getLast_executionTime());
        questions.setTotalExecutions(glaQuestion.getGlaQuestionProps().getTotalExecutions());
        log.info("GLA QUESTION FROM DB : ID : \t"+ glaQuestion.getId());
        log.info("GLA QUESTION FROM DB : Name : \t"+ glaQuestion.getQuestion_name());
        for( GLAIndicator glaIndicators : glaQuestion.getGlaIndicators()){
            log.info("GLA Indicator FROM DB : Name : \t"+ glaIndicators.getIndicator_name());
            log.info("GLA Indicator FROM DB : ID : \t"+ glaIndicators.getId());
            log.info("GLA Indicator FROM DB : HQL : \t"+ glaIndicators.getHql());
            log.info("GLA Indicator FROM DB : Short Name : \t"+ glaIndicators.getShort_name());
            log.info("GLA Indicator FROM DB : PROPS ID : \t"+ glaIndicators.getGlaIndicatorProps().getId());
            log.info("GLA INDICATOR FROM DB : LEX TIME : \t"+ glaIndicators.getGlaIndicatorProps().getLast_executionTime());
            log.info("GLA INDICATOR FROM DB : EXEC COUNTER : \t"+ glaIndicators.getGlaIndicatorProps().getTotalExecutions());
            GenQuery genQuery = new GenQuery(glaIndicators.getHql(),glaIndicators.getIndicator_name(),
                    glaIndicators.getId());
            genQuery.setGenIndicatorProps(glaIndicators.getGlaIndicatorProps().getId(),glaIndicators.getGlaIndicatorProps().getLast_executionTime(),
                    glaIndicators.getGlaIndicatorProps().getTotalExecutions());
            questions.getGenQueries().add(genQuery);
        }
        log.info("reteriveQuestion : ENDED \n");
    }

    @Override
    public void flushAll(Questions questions, SelectNumberParameters selectNumberParameters,
                                 IndicatorDefnOperationForm availableOperations){

        GLAEventDao glaEventBean = (GLAEventDao) appContext.getBean("glaEvent");
        GLAOperationsDao glaOperationsBean = (GLAOperationsDao) appContext.getBean("glaOperations");
        questions.reset();
        selectNumberParameters.reset();
        availableOperations.reset();
        selectNumberParameters.setSource(glaEventBean.selectAll("source"));
        selectNumberParameters.setPlatform(glaEventBean.selectAll("platform"));
        selectNumberParameters.setAction(glaEventBean.selectAll("action"));
        availableOperations.setOperation(glaOperationsBean.selectAllOperations());
    }

    @Override
    public void flushPrevQnData(SelectNumberParameters selectNumberParameters){
        GLAEventDao glaEventBean = (GLAEventDao) appContext.getBean("glaEvent");
        selectNumberParameters.reset();
        selectNumberParameters.setSource(glaEventBean.selectAll("source"));
        selectNumberParameters.setPlatform(glaEventBean.selectAll("platform"));
        selectNumberParameters.setAction(glaEventBean.selectAll("action"));
    }
}