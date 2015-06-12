
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

package com.indicator_engine.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.indicator_engine.dao.GLAQuestionDao;
import com.indicator_engine.datamodel.GLAQuestion;
import com.indicator_engine.graphgenerator.cewolf.PageViewCountData;
import com.indicator_engine.dao.GLAIndicatorDao;
import com.indicator_engine.datamodel.GLAIndicator;
import com.indicator_engine.indicator_system.IndicatorPreProcessing;
import com.indicator_engine.misc.NumberChecks;
import com.indicator_engine.model.app.QuestionRun;
import com.indicator_engine.model.app.SearchIndicatorForm;
import com.indicator_engine.model.indicator_system.IndicatorDeletionForm;
import com.indicator_engine.model.indicator_system.Number.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Tanmaya Mahapatra on 23-03-2015.
 */
@Controller
@RequestMapping(value="/indicators")
@SuppressWarnings({"unused", "unchecked"})
public class GAIndicatorSystemController {

    @Autowired
    private ApplicationContext appContext;
    @Autowired
    private PageViewCountData PageViews;
    static Logger log = Logger.getLogger(GAIndicatorSystemController.class.getName());

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public ModelAndView getIndicatorsHome() {
        return new ModelAndView("indicator_system/indicators_home");
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String getNewIndicatorDefinitionHome(Map<String, Object> model) {
        IndicatorPreProcessing indicatorPreProcessor = (IndicatorPreProcessing) appContext.getBean("indicatorPreProcessor");
        model.put("selectNumberParameters",indicatorPreProcessor.initSelectNumberParametersObject());
        return "indicator_system/number/new_ui";
    }

    @RequestMapping(value = "/addEntities", method = RequestMethod.GET)
    public String getAddEntities(Map<String, Object> model) {
        EntitySpecification entitySpecification = new EntitySpecification();
        EntitySpecification entitySpecificationBean = (EntitySpecification) appContext.getBean("entitySpecifications");
        entitySpecification.setKeys(entitySpecificationBean.getKeys());
        model.put("entitySpecifications", entitySpecification);
        return "indicator_system/number/indicator_number_select_entity";
    }

    @RequestMapping(value = "/addEntities", method = RequestMethod.POST)
    public String processAddEntities(@RequestParam String action,
                                     @Valid @ModelAttribute("entitySpecifications") EntitySpecification entitySpecifications,
                                     BindingResult bindingResult,
                                     Map<String, Object> model) {

        if(action.equals("Add")){
            EntitySpecification entitySpecificationBean = (EntitySpecification) appContext.getBean("entitySpecifications");
            entitySpecificationBean.getEntityValues().add(new EntityValues(entitySpecifications.getSelectedKeys(),
                    entitySpecifications.getSelectedentityValueTypes(), entitySpecifications.getEvalue()));
            entitySpecifications.setSelectedKeys(null);
            entitySpecifications.setSelectedentityValueTypes(null);
            entitySpecifications.setEvalue(null);
            entitySpecifications.setEntityValues(entitySpecificationBean.getEntityValues());
            model.put("entitySpecifications", entitySpecifications);
        }

        if(action.equals("Delete")){
            EntitySpecification entitySpecificationBean = (EntitySpecification) appContext.getBean("entitySpecifications");
            entitySpecificationBean.getEntityValues().clear();
            EntitySpecification entitySpecification = new EntitySpecification();
            model.put("entitySpecifications", entitySpecification);
        }

        return "indicator_system/number/indicator_number_select_entity";
    }


    @RequestMapping(value = "/viewall", method = RequestMethod.GET)
    public String getIndicatorsViewAll(Map<String, Object> model) {
        SearchIndicatorForm searchIndicatorForm = new SearchIndicatorForm();
        model.put("searchIndicatorForm", searchIndicatorForm);
        return  "indicator_system/viewall_indicators";
    }

    @RequestMapping(value = "/viewall", method = RequestMethod.POST)
    public ModelAndView processIndicatorSearchForm( @RequestParam String action, @Valid @ModelAttribute("searchIndicatorForm") SearchIndicatorForm searchIndicatorForm, BindingResult bindingResult, HttpSession session) {

        ModelAndView model = null;
        if (bindingResult.hasErrors()) {
            return new ModelAndView("indicator_system/viewall_indicators");
        }
        if(action.equals("search")){

            processSearchParams(searchIndicatorForm);
            model = new ModelAndView("indicator_system/viewall_indicators");
            model.addObject("searchIndicatorForm", searchIndicatorForm);
        }
        else if(action.equals("load")){
            if(searchIndicatorForm.getSelectedQuestionName() == null || searchIndicatorForm.getSelectedQuestionName().isEmpty()) {
                model = new ModelAndView("indicator_system/viewall_indicators");
                model.addObject("searchIndicatorForm", searchIndicatorForm);
            }
            else{
                model = new ModelAndView("indicator_system/view_indicator_details");
                model.addObject("numberIndicator", retrieveQuestion(searchIndicatorForm.getSelectedQuestionName()));
            }
        }
        return model;
    }
    @RequestMapping(value = "/modify", method = RequestMethod.GET)
    public String getIndicatorsModify(Map<String, Object> model) {
        SearchIndicatorForm searchIndicatorForm = new SearchIndicatorForm();
        model.put("searchIndicatorForm", searchIndicatorForm);
        return  "indicator_system/modify_indicator";
    }
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String getIndicatorsDelete(Map<String, Object> model) {
        SearchIndicatorForm searchIndicatorForm = new SearchIndicatorForm();
        model.put("searchIndicatorForm", searchIndicatorForm);
        return  "indicator_system/delete_indicator";

    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ModelAndView processQuestionDeleteForm(@RequestParam String action,
                                                  @Valid @ModelAttribute("searchIndicatorForm") SearchIndicatorForm searchIndicatorForm,
                                                  BindingResult bindingResult,
                                                  HttpSession session) {

        ModelAndView model = null;
        if (bindingResult.hasErrors()) {
            return new ModelAndView("indicator_system/delete_indicator");
        }
        if (action.equals("search")) {
            processSearchParams(searchIndicatorForm);
            model = new ModelAndView("indicator_system/delete_indicator");
            model.addObject("searchIndicatorForm", searchIndicatorForm);
        }
        else if(action.equals("load")) {
            if (searchIndicatorForm.getSelectedQuestionName() == null || searchIndicatorForm.getSelectedQuestionName().isEmpty()) {
                model = new ModelAndView("indicator_system/delete_indicator");
                model.addObject("searchIndicatorForm", searchIndicatorForm);
            }
            else {

                Questions questions = retrieveQuestion(searchIndicatorForm.getSelectedQuestionName());
                IndicatorDeletionForm indicatorDeletionForm = new IndicatorDeletionForm();
                indicatorDeletionForm.getDeletionList().add(questions.getQuestionName());
                indicatorDeletionForm.setIndName(questions.getQuestionName());
                for(GenQuery gQ  : questions.getGenQueries()) {
                    indicatorDeletionForm.getDeletionList().add(gQ.getIndicatorName());
                }
                model = new ModelAndView("indicator_system/delete_indicator_details");
                model.addObject("indicatorDeletionForm", indicatorDeletionForm);
                model.addObject("numberIndicator", questions);
            }
        }
        return model;
    }

    @RequestMapping(value = "/processdelete", method = RequestMethod.POST)
    public ModelAndView processDeletion( @ModelAttribute("indicatorDeletionForm") IndicatorDeletionForm indicatorDeletionForm) {

        ModelAndView model = null;
        log.info("processDeletion : STARTED \n");
        if (indicatorDeletionForm.getSelectedList()== null ){
            Questions questions = retrieveQuestion(indicatorDeletionForm.getIndName());
            indicatorDeletionForm.getDeletionList().add(questions.getQuestionName());
            for(GenQuery gQ  : questions.getGenQueries()) {
                indicatorDeletionForm.getDeletionList().add(gQ.getIndicatorName());
            }
            model = new ModelAndView("indicator_system/delete_indicator_details");
            model.addObject("indicatorDeletionForm", indicatorDeletionForm);
            model.addObject("numberIndicator", questions);
            return model;
        }
        else{
            log.info("processDeletion : Working With Deletion Logic \n");
            GLAIndicatorDao glaIndicatorBean = (GLAIndicatorDao) appContext.getBean("glaIndicator");
            for(String name : indicatorDeletionForm.getSelectedList()){
                log.info("Selected Deletion Name :in  indicatorDeletionForm.getSelectedList()\t" + name+ "\n");
                List<GLAIndicator> glaIndicatorList = glaIndicatorBean.searchIndicatorsName(name,true, null, null, false);
                log.info("Searching for Name : in  Indicator List\t" + glaIndicatorList.size()+ "\n");
                if(glaIndicatorList.size() > 0){
                    log.info("Dumping Name : in  Indicator List\t" + glaIndicatorList.get(0).getIndicator_name()+ "\n");
                    long indicator_id = glaIndicatorBean.findIndicatorID(glaIndicatorList.get(0).getIndicator_name());
                    if(indicator_id != 0 )
                        glaIndicatorBean.deleteIndicator(indicator_id);
                }
                /*else{
                    GLAQueriesDao glaQueriesBean = (GLAQueriesDao) appContext.getBean("glaQueries");
                    List<GLAQueries> glaQueriesList = glaQueriesBean.searchQuestionsName(name, true);
                    if(glaQueriesList.size() > 0){
                        long question_id = glaQueriesBean.findQuestionID(glaQueriesList.get(0).getQuestion_name());
                        if(question_id != 0 )
                            glaQueriesBean.deleteQuestion(question_id);

                    }
                }*/
            }
        }
        return model;
    }
    @RequestMapping(value = "/trialrun", method = RequestMethod.GET)
    public String getQuestionsTrialRun(Map<String, Object> model) {
        QuestionRun questionRun = new QuestionRun();
        GLAQuestionDao glaQuestionsBean = (GLAQuestionDao) appContext.getBean("glaQuestions");
        List<GLAQuestion> glaQuestionList = glaQuestionsBean.displayAll(null, null, false);
        for(GLAQuestion gQ : glaQuestionList){
            questionRun.getAvailableQuestions().add(gQ.getQuestion_name());
        }
        model.put("questionRun", questionRun);
        return "indicator_system/trial_run";
    }

    @RequestMapping(value = "/trialrun", method = RequestMethod.POST)
    public String processTrialRun( @Valid @ModelAttribute("questionRun") QuestionRun questionRun,
                                         BindingResult bindingResult,
                                         Map<String, Object> model) {

        if (bindingResult.hasErrors()) {
            return "indicator_system/trial_run";
        }

        if(questionRun.getSelectedChartEngine().equals("JFreeGraph")) {
            model.put("chartType", questionRun.getSelectedChartType());
            model.put("questionName", questionRun.getSelectedQuestion());
            return "indicator_system/run_results";
        }
        else if (questionRun.getSelectedChartEngine().equals("CEWOLF")) {
            model.put("pageViews", PageViews);
            return "indicator_system/run_results_cewolf";
        }

       return null;
    }

    @RequestMapping(value = "/fetchExistingIndicatorsData.web", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    String fetchIndicatorData(HttpServletRequest request) throws IOException {

        GLAIndicatorDao glaIndicatorBean = (GLAIndicatorDao) appContext.getBean("glaIndicator");
        List<GLAIndicator> glaIndicatorList = null;
        List<GLAIndicator> pageGLAindicatorList = new ArrayList<>();
        Integer idisplayStart = 0;
        Integer iSortingCols =0;
        if(null != request.getParameter("iSortingCols"))
            iSortingCols = Integer.valueOf(request.getParameter("iSortingCols"));
        GLAIndicatorJsonObject glaIndicatorJsonObject = new GLAIndicatorJsonObject();
        if (null != request.getParameter("iDisplayStart")) {
            idisplayStart = Integer.valueOf(request.getParameter("iDisplayStart"));
            log.info("iDisplayStart : \t" + request.getParameter("iDisplayStart") + "\n");
        }
        //Fetch search parameter
        String searchParameter = request.getParameter("sSearch");
        log.info("sSearch : \t"+ searchParameter+"\n");
        //Fetch Page display length
        Integer pageDisplayLength = Integer.parseInt(request.getParameter("iDisplayLength"));
        log.info("iDisplayLength : \t"+ pageDisplayLength+"\n");
        //Create page list data
        if(searchParameter == null || searchParameter.isEmpty()) {
            String colName = null;
            String sortDirection =null;
            if(iSortingCols == 1 ) {
                Integer isortCol = Integer.valueOf(request.getParameter("iSortCol_0"));
                sortDirection = request.getParameter("sSortDir_0");
                if (isortCol == 0)
                    colName = "id";
                else if (isortCol == 1)
                    colName = "indicator_name";
                else if (isortCol == 2)
                    colName = "short_name";
                glaIndicatorList = glaIndicatorBean.displayall(colName,sortDirection,true);
            }
            else
                glaIndicatorList = glaIndicatorBean.displayall(colName,sortDirection,false);
            if(idisplayStart != -1){
                Integer endRange = idisplayStart+pageDisplayLength;
                if(endRange >glaIndicatorList.size())
                    endRange = glaIndicatorList.size();
                for(int i=idisplayStart; i<endRange; i++){
                    pageGLAindicatorList.add(glaIndicatorList.get(i));
                }
            }
            //Set Total display record
            glaIndicatorJsonObject.setiTotalDisplayRecords(glaIndicatorBean.getTotalIndicators());
            //Set Total record
            glaIndicatorJsonObject.setiTotalRecords(glaIndicatorBean.getTotalIndicators());
            glaIndicatorJsonObject.setAaData(pageGLAindicatorList);
        }
        else {
            String colName = null;
            String sortDirection =null;
            if(iSortingCols == 1 ) {
                Integer isortCol = Integer.valueOf(request.getParameter("iSortCol_0"));
                sortDirection = request.getParameter("sSortDir_0");
                if (isortCol == 0)
                    colName = "id";
                else if (isortCol == 1)
                    colName = "indicator_name";
                else if (isortCol == 2)
                    colName = "short_name";
                glaIndicatorList = glaIndicatorBean.searchIndicatorsName(searchParameter, false,colName,sortDirection,true);
            }
            else
                glaIndicatorList = glaIndicatorBean.searchIndicatorsName(searchParameter, false,colName,sortDirection,false);
            if(idisplayStart != -1) {
                Integer endRange = idisplayStart+pageDisplayLength;
                Integer startRange = idisplayStart;
                if(startRange > glaIndicatorList.size())
                    startRange = 0;
                if (endRange > glaIndicatorList.size())
                    endRange = glaIndicatorList.size();
                for (int i = startRange; i <endRange; i++) {
                    pageGLAindicatorList.add(glaIndicatorList.get(i));
                }
            }
            //Set Total display record
            glaIndicatorJsonObject.setiTotalDisplayRecords(glaIndicatorList.size());
            //Set Total record
            glaIndicatorJsonObject.setiTotalRecords(glaIndicatorList.size());
            glaIndicatorJsonObject.setAaData(pageGLAindicatorList);
        }
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        return gson.toJson(glaIndicatorJsonObject);
    }

    private Questions retrieveQuestion(String questionName){
        log.info("reteriveQuestion : STARTED \n");
        GLAQuestionDao glaQuestionsBean = (GLAQuestionDao) appContext.getBean("glaQuestions");
        log.info("Retreive From DB : STARTED \n");
        log.info("Name : \t"+ questionName);
        long question_id = glaQuestionsBean.findQuestionID(questionName);
        GLAQuestion glaQuestion = glaQuestionsBean.loadByQuestionID(question_id);
        Questions questions = new Questions();
        questions.setQuestionId(glaQuestion.getId());
        questions.setQuestionName(glaQuestion.getQuestion_name());
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
        return questions;

    }
    private void processSearchParams(SearchIndicatorForm searchIndicatorForm){
        log.info("processSearchParams : STARTED \n");
        GLAIndicatorDao glaIndicatorBean = (GLAIndicatorDao) appContext.getBean("glaIndicator");
        GLAIndicator glaIndicator = null;
        List<GLAIndicator> glaIndicatorList;
        if(NumberChecks.isNumeric(searchIndicatorForm.getSearchField()) && searchIndicatorForm.getSelectedSearchType().equals("ID")) {
            glaIndicator = glaIndicatorBean.loadByIndicatorID(Long.parseLong(searchIndicatorForm.getSearchField()));
            if(glaIndicator != null) {
                log.info("GLA INDICATOR FROM DB SEARCH: ID : \t"+ glaIndicator.getId());
                searchIndicatorForm.getSearchResults().add(glaIndicator.getIndicator_name());
            }
        }
        else if (!NumberChecks.isNumeric(searchIndicatorForm.getSearchField()) && searchIndicatorForm.getSelectedSearchType().equals("Indicator Name")) {
            glaIndicatorList = glaIndicatorBean.loadByIndicatorByName(searchIndicatorForm.getSearchField(),false);
            if(glaIndicatorList != null) {
                for(GLAIndicator gI : glaIndicatorList){
                    log.info("GLA INDICATOR FROM DB SEARCH: NAME : \t"+ gI.getIndicator_name());
                    searchIndicatorForm.getSearchResults().add(gI.getIndicator_name());
                }
            }
        }
        log.info("processSearchParams : ENDED \n");
    }
}
