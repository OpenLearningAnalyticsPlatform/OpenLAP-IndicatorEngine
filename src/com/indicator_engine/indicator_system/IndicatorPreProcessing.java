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
import com.indicator_engine.datamodel.GLAQueries;
import com.indicator_engine.model.indicator_system.Number.*;
import com.indicator_engine.model.indicator_system.IndicatorDefnOperationForm;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
    public List<String> initPopulateMinors(SelectNumberParameters selectNumberParameters) {
        log.info("initPopulateMinors : STARTED");
        GLAEventDao glaEventBean = (GLAEventDao) appContext.getBean("glaEvent");
        GLACategoryDao glacategoryBean = (GLACategoryDao) appContext.getBean("glaCategory");
        List<String> minor = new ArrayList<>();
        for(String source : selectNumberParameters.getSelectedSource()) {
            log.info("initPopulateMinors : Selected Source : \t" + source + "\n");
            long category_id = glaEventBean.findCategoryId(selectNumberParameters.getSelectedAction(),source,
                    selectNumberParameters.getSelectedPlatform());
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
    public List<String> initPopulateMajors(SelectNumberParameters selectNumberParameters) {
        log.info("initPopulateMajors : STARTED");
        GLAEventDao glaEventBean = (GLAEventDao) appContext.getBean("glaEvent");
        GLACategoryDao glacategoryBean = (GLACategoryDao) appContext.getBean("glaCategory");
        List<String> major = new ArrayList<>();
        for(String source : selectNumberParameters.getSelectedSource()) {
            log.info("initPopulateMajors : Selected Source : \t" + source + "\n");
            long category_id = glaEventBean.findCategoryId(selectNumberParameters.getSelectedAction(), source,
                    selectNumberParameters.getSelectedPlatform());
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
    public List<String> initPopulateTypes(SelectNumberParameters selectNumberParameters) {
        log.info("initPopulateTypes : STARTED");
        GLAEventDao glaEventBean = (GLAEventDao) appContext.getBean("glaEvent");
        GLACategoryDao glacategoryBean = (GLACategoryDao) appContext.getBean("glaCategory");
        List<String> types = new ArrayList<>();
        for(String source : selectNumberParameters.getSelectedSource()) {
            log.info("initPopulateTypes : Selected Source : \t" + source + "\n");
            long category_id = glaEventBean.findCategoryId(selectNumberParameters.getSelectedAction(), source,
                    selectNumberParameters.getSelectedPlatform());
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
    public void manageEValues(SelectNumberParameters selectNumberParameters) {

            selectNumberParameters.getEntityValues().add(new EntityValues(selectNumberParameters.getSelectedKeys(),
                    selectNumberParameters.getSelectedentityValueTypes(), selectNumberParameters.getEvalue()));
    }

    @Override
    public void addDefaultEValues(SelectNumberParameters selectNumberParameters) {
        if ((selectNumberParameters.getEntityValues().isEmpty()))
            selectNumberParameters.getEntityValues().add(new EntityValues(selectNumberParameters.getSelectedKeys(),
                    selectNumberParameters.getSelectedentityValueTypes(), "ALL"));

    }

    @Override
    public void clearEValuesSpecifications(SelectNumberParameters selectNumberParameters) {
        selectNumberParameters.getEntityValues().clear();
    }

    @Override
    public void specifyNewUser(SelectNumberParameters selectNumberParameters) {
        selectNumberParameters.getUserSpecifications().add(new UserSearchSpecifications(selectNumberParameters.getSelecteduserSearchTypes(),
                selectNumberParameters.getSelectedUserString(), selectNumberParameters.getSelectedSearchType()));
    }

    @Override
    public void clearUserSpecifications(SelectNumberParameters selectNumberParameters) {
        selectNumberParameters.getUserSpecifications().clear();
    }

    @Override
    public void searchUser(SelectNumberParameters selectNumberParameters) {
        log.info("Within Search User Method : Indicator PreProcessing");
        GLAUserDao glauserBean = (GLAUserDao) appContext.getBean("glaUser");
        selectNumberParameters.setSearchResults(glauserBean.searchSimilarUserDetails(
                selectNumberParameters.getSelecteduserSearchTypes(), selectNumberParameters.getSearchUserString()));
        selectNumberParameters.getSearchResults().add(selectNumberParameters.getSearchUserString());

    }
    @Override
    public void specifyNewSession(SelectNumberParameters selectNumberParameters){
        selectNumberParameters.getSessionSpecifications().add(new SessionSpecifications(selectNumberParameters.getSelectedSearchType(),
                selectNumberParameters.getSelectedUserString()));

    }
    @Override
    public void clearSessionSpecifications(SelectNumberParameters selectNumberParameters){
        selectNumberParameters.getSessionSpecifications().clear();
    }
    @Override
    public void searchSession(SelectNumberParameters selectNumberParameters){
        log.info("Within Search Session Method : Indicator PreProcessing");
        GLAEventDao glaEventBean = (GLAEventDao) appContext.getBean("glaEvent");
        selectNumberParameters.setSearchResults(glaEventBean.searchSimilarSessionDetails(selectNumberParameters.getSelectedsessionSearchType(),
                selectNumberParameters.getSessionSearch()));
        selectNumberParameters.getSearchResults().add(selectNumberParameters.getSessionSearch());


    }
    @Override
    public void specifyNewTime(SelectNumberParameters selectNumberParameters){
        selectNumberParameters.getTimeSpecifications().add(new TimeSearchSpecifications(selectNumberParameters.getSelectedTimeType(),
                selectNumberParameters.getSelectedSearchStrings()));
    }
    @Override
    public void clearTimeSpecifications(SelectNumberParameters selectNumberParameters){
        selectNumberParameters.getTimeSpecifications().clear();
    }
    @Override
    public void searchTime(SelectNumberParameters selectNumberParameters){
        log.info("Within Search Time Method : Indicator PreProcessing");
        GLAEventDao glaEventBean = (GLAEventDao) appContext.getBean("glaEvent");
        selectNumberParameters.setSearchResults(glaEventBean.searchSimilarTimeDetails(selectNumberParameters.getSelectedTimeSearchType(),
                selectNumberParameters.getTimeSearch()));

    }
    @Override
    public void clearSearchSettings(SelectNumberParameters selectNumberParameters) {
        selectNumberParameters.getSearchResults().clear();
        selectNumberParameters.setSelectedSearchType("");

    }
    @Override
    public void addQuestion(SelectNumberParameters selectNumberParameters, NumberIndicator numberIndicator){
        numberIndicator.getGenQueries().add(new GenQuery(selectNumberParameters.getHql(), selectNumberParameters.getQuestionName(),0));
        log.info("Dumping Questions : \n");
        for(GenQuery gQ : numberIndicator.getGenQueries()){
            log.info(gQ.getQueryID() + "\t" + gQ.getQuery()+ "\n");
        }

    }
    @Override
    public void saveIndicator(NumberIndicator numberIndicator){
        log.info("Saving Indicator and all its Questions/Queries : STARTED");
        GLAIndicatorDao glaIndicatorBean = (GLAIndicatorDao) appContext.getBean("glaIndicator");
        GLAQueriesDao glaQueriesBean = (GLAQueriesDao) appContext.getBean("glaQueries");
        GLAIndicator glaIndicator = new GLAIndicator();
        GLAQueries glaQueries = new GLAQueries();
        glaIndicator.setIndicator_name(numberIndicator.getIndicatorName());
        glaQueries.setHql(numberIndicator.getGenQueries().get(0).getQuery());
        glaQueries.setQuestion_name(numberIndicator.getGenQueries().get(0).getQuestionName());
        numberIndicator.setIndicator_id(glaIndicatorBean.add(glaIndicator, glaQueries));
        for(int i =1 ; i < numberIndicator.getGenQueries().size(); i++) {
            glaQueriesBean.addWithExistingIndicator(new GLAQueries(numberIndicator.getGenQueries().get(i).getQuery(),
                            numberIndicator.getGenQueries().get(i).getQuestionName()),glaIndicator.getId());
        }
        log.info("Saving Indicator and all its Questions/Queries : ENDED");


    }

    @Override
    public void retreiveFromDB(NumberIndicator numberIndicator){

        GLAIndicatorDao glaIndicatorBean = (GLAIndicatorDao) appContext.getBean("glaIndicator");
        log.info("Retreive From DB : STARTED \n");
        log.info("ID : \t"+ numberIndicator.getIndicator_id());

        GLAIndicator glaIndicator = glaIndicatorBean.loadByIndicatorID(numberIndicator.getIndicator_id());
            log.info("GLA INDICATOR FROM DB : ID : \t"+ glaIndicator.getId());
            log.info("GLA INDICATOR FROM DB : Name : \t"+ glaIndicator.getIndicator_name());
            log.info("GLA INDICATOR FROM DB : PROPS ID : \t"+ glaIndicator.getGlaIndicatorProps().getId());
            log.info("GLA INDICATOR FROM DB : LEX TIME : \t"+ glaIndicator.getGlaIndicatorProps().getLast_executionTime());
            log.info("GLA INDICATOR FROM DB : EXEC COUNTER : \t"+ glaIndicator.getGlaIndicatorProps().getTotalExecutions());
            numberIndicator.reset();
            numberIndicator.setIndicator_id(glaIndicator.getId());
            numberIndicator.setIndicatorName(glaIndicator.getIndicator_name());
            numberIndicator.setGenIndicatorProps(glaIndicator.getGlaIndicatorProps().getId(),
                    glaIndicator.getGlaIndicatorProps().getLast_executionTime(),
                    glaIndicator.getGlaIndicatorProps().getTotalExecutions());
            Set<GLAQueries> genQueries = glaIndicator.getQueries();
            for (GLAQueries gQ : genQueries) {
                numberIndicator.getGenQueries().add(new GenQuery(gQ.getHql(),gQ.getQuestion_name(), gQ.getId()));
            }
    }

    @Override
    public void flushAll(NumberIndicator numberIndicator, SelectNumberParameters selectNumberParameters,
                                 IndicatorDefnOperationForm availableOperations){

        GLAEventDao glaEventBean = (GLAEventDao) appContext.getBean("glaEvent");
        GLAOperationsDao glaOperationsBean = (GLAOperationsDao) appContext.getBean("glaOperations");
        numberIndicator.reset();
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