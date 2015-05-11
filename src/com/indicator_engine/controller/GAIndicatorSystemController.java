
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
import com.indicator_engine.cewolf_graphgenerator.PageViewCountData;
import com.indicator_engine.dao.GLAIndicatorDao;
import com.indicator_engine.dao.GLAQueriesDao;
import com.indicator_engine.datamodel.GLAIndicator;
import com.indicator_engine.datamodel.GLAQueries;
import com.indicator_engine.misc.NumberChecks;
import com.indicator_engine.model.app.IndicatorRun;
import com.indicator_engine.model.app.SearchIndicatorForm;
import com.indicator_engine.model.indicator_system.IndicatorDeletionForm;
import com.indicator_engine.model.indicator_system.Number.GLAIndicatorJsonObject;
import com.indicator_engine.model.indicator_system.Number.GenQuery;
import com.indicator_engine.model.indicator_system.Number.NumberIndicator;
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
import java.util.Set;

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
            if(searchIndicatorForm.getSelectedIndicatorName() == null || searchIndicatorForm.getSelectedIndicatorName().isEmpty()) {
                model = new ModelAndView("indicator_system/viewall_indicators");
                model.addObject("searchIndicatorForm", searchIndicatorForm);
            }
            else{
                model = new ModelAndView("indicator_system/view_indicator_details");
                model.addObject("numberIndicator", reteriveIndicator(searchIndicatorForm.getSelectedIndicatorName()));
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
    public ModelAndView processIndicatorDeleteForm( @RequestParam String action,
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
            if (searchIndicatorForm.getSelectedIndicatorName() == null || searchIndicatorForm.getSelectedIndicatorName().isEmpty()) {
                model = new ModelAndView("indicator_system/delete_indicator");
                model.addObject("searchIndicatorForm", searchIndicatorForm);
            }
            else {

                NumberIndicator numberIndicator = reteriveIndicator(searchIndicatorForm.getSelectedIndicatorName());
                IndicatorDeletionForm indicatorDeletionForm = new IndicatorDeletionForm();
                indicatorDeletionForm.getDeletionList().add(numberIndicator.getIndicatorName());
                indicatorDeletionForm.setIndName(numberIndicator.getIndicatorName());
                for(GenQuery gQ  : numberIndicator.getGenQueries()) {
                    indicatorDeletionForm.getDeletionList().add(gQ.getQuestionName());
                }
                model = new ModelAndView("indicator_system/delete_indicator_details");
                model.addObject("indicatorDeletionForm", indicatorDeletionForm);
                model.addObject("numberIndicator", numberIndicator);
            }
        }
        return model;
    }

    @RequestMapping(value = "/processdelete", method = RequestMethod.POST)
    public ModelAndView processDeletion( @ModelAttribute("indicatorDeletionForm") IndicatorDeletionForm indicatorDeletionForm) {

        ModelAndView model = null;
        log.info("processDeletion : STARTED \n");
        if (indicatorDeletionForm.getSelectedList()== null ){
            NumberIndicator numberIndicator = reteriveIndicator(indicatorDeletionForm.getIndName());
            indicatorDeletionForm.getDeletionList().add(numberIndicator.getIndicatorName());
            for(GenQuery gQ  : numberIndicator.getGenQueries()) {
                indicatorDeletionForm.getDeletionList().add(gQ.getQuestionName());
            }
            model = new ModelAndView("indicator_system/delete_indicator_details");
            model.addObject("indicatorDeletionForm", indicatorDeletionForm);
            model.addObject("numberIndicator", numberIndicator);
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
                else{
                    GLAQueriesDao glaQueriesBean = (GLAQueriesDao) appContext.getBean("glaQueries");
                    List<GLAQueries> glaQueriesList = glaQueriesBean.searchQuestionsName(name, true);
                    if(glaQueriesList.size() > 0){
                        long question_id = glaQueriesBean.findQuestionID(glaQueriesList.get(0).getQuestion_name());
                        if(question_id != 0 )
                            glaQueriesBean.deleteQuestion(question_id);

                    }
                }
            }
        }
        return model;
    }
    @RequestMapping(value = "/trialrun", method = RequestMethod.GET)
    public String getIndicatorsTrialRun(Map<String, Object> model) {
        IndicatorRun indicatorRun = new IndicatorRun();
        GLAIndicatorDao glaIndicatorBean = (GLAIndicatorDao) appContext.getBean("glaIndicator");
        List<GLAIndicator> glaIndicatorList = glaIndicatorBean.displayall(null,null,false);
        for(GLAIndicator gI : glaIndicatorList){
            indicatorRun.getAvailableIndicators().add(gI.getIndicator_name());
        }
        model.put("indicatorRun",indicatorRun);

        return "indicator_system/trial_run";
    }

    @RequestMapping(value = "/trialrun", method = RequestMethod.POST)
    public ModelAndView processTrialRun( @Valid @ModelAttribute("indicatorRun") IndicatorRun indicatorRun, BindingResult bindingResult) {

        ModelAndView model = null;
        if (bindingResult.hasErrors()) {
            return new ModelAndView("indicator_system/viewall_indicators");
        }
        model = new ModelAndView("indicator_system/run_results");
        model.addObject("pageViews", PageViews);
        return model;
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

    private NumberIndicator reteriveIndicator(String indicatorName){
        log.info("reteriveIndicator : STARTED \n");
        GLAIndicatorDao glaIndicatorBean = (GLAIndicatorDao) appContext.getBean("glaIndicator");
        log.info("Retreive From DB : STARTED \n");
        log.info("Name : \t"+ indicatorName);
        long indicator_id = glaIndicatorBean.findIndicatorID(indicatorName);
        GLAIndicator glaIndicator = glaIndicatorBean.loadByIndicatorID(indicator_id);
        log.info("GLA INDICATOR FROM DB : ID : \t"+ glaIndicator.getId());
        log.info("GLA INDICATOR FROM DB : Name : \t"+ glaIndicator.getIndicator_name());
        log.info("GLA INDICATOR FROM DB : PROPS ID : \t"+ glaIndicator.getGlaIndicatorProps().getId());
        log.info("GLA INDICATOR FROM DB : LEX TIME : \t"+ glaIndicator.getGlaIndicatorProps().getLast_executionTime());
        log.info("GLA INDICATOR FROM DB : EXEC COUNTER : \t"+ glaIndicator.getGlaIndicatorProps().getTotalExecutions());
        NumberIndicator numberIndicator = new NumberIndicator();
        numberIndicator.setIndicator_id(glaIndicator.getId());
        numberIndicator.setIndicatorName(glaIndicator.getIndicator_name());
        numberIndicator.setGenIndicatorProps(glaIndicator.getGlaIndicatorProps().getId(),
                glaIndicator.getGlaIndicatorProps().getLast_executionTime(),
                glaIndicator.getGlaIndicatorProps().getTotalExecutions());
        Set<GLAQueries> genQueries = glaIndicator.getQueries();
        for (GLAQueries gQ : genQueries) {
            numberIndicator.getGenQueries().add(new GenQuery(gQ.getHql(),gQ.getQuestion_name(),gQ.getId()));
        }
        log.info("reteriveIndicator : ENDED \n");
        return numberIndicator;

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
            glaIndicatorList = glaIndicatorBean.loadByIndicatorByName(searchIndicatorForm.getSearchField());
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
