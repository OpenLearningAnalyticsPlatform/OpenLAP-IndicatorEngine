
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

package com.indicator_engine.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.indicator_engine.dao.*;
import com.indicator_engine.datamodel.GLAIndicator;
import com.indicator_engine.datamodel.GLAQuestion;
import com.indicator_engine.graphgenerator.cewolf.PageViewCountData;
import com.indicator_engine.indicator_system.IndicatorPreProcessing;
import com.indicator_engine.indicator_system.Number.OperationNumberProcessorDao;
import com.indicator_engine.misc.NumberChecks;
import com.indicator_engine.model.app.SearchQuestionForm;
import com.indicator_engine.model.indicator_system.Number.*;
import de.rwthaachen.openlap.analyticsengine.core.dtos.response.IndicatorPreviewResponse;
import de.rwthaachen.openlap.dataset.OpenLAPPortConfig;
import de.rwthaachen.openlap.dataset.OpenLAPPortMapping;
import org.apache.log4j.Logger;
import org.jfree.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by Tanmaya Mahapatra on 23-03-2015.
 */
@Controller
@Scope("session")
@SessionAttributes({"loggedIn", "userName", "sid", "activationStatus","role", "admin_access"})
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

    @RequestMapping(value = "/define_new", method = RequestMethod.GET)
    public String getNewIndicatorDefinitionHome(Map<String, Object> model) {
        IndicatorPreProcessing indicatorPreProcessor = (IndicatorPreProcessing) appContext.getBean("indicatorPreProcessor");
        EntitySpecification entitySpecificationBean = (EntitySpecification) appContext.getBean("entitySpecifications");
        SelectNumberParameters selectNumberParameters = indicatorPreProcessor.initSelectNumberParametersObject();
        model.put("selectNumberParameters", selectNumberParameters);
//        return "indicator_system/number/question_indicator_editor";
        return "indicator_system/editor/define_new";
    }

    @RequestMapping(value = "/view_existing", method = RequestMethod.GET)
    public ModelAndView viewExistingIndicator() {
        return  new ModelAndView("indicator_system/editor/view_existing");
    }

    @RequestMapping(value = "/addNewIndicator", method = RequestMethod.GET)
    public @ResponseBody
    String  addNewIndicator(@RequestParam(value="type", required = true) String type, Model model) {
        Gson gson = new Gson();
        EntitySpecification entitySpecificationBean = (EntitySpecification) appContext.getBean("entitySpecifications");
        entitySpecificationBean.reset();
        entitySpecificationBean.getCurrentIndicator().setIndicatorType(type);
        return gson.toJson(entitySpecificationBean.getCurrentQuestion());
    }

    @RequestMapping(value = "/refreshQuestionSummary", method = RequestMethod.GET)
    public @ResponseBody
    String  refreshQuestionSummary(@RequestParam(value="indName" ,required = false)String indicatorName, Model model) {
        Gson gson = new Gson();
        EntitySpecification entitySpecificationBean = (EntitySpecification) appContext.getBean("entitySpecifications");
        if(indicatorName == null) {
            return gson.toJson(entitySpecificationBean.getCurrentQuestion());
        }
        else {
            SessionQuestion sessionQuestion = entitySpecificationBean.getCurrentQuestion();
            for(SessionIndicator sessionIndicator : sessionQuestion.getSessionIndicators()){
                //if(sessionIndicator.getIndicatorName().equals(indicatorName) && !sessionIndicator.getGenIndicatorProps().isComposite())
                if(sessionIndicator.getIndicatorName().equals(indicatorName) && sessionIndicator.getIndicatorType().equals("simple"))
                    return gson.toJson(sessionIndicator);
            }
        }
        return gson.toJson(null);
    }




    @RequestMapping(value = "/addEntity", method = RequestMethod.GET)
    public @ResponseBody
    String  addEntity(@RequestParam(value="key", required = true) String key,
                      @RequestParam(value="value", required = true) String value,
                      @RequestParam(value="title", required = true) String title,
                      Model model) {
        Gson gson = new Gson();
        EntitySpecification entitySpecificationBean = (EntitySpecification) appContext.getBean("entitySpecifications");

        boolean hasEntity = false;
        String indType = entitySpecificationBean.getCurrentIndicator().getIndicatorType();
        if(indType.equals("simple")) {

            if(entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getIndicatorDataset().size()<= 0)
                entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getIndicatorDataset().put("0", new IndicatorDataset());

            Iterator<EntityValues> entityValues = entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getIndicatorDataset().get("0").getEntityValues().iterator();

            while(entityValues.hasNext()) {
                EntityValues aEntityValue = entityValues.next();
                if (aEntityValue.getKey().equals(key) && aEntityValue.geteValues().equals(value)) {
                    hasEntity = true;
                    break;
                }
            }
            if (!hasEntity) {
                entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getIndicatorDataset().get("0").getEntityValues().add(new EntityValues(key, value, title));
            }

            return gson.toJson(entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getIndicatorDataset().get("0").getEntityValues());
        }
        else if(entitySpecificationBean.getCurrentIndicator().getIndicatorType() == "composite") {

            //TODO adding entity for composite indicator type

            return gson.toJson(entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getIndicatorDataset().get("0").getEntityValues());
        }
        else if(entitySpecificationBean.getCurrentIndicator().getIndicatorType() == "multianalysis") {

            //TODO adding entity for multianalysis indicator type

            return gson.toJson(entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getIndicatorDataset().get("0").getEntityValues());
        }

        return null;
    }

    @RequestMapping(value = "/getEntities", method = RequestMethod.GET)
    public @ResponseBody
    String  getEntities(Model model, @RequestParam(value="size", required = false) String size) {
        Gson gson = new Gson();
        EntitySpecification entitySpecificationBean = (EntitySpecification) appContext.getBean("entitySpecifications");

        if(entitySpecificationBean.getCurrentIndicator().getIndicatorType().equals("simple")) {
            if(size != null && size.equals("Y")) {
                if(!entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getIndicatorDataset().containsKey("0"))
                    entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getIndicatorDataset().put("0", new IndicatorDataset());

                return gson.toJson(entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getIndicatorDataset().get("0").getEntityValues().size());
            }
            return gson.toJson(entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getIndicatorDataset().get("0").getEntityValues());
        }
        else if(entitySpecificationBean.getCurrentIndicator().getIndicatorType() == "composite") {

            //TODO adding entity for composite indicator type
            return gson.toJson(entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getIndicatorDataset().get("0").getEntityValues());
        }
        else if(entitySpecificationBean.getCurrentIndicator().getIndicatorType() == "multianalysis") {

            //TODO adding entity for multianalysis indicator type
            return gson.toJson(entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getIndicatorDataset().get("0").getEntityValues());
        }

        return null;
    }

    @RequestMapping(value = "/deleteEntities", method = RequestMethod.GET)
    public @ResponseBody
    String  deleteEntities(Model model, @RequestParam(value="filter", required = true) String filter) {
        Gson gson = new Gson();
        EntitySpecification entitySpecificationBean = (EntitySpecification) appContext.getBean("entitySpecifications");


        if(entitySpecificationBean.getCurrentIndicator().getIndicatorType().equals("simple")) {
            if(entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getIndicatorDataset().containsKey("0")){
                if(filter.equals("ALL"))
                    entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getIndicatorDataset().get("0").getEntityValues().clear();
                else {
                    String[] filterTerms = filter.split("_");
                    Iterator<EntityValues> entityValues = entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getIndicatorDataset().get("0").getEntityValues().iterator();
                    while(entityValues.hasNext()) {
                        EntityValues aEntityValue = entityValues.next();
                        if (aEntityValue.getKey().equals(filterTerms[0]) && aEntityValue.geteValues().equals(filterTerms[1])) {
                            entityValues.remove();
                            break;
                        }
                    }
                }
            }
            else{
                entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getIndicatorDataset().put("0", new IndicatorDataset());
            }
            return gson.toJson(entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getIndicatorDataset().get("0").getEntityValues());
        }
        else if(entitySpecificationBean.getCurrentIndicator().getIndicatorType() == "composite") {

            //TODO deleting entity for composite indicator type
            return gson.toJson(entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getIndicatorDataset().get("0").getEntityValues());
        }
        else if(entitySpecificationBean.getCurrentIndicator().getIndicatorType() == "multianalysis") {

            //TODO deleting entity for multianalysis indicator type
            return gson.toJson(entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getIndicatorDataset().get("0").getEntityValues());
        }

        return null;
    }

    @RequestMapping(value = "/addUserFilter", method = RequestMethod.GET)
    public @ResponseBody
    String  addUserFilter(@RequestParam(value="isUserData", defaultValue = "false") String isUserData, Model model) {
        EntitySpecification entitySpecificationBean = (EntitySpecification) appContext.getBean("entitySpecifications");
        Gson gson = new Gson();
        boolean isUserDataSet = Boolean.parseBoolean(isUserData);


        if(entitySpecificationBean.getCurrentIndicator().getIndicatorType().equals("simple")) {
            if(!entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getIndicatorDataset().containsKey("0"))
                entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getIndicatorDataset().put("0", new IndicatorDataset());

            entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getIndicatorDataset().get("0").getUserSpecifications().clear();
            if(isUserDataSet) {
                UserSearchSpecifications aUserSearchSpecifications = new UserSearchSpecifications("isUserDataSet", isUserData, "", "", "");
                entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getIndicatorDataset().get("0").getUserSpecifications().add(0, aUserSearchSpecifications);
            }

            return gson.toJson(entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getIndicatorDataset().get("0").getUserSpecifications());
        }
        else if(entitySpecificationBean.getCurrentIndicator().getIndicatorType() == "composite") {
            //TODO for composite indicator type
        }
        else if(entitySpecificationBean.getCurrentIndicator().getIndicatorType() == "multianalysis") {
            //TODO for multianalysis indicator type
        }

        return null;
    }

    @RequestMapping(value = "/getUserFilters", method = RequestMethod.GET)
    public @ResponseBody
    String  getUserFilters(Model model) {
        Gson gson = new Gson();
        EntitySpecification entitySpecificationBean = (EntitySpecification) appContext.getBean("entitySpecifications");

        if(!entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getIndicatorDataset().containsKey("0"))
            entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getIndicatorDataset().put("0", new IndicatorDataset());

        if(entitySpecificationBean.getCurrentIndicator().getIndicatorType().equals("simple")) {
            return gson.toJson(entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getIndicatorDataset().get("0").getUserSpecifications());
        }
        else if(entitySpecificationBean.getCurrentIndicator().getIndicatorType() == "composite") {
            //TODO for composite indicator type
        }
        else if(entitySpecificationBean.getCurrentIndicator().getIndicatorType() == "multianalysis") {
            //TODO for multianalysis indicator type
        }

        return null;
    }

    @RequestMapping(value = "/deleteUserFilters", method = RequestMethod.GET)
    public @ResponseBody
    String  deleteUserFilters(Model model,  @RequestParam(value="filter", required = true) String filter) {
        Gson gson = new Gson();
        EntitySpecification entitySpecificationBean = (EntitySpecification) appContext.getBean("entitySpecifications");

        if(entitySpecificationBean.getCurrentIndicator().getIndicatorType().equals("simple")) {
            if(!entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getIndicatorDataset().containsKey("0"))
                entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getIndicatorDataset().put("0", new IndicatorDataset());

            entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getIndicatorDataset().get("0").getUserSpecifications().clear();
            return gson.toJson(entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getIndicatorDataset().get("0").getUserSpecifications());
        }
        else if(entitySpecificationBean.getCurrentIndicator().getIndicatorType() == "composite") {
            //TODO for composite indicator type
        }
        else if(entitySpecificationBean.getCurrentIndicator().getIndicatorType() == "multianalysis") {
            //TODO for multianalysis indicator type
        }

        return null;
    }

    @RequestMapping(value = "/searchTime", method = RequestMethod.GET)
    public @ResponseBody
    String  searchTime(@RequestParam(value="searchTime", required = true) String time,
                        @RequestParam(value="timeType", required = true) String timeType,
                        Model model) {
        GLAEventDao glaEventBean = (GLAEventDao) appContext.getBean("glaEvent");
        Gson gson = new Gson();
        List<Timestamp> searchResults = glaEventBean.searchSimilarTimeDetails(timeType, time);
        return gson.toJson(searchResults);
    }

    @RequestMapping(value = "/addTimeFilter", method = RequestMethod.GET)
    public @ResponseBody
    String  addTimeFilter(@RequestParam(value="time", required = true) String time,
                          @RequestParam(value="timeType", required = true) String timeType,
                          Model model) {
        EntitySpecification entitySpecificationBean = (EntitySpecification) appContext.getBean("entitySpecifications");
        Gson gson = new Gson();

        if(entitySpecificationBean.getCurrentIndicator().getIndicatorType().equals("simple")) {
            boolean hasTimeFilter = false;
            int timeFilterIndex = -1;

            if(!entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getIndicatorDataset().containsKey("0"))
                entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getIndicatorDataset().put("0", new IndicatorDataset());

            Iterator<TimeSearchSpecifications> timeSearchSpecifications = entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getIndicatorDataset().get("0").getTimeSpecifications().iterator();

            while(timeSearchSpecifications.hasNext()) {
                TimeSearchSpecifications aTimeSearchSpecifications = timeSearchSpecifications.next();

                if (aTimeSearchSpecifications.getType().equals(timeType)) {
                    hasTimeFilter = true;
                    timeFilterIndex = entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getIndicatorDataset().get("0").getTimeSpecifications().indexOf(aTimeSearchSpecifications);
                    break;
                }
            }

            if (!hasTimeFilter)
                entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getIndicatorDataset().get("0").getTimeSpecifications().add(new TimeSearchSpecifications(timeType, time));
            else
                entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getIndicatorDataset().get("0").getTimeSpecifications().get(timeFilterIndex).setTimestamp(time);

            return gson.toJson(entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getIndicatorDataset().get("0").getTimeSpecifications());
        }
        else if(entitySpecificationBean.getCurrentIndicator().getIndicatorType() == "composite") {
            //TODO for composite indicator type
        }
        else if(entitySpecificationBean.getCurrentIndicator().getIndicatorType() == "multianalysis") {
            //TODO for multianalysis indicator type
        }

        return null;
    }

    @RequestMapping(value = "/getTimeFilters", method = RequestMethod.GET)
    public @ResponseBody
    String  getTimeFilters(Model model) {
        Gson gson = new Gson();
        EntitySpecification entitySpecificationBean = (EntitySpecification) appContext.getBean("entitySpecifications");

        if(entitySpecificationBean.getCurrentIndicator().getIndicatorType().equals("simple")) {
            if(!entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getIndicatorDataset().containsKey("0"))
                entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getIndicatorDataset().put("0", new IndicatorDataset());

            return gson.toJson(entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getIndicatorDataset().get("0").getTimeSpecifications());
        }
        else if(entitySpecificationBean.getCurrentIndicator().getIndicatorType() == "composite") {
            //TODO for composite indicator type
        }
        else if(entitySpecificationBean.getCurrentIndicator().getIndicatorType() == "multianalysis") {
            //TODO for multianalysis indicator type
        }

        return null;

    }

    @RequestMapping(value = "/deleteTimeFilters", method = RequestMethod.GET)
    public @ResponseBody
    String  deleteTimeFilters(Model model, @RequestParam(value="filter", required = true) String filter) {
        Gson gson = new Gson();
        EntitySpecification entitySpecificationBean = (EntitySpecification) appContext.getBean("entitySpecifications");
        String[] filterTerms = filter.split("_");

        if(entitySpecificationBean.getCurrentIndicator().getIndicatorType().equals("simple")) {

            if(!entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getIndicatorDataset().containsKey("0"))
                entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getIndicatorDataset().put("0", new IndicatorDataset());

            Iterator<TimeSearchSpecifications> timeSearchSpecifications = entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getIndicatorDataset().get("0").getTimeSpecifications().iterator();

            while(timeSearchSpecifications.hasNext()) {
                TimeSearchSpecifications aTimeSearchSpecifications = timeSearchSpecifications.next();

                if (aTimeSearchSpecifications.getType().equals(filterTerms[0])) {
                    timeSearchSpecifications.remove();
                    break;
                }
            }

            return gson.toJson(entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getIndicatorDataset().get("0").getTimeSpecifications());
        }
        else if(entitySpecificationBean.getCurrentIndicator().getIndicatorType() == "composite") {
            //TODO for composite indicator type
        }
        else if(entitySpecificationBean.getCurrentIndicator().getIndicatorType() == "multianalysis") {
            //TODO for multianalysis indicator type
        }

        return null;
    }

//    @RequestMapping(value = "/addSessionFilter", method = RequestMethod.GET)
//    public @ResponseBody
//    String  addSessionFilter(@RequestParam(value="sessionData", required = true) String sessionData,
//                          @RequestParam(value="searchType", required = true) String searchType,
//
//                          Model model) {
//        EntitySpecification entitySpecificationBean = (EntitySpecification) appContext.getBean("entitySpecifications");
//        Gson gson = new Gson();
//        entitySpecificationBean.getSessionSpecifications().add(new SessionSpecifications(searchType, sessionData));
//
//        return gson.toJson(entitySpecificationBean.getSessionSpecifications());
//
//    }
//    @RequestMapping(value = "/getSessionFilters", method = RequestMethod.GET)
//    public @ResponseBody
//    String  getSessionFilters(Model model) {
//        Gson gson = new Gson();
//        EntitySpecification entitySpecificationBean = (EntitySpecification) appContext.getBean("entitySpecifications");
//        return gson.toJson(entitySpecificationBean.getSessionSpecifications());
//
//    }
//    @RequestMapping(value = "/deleteSessionFilters", method = RequestMethod.GET)
//    public @ResponseBody
//    String  deleteSessionFilters(Model model, @RequestParam(value="filter", required = true) String filter) {
//        Gson gson = new Gson();
//        EntitySpecification entitySpecificationBean = (EntitySpecification) appContext.getBean("entitySpecifications");
//        if(filter.equals("ALL"))
//            entitySpecificationBean.getSessionSpecifications().clear();
//        else {
//            String[] filterTerms = filter.split("_");
//            for (Iterator<SessionSpecifications> sessionSpecifications = entitySpecificationBean.getSessionSpecifications().iterator(); sessionSpecifications.hasNext(); ) {
//                SessionSpecifications aSessionSpecifications = sessionSpecifications.next();
//                if (aSessionSpecifications.getSession().equals(filterTerms[0]) && aSessionSpecifications.getType().equals(filterTerms[1])) {
//                    sessionSpecifications.remove();
//                    break;
//                }
//            }
//        }
//        return gson.toJson(entitySpecificationBean.getSessionSpecifications());
//    }


    // Tanmaya code
    /*@RequestMapping(value = "/refreshGraph", method = RequestMethod.GET)
    public @ResponseBody
    String  refreshGraph(@RequestParam(value="questionName", required = true) String questionName,
                         @RequestParam(value="indicatorName", required = true) String indicatorName,
                         @RequestParam(value="graphType", required = true) String graphType,
                         @RequestParam(value="graphEngine", required = true) String graphEngine,
                         Model model) {
        Gson gson = new Gson();
        EntitySpecification entitySpecificationBean = (EntitySpecification) appContext.getBean("entitySpecifications");
        GLAEntityDao glaEntityBean = (GLAEntityDao) appContext.getBean("glaEntity");
        GLACategoryDao glaCategoryBean = (GLACategoryDao) appContext.getBean("glaCategory");
        OperationNumberProcessorDao operationNumberProcessorBean =  (OperationNumberProcessorDao) appContext.getBean("operationNumberProcessor");
        entitySpecificationBean.setSelectedChartType(graphType);
        entitySpecificationBean.setSelectedChartEngine(graphEngine);
        entitySpecificationBean.setQuestionName(questionName);
        entitySpecificationBean.setIndicatorName(indicatorName);
        entitySpecificationBean.setComposite(false);
        GLACategory glaCategory = glaCategoryBean.loadCategoryByName(entitySpecificationBean.getSelectedMinor());
        entitySpecificationBean.setSelectedMajor(glaCategory.getMajor());
        entitySpecificationBean.setSelectedType(glaCategory.getType());
        operationNumberProcessorBean.computeResult(entitySpecificationBean);
        long result = glaEntityBean.findNumber(entitySpecificationBean.getHql());
        log.info("Dumping Result \n" + result);
        return gson.toJson("true");
    }*/

 /*   @RequestMapping(value = "/loadQuestion", method = RequestMethod.GET)
    public @ResponseBody
    String loadQuestion(@RequestParam(value="name", required = true) String name) {
        Questions question = retrieveQuestion(name);
        String questionName = question.getQuestionName();
        Gson gson = new Gson();

        for( SessionIndicator query : question.getGenQueries()){
            IndicatorXMLData data = query.getIndicatorXMLData();
            finalizeIndicator(questionName, query.getIndicatorName(), query.getGenIndicatorProps().getChartType(), query.getGenIndicatorProps().getChartEngine(),
                    null, data.getAnalyticsMethodId(), data.getQueryToMethodConfig(), data.getMethodToVisualizationConfig(), data.getRetrievableObjects());
        }

        GLAQuestionDao glaQuestionsBean = (GLAQuestionDao) appContext.getBean("glaQuestions");
        long question_id = glaQuestionsBean.findQuestionID(name);
        GLAQuestion glaQuestion = glaQuestionsBean.loadByQuestionID(question_id);
        Questions questions = new Questions();
        questions.setQuestionId(glaQuestion.getId());
        questions.setQuestionName(glaQuestion.getQuestion_name());
        questions.setTotalExecutions(glaQuestion.getGlaQuestionProps().getTotalExecutions());
        questions.setLast_executionTime(glaQuestion.getGlaQuestionProps().getLast_executionTime());
        questions.setLast_executionTime(glaQuestion.getGlaQuestionProps().getLast_executionTime());
        questions.setUserName(glaQuestion.getGlaQuestionProps().getUserName());
        questions.setNumIndicators(glaQuestion.getIndicators_num());
        for( GLAIndicator glaIndicators : glaQuestion.getGlaIndicators()){
            SessionIndicator genQuery = new SessionIndicator(glaIndicators.getHql(),glaIndicators.getIndicator_name(),
                    glaIndicators.getId());
            genQuery.setGenIndicatorProps(glaIndicators.getGlaIndicatorProps().getId(),glaIndicators.getGlaIndicatorProps().getLast_executionTime(),
                    glaIndicators.getGlaIndicatorProps().getTotalExecutions(),glaIndicators.getGlaIndicatorProps().getChartType(),
                    glaIndicators.getGlaIndicatorProps().getChartEngine(), glaIndicators.getGlaIndicatorProps().getUserName(), glaIndicators.getGlaIndicatorProps().getJson_data());
            questions.getGenQueries().add(genQuery);
        }
        return questions;



        Gson gson = new Gson();
        return gson.toJson(question);
    }*/


    @RequestMapping(value = "/finalize", method = RequestMethod.GET)
    public @ResponseBody
    String  finalizeIndicator(@RequestParam(value="goalId", required = true) String goalId,
                              @RequestParam(value="questionName", required = true) String questionName,
                              @RequestParam(value="indicatorName", required = true) String indicatorName,
                              @RequestParam(value="graphType", required = true) String graphType,
                              @RequestParam(value="graphEngine", required = true) String graphEngine,
                              @RequestParam(value="indicatorIdentifier", required = true) String indicatorIdentifier,
                              @RequestParam(value="analyticsMethod", required = true) String analyticsMethod,
                              @RequestParam(value="methodMappings", required = true) String methodMappings,
                              @RequestParam(value="visualizationMappings", required = true) String visualizationMappings,
                              @RequestParam(value="selectedMethods", required = true) String selectedMethodDataColumns,
                              Model model) {
        Gson gson = new Gson();

        List<String> entityDisplayObjects = new ArrayList<>();
        if(selectedMethodDataColumns != null && !selectedMethodDataColumns.isEmpty())
            entityDisplayObjects = Arrays.asList(selectedMethodDataColumns.split("\\s*,\\s*"));

        Type olapPortMappingType = new TypeToken<List<OpenLAPPortMapping>>(){}.getType();
        List<OpenLAPPortMapping> methodOpenLAPPortMappingList = gson.fromJson(methodMappings, olapPortMappingType);
        OpenLAPPortConfig queryToMethodConfig = new OpenLAPPortConfig();
        for (OpenLAPPortMapping methodPortMapping : methodOpenLAPPortMappingList) {
            queryToMethodConfig.getMapping().add(methodPortMapping);
        }

        List<OpenLAPPortMapping> visualizationOpenLAPPortMappingList = gson.fromJson(visualizationMappings, olapPortMappingType);
        OpenLAPPortConfig methodToVisualizationConfig = new OpenLAPPortConfig();
        for (OpenLAPPortMapping visualizationPortMapping : visualizationOpenLAPPortMappingList) {
            methodToVisualizationConfig.getMapping().add(visualizationPortMapping);
        }

        //GLACategoryDao glaCategoryBean = (GLACategoryDao) appContext.getBean("glaCategory");
        OperationNumberProcessorDao operationNumberProcessorBean =  (OperationNumberProcessorDao) appContext.getBean("operationNumberProcessor");
        EntitySpecification entitySpecificationBean = (EntitySpecification) appContext.getBean("entitySpecifications");
        //entitySpecificationBean.setComposite(false);

        entitySpecificationBean.setGoalId(Long.parseLong(goalId));
        entitySpecificationBean.setQuestionName(questionName);
        entitySpecificationBean.getCurrentIndicator().setIndicatorName(indicatorName);

        //entitySpecificationBean.setAnalyticsMethodId(Long.parseLong(analyticsMethod));
        entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getAnalyticsMethodId().put("0",Long.parseLong(analyticsMethod));

        entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().setVisualizationType(graphType);
        entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().setVisualizationLibrary(graphEngine);

        //entitySpecificationBean.setQueryToMethodConfig(queryToMethodConfig);
        entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getQueryToMethodConfig().put("0",queryToMethodConfig);
        entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().setMethodToVisualizationConfig(methodToVisualizationConfig);

        //entitySpecificationBean.setRetrievableObjects(selectedMethods);
        entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().setEntityDisplayObjects(entityDisplayObjects);

        //If user Directly finalizes the Indicator, then we have to implicitly generate the HQL
        if(entitySpecificationBean.getCurrentIndicator().getHqlQuery().get("0") == null ) {
//            entitySpecificationBean.setGoalId(Long.parseLong(goalId));
//            entitySpecificationBean.setQuestionName(questionName);

            //GLACategory glaCategory = glaCategoryBean.loadCategoryByName(entitySpecificationBean.getSelectedMinor());
            //entitySpecificationBean.setSelectedMajor(glaCategory.getMajor());
            //entitySpecificationBean.setSelectedType(glaCategory.getType());

            operationNumberProcessorBean.computeResult(entitySpecificationBean, "0");
        }

//        List<EntityValues>  xMLentityValues = new ArrayList<EntityValues>();
//        Iterator<EntityValues> entityIterator = entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getIndicatorDataset().get("0").getEntityValues().iterator();
//        while(entityIterator.hasNext()){
//            xMLentityValues.add(entityIterator.next().clone());
//        }
//        List<UserSearchSpecifications>  xMlUserSpecifications = new ArrayList<UserSearchSpecifications>();
//        Iterator<UserSearchSpecifications> userSpecIterator = entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getIndicatorDataset().get("0").getUserSpecifications().iterator();
//        while(userSpecIterator.hasNext()){
//            xMlUserSpecifications.add(userSpecIterator.next().clone());
//        }
//
//        List<SessionSpecifications>  xMLSessionSpecifications = new ArrayList<SessionSpecifications>();
//        Iterator<SessionSpecifications> sessionSpecIterator = entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getIndicatorDataset().get("0").getSessionSpecifications().iterator();
//        while(sessionSpecIterator.hasNext()){
//            xMLSessionSpecifications.add(sessionSpecIterator.next().clone());
//        }
//        List<TimeSearchSpecifications>  xMLTimeSpecifications = new ArrayList<TimeSearchSpecifications>();
//        Iterator<TimeSearchSpecifications> timeSpecIterator = entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getIndicatorDataset().get("0").getTimeSpecifications().iterator();
//        while(timeSpecIterator.hasNext()){
//            xMLTimeSpecifications.add(timeSpecIterator.next().clone());
//        }

        AnalyticsEngineController analyticsEngineController = appContext.getBean(AnalyticsEngineController.class);

        String visualizationCode;
        if(entitySpecificationBean.getCurrentIndicator().getVisualization() == null || entitySpecificationBean.getCurrentIndicator().getVisualization().isEmpty()){
                String previewResponse = analyticsEngineController.getIndicatorPreviewVisualizationCode("xxxheightxxx", "xxxwidthxxx", analyticsMethod, graphEngine, graphType, indicatorName,
                    methodMappings, visualizationMappings, selectedMethodDataColumns);

            IndicatorPreviewResponse indicatorPreviewResponse = gson.fromJson(previewResponse, IndicatorPreviewResponse.class);

            if(indicatorPreviewResponse.isSuccess())
                visualizationCode = indicatorPreviewResponse.getVisualizationCode();
            else
                visualizationCode = indicatorPreviewResponse.getErrorMessage();
        }
        else
            visualizationCode = entitySpecificationBean.getCurrentIndicator().getVisualization();





        SessionIndicator sessionIndicator = null;
        try {
            sessionIndicator = entitySpecificationBean.getCurrentIndicator().clone();


            int curIndID =  entitySpecificationBean.getCurrentIndicator().getIdentifier();
            int cloneIndID =  sessionIndicator.getIdentifier();

            Log.info("Current indicator identifier:" + curIndID + ", Clonned indicator identifier: " + cloneIndID);

            sessionIndicator.setIdentifier(entitySpecificationBean.getCurrentIndicator().getIdentifier());

        } catch (CloneNotSupportedException e) {
            IndicatorDataset indicatorDataset = new IndicatorDataset();
            indicatorDataset.setSelectedSource(entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getIndicatorDataset().get("0").getSelectedSource());
            indicatorDataset.setSelectedPlatform(entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getIndicatorDataset().get("0").getSelectedPlatform());
            indicatorDataset.setSelectedAction(entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getIndicatorDataset().get("0").getSelectedAction());
            indicatorDataset.setSelectedMinor(entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getIndicatorDataset().get("0").getSelectedMinor());
            indicatorDataset.setSelectedMajor(entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getIndicatorDataset().get("0").getSelectedMajor());
            indicatorDataset.setSelectedType(entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getIndicatorDataset().get("0").getSelectedType());
            indicatorDataset.setEntityValues(entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getIndicatorDataset().get("0").getEntityValues());
            indicatorDataset.setUserSpecifications(entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getIndicatorDataset().get("0").getUserSpecifications());
            indicatorDataset.setSessionSpecifications(entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getIndicatorDataset().get("0").getSessionSpecifications());
            indicatorDataset.setTimeSpecifications(entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getIndicatorDataset().get("0").getTimeSpecifications());

            IndicatorParameters indicatorParameters = new IndicatorParameters();
            indicatorParameters.getIndicatorDataset().put("0", indicatorDataset);
            indicatorParameters.getAnalyticsMethodId().put("0", entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getAnalyticsMethodId().get("0"));
            indicatorParameters.setVisualizationLibrary(entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getVisualizationLibrary());
            indicatorParameters.setVisualizationType(entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getVisualizationType());
            indicatorParameters.setRetrievableObjects(entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getRetrievableObjects());
            indicatorParameters.setEntityDisplayObjects(entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getEntityDisplayObjects());
            indicatorParameters.setQueryToMethodConfig(entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getQueryToMethodConfig());
            indicatorParameters.setMethodToVisualizationConfig(entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getMethodToVisualizationConfig());

            sessionIndicator = new SessionIndicator();
            sessionIndicator.setIndicatorName(entitySpecificationBean.getCurrentIndicator().getIndicatorName());
            sessionIndicator.setIndicatorType(entitySpecificationBean.getCurrentIndicator().getIndicatorType());
            sessionIndicator.setHqlQuery(entitySpecificationBean.getCurrentIndicator().getHqlQuery());
            sessionIndicator.setIndicatorParameters(indicatorParameters);
            sessionIndicator.setIdentifier(entitySpecificationBean.getCurrentIndicator().getIdentifier());
        }

        sessionIndicator.setVisualization(visualizationCode);

        if(entitySpecificationBean.getCurrentQuestion().getSessionIndicators().size() == 0 ) {
            SessionQuestion sessionQuestion = new SessionQuestion();
            sessionQuestion.setGoalId(Long.parseLong(goalId));
            sessionQuestion.setQuestionName(entitySpecificationBean.getQuestionName());
            sessionQuestion.getSessionIndicators().add(sessionIndicator);

            entitySpecificationBean.setCurrentQuestion(sessionQuestion);
        }
        else if(entitySpecificationBean.getCurrentQuestion().getSessionIndicators().size() >= 1) {
            if(indicatorIdentifier.equals("null")) {
                entitySpecificationBean.getCurrentQuestion().getSessionIndicators().add(sessionIndicator);
            } else {
//                entitySpecificationBean.getQuestionsContainer().getGenQueries().set(Integer.parseInt(indicatorIndex), new SessionIndicator(entitySpecificationBean.getHql(),entitySpecificationBean.getIndicatorName(),1, indicatorXMLData, genIndicatorProps));

                ListIterator<SessionIndicator> sessionIndicatorListIterator = entitySpecificationBean.getCurrentQuestion().getSessionIndicators().listIterator();
                while(sessionIndicatorListIterator.hasNext()){
                    SessionIndicator indicator = sessionIndicatorListIterator.next();
                    if(indicator.getIdentifier() == Integer.parseInt(indicatorIdentifier)) {
                        sessionIndicatorListIterator.set(sessionIndicator);
                    }
                }
            }
        }
        return gson.toJson(entitySpecificationBean.getCurrentQuestion());
    }




    /*@RequestMapping(value = "/refreshCurrentIndicator", method = RequestMethod.GET)
    public @ResponseBody
    String  refreshCurrentIndicator(Model model) {

        Gson gson = new Gson();
        EntitySpecification entitySpecificationBean = (EntitySpecification) appContext.getBean("entitySpecifications");
        CurrentIndicatorSummary currentIndicatorSummary = new CurrentIndicatorSummary(entitySpecificationBean.getIndicatorName(), entitySpecificationBean.getSelectedPlatform(),
                entitySpecificationBean.getSelectedAction(), entitySpecificationBean.getSelectedChartType(), entitySpecificationBean.getSelectedChartEngine(), entitySpecificationBean.getHql(),
                entitySpecificationBean.getEntityValues().size(), entitySpecificationBean.getUserSpecifications().size(), entitySpecificationBean.getSessionSpecifications().size(),
                entitySpecificationBean.getTimeSpecifications().size());
        return gson.toJson(currentIndicatorSummary);

    }*/

    @RequestMapping(value = "/deleteIndFromQn", method = RequestMethod.GET)
    public @ResponseBody
//    String  deleteIndicatorFromQn(@RequestParam(value="indName" ,required = false)String indicatorName, Model model) {
    String  deleteIndicatorFromQn(@RequestParam(value="indIdentifier" ,required = false)String indicatorIdentifier, Model model) {
        Gson gson = new Gson();
        EntitySpecification entitySpecificationBean = (EntitySpecification) appContext.getBean("entitySpecifications");
        String msg = null;
        if (indicatorIdentifier == null) {
           msg = "No indicator specified for deletion.";
        } else {
            Iterator<SessionIndicator> sessionIndicatorIterator = entitySpecificationBean.getCurrentQuestion().getSessionIndicators().iterator();
            while(sessionIndicatorIterator.hasNext()) {
                SessionIndicator indicator = sessionIndicatorIterator.next();
                if (indicator.getIdentifier() == Integer.parseInt(indicatorIdentifier)) {
                    sessionIndicatorIterator.remove();
                    msg=indicator.getIndicatorName() + " successfully deleted from current question.";
                }
            }
        }
        return gson.toJson(msg);
    }

    @RequestMapping(value = "/loadIndFromQnSetToEditor", method = RequestMethod.GET)
    public @ResponseBody
//    String  loadIndicatorFromQn(@RequestParam(value="indName" ,required = false)String indicatorName, Model model) {
    String loadIndicatorFromQn(@RequestParam(value="indIdentifier" ,required = false)String indicatorIdentifier, Model model) throws CloneNotSupportedException {
        Gson gson = new Gson();
        EntitySpecification entitySpecificationBean = (EntitySpecification) appContext.getBean("entitySpecifications");
        String msg = null;
        if (indicatorIdentifier != null) {
            //SessionQuestion temp = entitySpecificationBean.getCurrentQuestion();
            Iterator<SessionIndicator> sessionIndicatorIterator = entitySpecificationBean.getCurrentQuestion().getSessionIndicators().iterator();

            while (sessionIndicatorIterator.hasNext()) {
                SessionIndicator indicator = sessionIndicatorIterator.next();
//                if (agenQuery.getIndicatorName().equals(indicatorName) && !agenQuery.getGenIndicatorProps().isComposite()) {
                if (indicator.getIdentifier() == Integer.parseInt(indicatorIdentifier) && indicator.getIndicatorType().equals("simple")) {

                    entitySpecificationBean.reset();


                    entitySpecificationBean.setCurrentIndicator(indicator.clone());


//                    if(!entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getIndicatorDataset().containsKey("0"))
//                        entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getIndicatorDataset().put("0", new IndicatorDataset());
//
//                    entitySpecificationBean.getCurrentIndicator().setIdentifier(Integer.parseInt(indicatorIdentifier));
//                    entitySpecificationBean.getCurrentIndicator().setIndicatorType("simple");
//
//                    entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getIndicatorDataset().get("0").setEntityValues(new ArrayList<EntityValues>());
//                    Iterator<EntityValues> entityIterator = indicator.getIndicatorParameters().getIndicatorDataset().get("0").getEntityValues().iterator();
//                    while(entityIterator.hasNext()) {
//                        entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getIndicatorDataset().get("0").getEntityValues().add(entityIterator.next().clone());
//                    }
//
//                    entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getIndicatorDataset().get("0").setUserSpecifications(new ArrayList<UserSearchSpecifications>());
//                    Iterator<UserSearchSpecifications> userSpecIterator = indicator.getIndicatorParameters().getIndicatorDataset().get("0").getUserSpecifications().iterator();
//                    while(userSpecIterator.hasNext()){
//                        entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getIndicatorDataset().get("0").getUserSpecifications().add(userSpecIterator.next().clone());
//                    }
//                    entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getIndicatorDataset().get("0").setSessionSpecifications(new ArrayList<SessionSpecifications>());
//                    Iterator<SessionSpecifications> sessionSpecIterator = indicator.getIndicatorParameters().getIndicatorDataset().get("0").getSessionSpecifications().iterator();
//                    while(sessionSpecIterator.hasNext()){
//                        entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getIndicatorDataset().get("0").getSessionSpecifications().add(sessionSpecIterator.next().clone());
//                    }
//                    entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getIndicatorDataset().get("0").setTimeSpecifications(new ArrayList<TimeSearchSpecifications>());
//                    Iterator<TimeSearchSpecifications> timeSpecIterator = indicator.getIndicatorParameters().getIndicatorDataset().get("0").getTimeSpecifications().iterator();
//                    while(timeSpecIterator.hasNext()){
//                        entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getIndicatorDataset().get("0").getTimeSpecifications().add(timeSpecIterator.next().clone());
//                    }

                    return gson.toJson(indicator);
                }
                else if(entitySpecificationBean.getCurrentIndicator().getIndicatorType() == "composite") {
                    //TODO for composite indicator type
                }
                else if(entitySpecificationBean.getCurrentIndicator().getIndicatorType() == "multianalysis") {
                    //TODO for multianalysis indicator type
                }
            }
        }
        return gson.toJson(null);
    }

    /*@RequestMapping(value = "/saveQuestionDB", method = RequestMethod.GET)
    public @ResponseBody
    String  saveQnToDB(@RequestParam(value="userName" ,required = true) String userName,
                       Model model) {

        log.info("Saving question and asoicated indicators");
        Gson gson = new Gson();
        EntitySpecification entitySpecificationBean = (EntitySpecification) appContext.getBean("entitySpecifications");
        GLAQuestionDao glaQuestionBean = (GLAQuestionDao) appContext.getBean("glaQuestions");
        Calendar calendar = Calendar.getInstance();
        java.util.Date now = calendar.getTime();

        entitySpecificationBean.setGoalId(Long.parseLong(goalId));
        entitySpecificationBean.setQuestionName(questionName);

        QuestionSaveRequest questionSaveRequest = new QuestionSaveRequest();
        questionSaveRequest.setGoalID(Long.parseLong(goalId));
        questionSaveRequest.setQuestion(questionName);

        List<IndicatorSaveRequest> indicatorSaveRequestList = new ArrayList<>();
        IndicatorSaveRequest indicatorSaveRequest;
        for (SessionIndicator genQuery : entitySpecificationBean.getQuestionsContainer().getGenQueries()) {
            indicatorSaveRequest = new IndicatorSaveRequest();
            indicatorSaveRequest.setName(genQuery.getIndicatorName());
            indicatorSaveRequest.setCreatedBy(userName);
            indicatorSaveRequest.setQuery(genQuery.getQuery());
            indicatorSaveRequest.setComposite(genQuery.getGenIndicatorProps().isComposite());
            indicatorSaveRequest.setQueryToMethodConfig(genQuery.getIndicatorXMLData().getQueryToMethodConfig());
            indicatorSaveRequest.setMethodToVisualizationConfig(genQuery.getIndicatorXMLData().getMethodToVisualizationConfig());
            indicatorSaveRequest.setIndicatorClientID(genQuery.getIdentifier());
//            indicatorSaveRequest.setServerID(genQuery.isComposite());
            indicatorSaveRequest.setAnalyticsMethodId(entitySpecificationBean.getAnalyticsMethodId());
            indicatorSaveRequest.setVisualizationFrameworkId(Long.parseLong(genQuery.getIndicatorXMLData().getSelectedChartEngine()));
            indicatorSaveRequest.setVisualizationMethodId(Long.parseLong(genQuery.getIndicatorXMLData().getSelectedChartType()));
            indicatorSaveRequest.setParameters(gson.toJson(genQuery.getIndicatorXMLData()));
            indicatorSaveRequestList.add(indicatorSaveRequest);
        }
        questionSaveRequest.setIndicators(indicatorSaveRequestList);

        RestTemplate restTemplate = new RestTemplate();
        String url = "http://137.226.117.226:8080/AnalyticsEngine/SaveQuestionAndIndicators";
        String requestJson = gson.toJson(questionSaveRequest);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<String>(requestJson,headers);
        QuestionSaveResponse result = restTemplate.postForObject(url, entity, QuestionSaveResponse.class);
//        return gson.toJson(result.getQuestionRequestCode());


        entitySpecificationBean.setQuestionsContainer(new Questions());

//        return gson.toJson(qid);
        return gson.toJson(result);

    }*/

    @RequestMapping(value = "/deleteDataFromSession", method = RequestMethod.GET)
    public @ResponseBody
    void deleteDataFromSession() {
        EntitySpecification entitySpecificationBean = (EntitySpecification) appContext.getBean("entitySpecifications");
        //entitySpecificationBean.setCurrentQuestion(new SessionQuestion());
        entitySpecificationBean.completeReset();
    }

    /*@RequestMapping(value = "/searchIndicator", method = RequestMethod.GET)
    public @ResponseBody
    String  searchIndicator(@RequestParam(value="searchString" ,required = true)String searchString,
                            @RequestParam(value="searchType" ,required = true)String searchType) {
        Gson gson = new Gson();
        List<String> names = new ArrayList<>();
        GLAIndicatorDao glaIndicatorBean = (GLAIndicatorDao) appContext.getBean("glaIndicator");
        GLAIndicator glaIndicator = null;
        List<GLAIndicator> glaIndicatorList;
        if(NumberChecks.isNumeric(searchString) && searchType.equals("ID")) {
            glaIndicator = glaIndicatorBean.loadByIndicatorID(Long.parseLong(searchString));
            if (glaIndicator != null) {
                log.info("GLA Question FROM DB SEARCH: ID : \t"+ glaIndicator.getId());
                names.add(glaIndicator.getIndicator_name());
            }
        }
        else if (!NumberChecks.isNumeric(searchString) && searchType.equals("IndicatorName")) {
            glaIndicatorList = glaIndicatorBean.loadByIndicatorByName(searchString, false);
            if (glaIndicatorList != null) {
                for(GLAIndicator gI : glaIndicatorList){
                    names.add(gI.getIndicator_name());
                }
            }
        }
        return gson.toJson(names);
    }*/

    /*@RequestMapping(value = "/loadIndicator", method = RequestMethod.GET)
    public @ResponseBody
    String  loadIndicatorFromDB(@RequestParam(value="indName" ,required = true)String indName,
                                @RequestParam(value="loadTemplate" ,required = false)String loadTemplate) {

        GLAIndicatorDao glaIndicatorBean = (GLAIndicatorDao) appContext.getBean("glaIndicator");
        EntitySpecification entitySpecificationBean = (EntitySpecification) appContext.getBean("entitySpecifications");
        GLAIndicator glaIndicator = null;
        IndicatorXMLData indicatorXMLData = null;

        long indicatorID = glaIndicatorBean.findIndicatorID(indName);
        glaIndicator = glaIndicatorBean.loadByIndicatorID(indicatorID);
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        Gson  gson2 = new Gson();
        if(loadTemplate != null && !glaIndicator.getGlaIndicatorProps().isComposite()) {

            indicatorXMLData = gson2.fromJson(glaIndicator.getGlaIndicatorProps().getJson_data(), IndicatorXMLData.class);
            entitySpecificationBean.setEntityValues(new ArrayList<EntityValues>(indicatorXMLData.getEntityValues().size()));
            Iterator<EntityValues> entityIterator = indicatorXMLData.getEntityValues().iterator();
            while(entityIterator.hasNext()) {
                entitySpecificationBean.getEntityValues().add(entityIterator.next().clone());
            }
            entitySpecificationBean.setUserSpecifications(new ArrayList<UserSearchSpecifications>(indicatorXMLData.getUserSpecifications().size()));
            Iterator<UserSearchSpecifications> userSpecIterator =indicatorXMLData.getUserSpecifications().iterator();
            while(userSpecIterator.hasNext()){
                entitySpecificationBean.getUserSpecifications().add(userSpecIterator.next().clone());
            }
            entitySpecificationBean.setSessionSpecifications(new ArrayList<SessionSpecifications>(indicatorXMLData.getSessionSpecifications().size()));
            Iterator<SessionSpecifications> sessionSpecIterator =indicatorXMLData.getSessionSpecifications().iterator();
            while(sessionSpecIterator.hasNext()){
                entitySpecificationBean.getSessionSpecifications().add(sessionSpecIterator.next().clone());
            }
            entitySpecificationBean.setTimeSpecifications(new ArrayList<TimeSearchSpecifications>(indicatorXMLData.getTimeSpecifications().size()));
            Iterator<TimeSearchSpecifications> timeSpecIterator = indicatorXMLData.getTimeSpecifications().iterator();
            while(timeSpecIterator.hasNext()){
                entitySpecificationBean.getTimeSpecifications().add(timeSpecIterator.next().clone());
            }
        }
        return gson.toJson(glaIndicator);
    }*/

    @RequestMapping(value = "/viewall", method = RequestMethod.GET)
    public String getIndicatorsViewAll(Map<String, Object> model) {
        SearchQuestionForm searchQuestionForm = new SearchQuestionForm();
        model.put("searchQuestionForm", searchQuestionForm);
        return  "indicator_system/viewall_indicators";
    }

    /*@RequestMapping(value = "/viewall", method = RequestMethod.POST)
    public ModelAndView processIndicatorSearchForm( @RequestParam String action,
                                                    @Valid @ModelAttribute("searchQuestionForm") SearchQuestionForm searchQuestionForm,
                                                    BindingResult bindingResult, HttpSession session) {

        ModelAndView model = null;
        if (bindingResult.hasErrors()) {
            return new ModelAndView("indicator_system/viewall_indicators");
        }
        if(action.equals("search")){

            processSearchParams(searchQuestionForm);
            model = new ModelAndView("indicator_system/viewall_indicators");
            model.addObject("searchQuestionForm", searchQuestionForm);
        }
        else if(action.equals("load")){
            if(searchQuestionForm.getSelectedQuestionName() == null || searchQuestionForm.getSelectedQuestionName().isEmpty()) {
                model = new ModelAndView("indicator_system/viewall_indicators");
                model.addObject("searchQuestionForm", searchQuestionForm);
            }
            else{
                model = new ModelAndView("indicator_system/view_indicator_details");
                model.addObject("question", retrieveQuestion(searchQuestionForm.getSelectedQuestionName()));
            }
        }
        else if(action.equals("Visualize")){

            model = new ModelAndView("indicator_system/run_results");
            model.addObject("chartType", "Pie");
            model.addObject("questionName", searchQuestionForm.getSelectedQuestionName());
            model.addObject("question", retrieveQuestion(searchQuestionForm.getSelectedQuestionName()));

        }
        return model;
    }*/

    /*@RequestMapping(value = "/fetchExistingQuestionsData.web", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    String fetchQuestionData(HttpServletRequest request) throws IOException {

        GLAQuestionDao glaQuestionsBean = (GLAQuestionDao) appContext.getBean("glaQuestions");
        List<GLAQuestion> glaQuestionList = null;
        List<GLAQuestion> pageGLAQuestionList = new ArrayList<>();
        Integer idisplayStart = 0;
        Integer iSortingCols =0;
        if(null != request.getParameter("iSortingCols"))
            iSortingCols = Integer.valueOf(request.getParameter("iSortingCols"));
        GLAQuestionJsonObject glaQuestionJsonObject = new GLAQuestionJsonObject();
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
                glaQuestionList = glaQuestionsBean.displayAll(colName, sortDirection, true);
            }
            else
                glaQuestionList = glaQuestionsBean.displayAll(colName, sortDirection, false);
            if(idisplayStart != -1){
                Integer endRange = idisplayStart+pageDisplayLength;
                if(endRange >glaQuestionList.size())
                    endRange = glaQuestionList.size();
                for(int i=idisplayStart; i<endRange; i++){
                    pageGLAQuestionList.add(glaQuestionList.get(i));
                }
            }
            //Set Total display record
            glaQuestionJsonObject.setiTotalDisplayRecords(glaQuestionsBean.getTotalQuestions());
            //Set Total record
            glaQuestionJsonObject.setiTotalRecords(glaQuestionsBean.getTotalQuestions());
            glaQuestionJsonObject.setAaData(pageGLAQuestionList);
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
                glaQuestionList = glaQuestionsBean.searchQuestionsName(searchParameter, false, colName, sortDirection, true);
            }
            else
                glaQuestionList = glaQuestionsBean.searchQuestionsName(searchParameter, false, colName, sortDirection, false);
            if(idisplayStart != -1) {
                Integer endRange = idisplayStart+pageDisplayLength;
                Integer startRange = idisplayStart;
                if(startRange > glaQuestionList.size())
                    startRange = 0;
                if (endRange > glaQuestionList.size())
                    endRange = glaQuestionList.size();
                for (int i = startRange; i <endRange; i++) {
                    pageGLAQuestionList.add(glaQuestionList.get(i));
                }
            }
            //Set Total display record
            glaQuestionJsonObject.setiTotalDisplayRecords(glaQuestionList.size());
            //Set Total record
            glaQuestionJsonObject.setiTotalRecords(glaQuestionList.size());
            glaQuestionJsonObject.setAaData(pageGLAQuestionList);
        }
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        return gson.toJson(glaQuestionJsonObject);
    }*/

   /* @RequestMapping(value = "/fetchExistingIndicatorsData.web", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    String fetchIndicatorData(HttpServletRequest request) throws IOException {

        GLAIndicatorDao glaIndicatorBean = (GLAIndicatorDao) appContext.getBean("glaIndicator");
        List<GLAIndicator> glaIndicatorList = null;
        List<GLAIndicator> pageGLAIndicatorList = new ArrayList<>();
        Integer idisplayStart = 0;
        Integer iSortingCols =0;
        if(null != request.getParameter("iSortingCols"))
            iSortingCols = Integer.valueOf(request.getParameter("iSortingCols"));
        GLAIndicatorJsonObject glaIndicatorJsonObject = new GLAIndicatorJsonObject();
        if (null != request.getParameter("iDisplayStart")) {
            idisplayStart = Integer.valueOf(request.getParameter("iDisplayStart"));
            //log.info("iDisplayStart : \t" + request.getParameter("iDisplayStart") + "\n");
        }
        //Fetch search parameter
        String searchParameter = request.getParameter("sSearch");
        //log.info("sSearch : \t"+ searchParameter+"\n");

        //Fetch Page display length
        Integer pageDisplayLength = Integer.parseInt(request.getParameter("iDisplayLength"));
        //log.info("iDisplayLength : \t"+ pageDisplayLength+"\n");

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
//                else if (isortCol == 2)
//                    colName = "short_name";
                glaIndicatorList = glaIndicatorBean.displayAllNonComposite(colName, sortDirection, true);
            }
            else
                glaIndicatorList = glaIndicatorBean.displayAllNonComposite(colName, sortDirection, false);
            if(idisplayStart != -1){
                Integer endRange = idisplayStart+pageDisplayLength;
                if(endRange >glaIndicatorList.size())
                    endRange = glaIndicatorList.size();
                for(int i=idisplayStart; i<endRange; i++){
                    pageGLAIndicatorList.add(glaIndicatorList.get(i));
                }
            }
            //Set Total display record
            glaIndicatorJsonObject.setiTotalDisplayRecords(glaIndicatorBean.getTotalIndicators());
            //Set Total record
            glaIndicatorJsonObject.setiTotalRecords(glaIndicatorBean.getTotalIndicators());
            glaIndicatorJsonObject.setAaData(pageGLAIndicatorList);
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
//                else if (isortCol == 2)
//                    colName = "short_name";
                glaIndicatorList = glaIndicatorBean.searchIndicatorsName(searchParameter, false, colName, sortDirection, true);
            }
            else
                glaIndicatorList = glaIndicatorBean.searchIndicatorsName(searchParameter, false, colName, sortDirection, false);
            if(idisplayStart != -1) {
                Integer endRange = idisplayStart+pageDisplayLength;
                Integer startRange = idisplayStart;
                if(startRange > glaIndicatorList.size())
                    startRange = 0;
                if (endRange > glaIndicatorList.size())
                    endRange = glaIndicatorList.size();
                for (int i = startRange; i <endRange; i++) {
                    pageGLAIndicatorList.add(glaIndicatorList.get(i));
                }
            }
            //Set Total display record
            glaIndicatorJsonObject.setiTotalDisplayRecords(glaIndicatorList.size());
            //Set Total record
            glaIndicatorJsonObject.setiTotalRecords(glaIndicatorList.size());
            glaIndicatorJsonObject.setAaData(pageGLAIndicatorList);
        }
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        return gson.toJson(glaIndicatorJsonObject);
    }*/

    /*private Questions retrieveQuestion(String questionName){
        GLAQuestionDao glaQuestionsBean = (GLAQuestionDao) appContext.getBean("glaQuestions");
        long question_id = glaQuestionsBean.findQuestionID(questionName);
        GLAQuestion glaQuestion = glaQuestionsBean.loadByQuestionID(question_id);
        Questions questions = new Questions();
        questions.setQuestionId(glaQuestion.getId());
        questions.setQuestionName(glaQuestion.getQuestion_name());
        questions.setTotalExecutions(glaQuestion.getGlaQuestionProps().getTotalExecutions());
        questions.setLast_executionTime(glaQuestion.getGlaQuestionProps().getLast_executionTime());
        questions.setLast_executionTime(glaQuestion.getGlaQuestionProps().getLast_executionTime());
        questions.setUserName(glaQuestion.getGlaQuestionProps().getUserName());
        questions.setNumIndicators(glaQuestion.getIndicators_num());
        for( GLAIndicator glaIndicators : glaQuestion.getGlaIndicators()){
            SessionIndicator genQuery = new SessionIndicator(glaIndicators.getHql(),glaIndicators.getIndicator_name(), glaIndicators.getId());
            System.out.println(glaIndicators.getGlaIndicatorProps().getJson_data());
            System.out.println(genQuery.getIndicatorXMLData());
            genQuery.setIndicatorXMLData(genQuery.getIndicatorXMLData());
            genQuery.setGenIndicatorProps(glaIndicators.getGlaIndicatorProps().getId(),glaIndicators.getGlaIndicatorProps().getLast_executionTime(),
                    glaIndicators.getGlaIndicatorProps().getTotalExecutions(),glaIndicators.getGlaIndicatorProps().getChartType(),
                    glaIndicators.getGlaIndicatorProps().getChartEngine(), glaIndicators.getGlaIndicatorProps().getUserName());
            questions.getGenQueries().add(genQuery);
        }
        return questions;

    }*/

    /*private void processSearchParams(SearchQuestionForm searchQuestionForm){
        log.info("processSearchParams : STARTED \n");
        GLAQuestionDao glaQuestionBean = (GLAQuestionDao) appContext.getBean("glaQuestions");
        GLAQuestion glaQuestion = null;
        List<GLAQuestion> glaQuestionList;
        if(NumberChecks.isNumeric(searchQuestionForm.getSearchField()) && searchQuestionForm.getSelectedSearchType().equals("ID")) {
            glaQuestion = glaQuestionBean.loadByQuestionID(Long.parseLong(searchQuestionForm.getSearchField()));
            if(glaQuestion != null) {
                log.info("GLA Question FROM DB SEARCH: ID : \t"+ glaQuestion.getId());
                searchQuestionForm.getSearchResults().add(glaQuestion.getQuestion_name());
            }
        }
        else if (!NumberChecks.isNumeric(searchQuestionForm.getSearchField()) && searchQuestionForm.getSelectedSearchType().equals("Question Name")) {
            glaQuestionList = glaQuestionBean.loadByQuestionName(searchQuestionForm.getSearchField(), false);
            if(glaQuestionList != null) {
                for(GLAQuestion gQ : glaQuestionList){
                    log.info("GLA INDICATOR FROM DB SEARCH: NAME : \t"+ gQ.getQuestion_name());
                    searchQuestionForm.getSearchResults().add(gQ.getQuestion_name());
                }
            }
        }
        log.info("processSearchParams : ENDED \n");
    }*/
}

//class Categories {
//
//    String type;
//    String major;
//    String minor;
//
//    Categories(String type, String major, String minor) {
//        this.major = major;
//        this.minor = minor;
//        this.type = type;
//    }
//    public String getType() {
//        return type;
//    }
//
//    public void setType(String type) {
//        this.type = type;
//    }
//
//    public String getMajor() {
//        return major;
//    }
//
//    public void setMajor(String major) {
//        this.major = major;
//    }
//
//    public String getMinor() {
//        return minor;
//    }
//
//    public void setMinor(String minor) {
//        this.minor = minor;
//    }
//}

//class CurrentIndicatorSummary {
//
//    String name;
//    String platform;
//    String action;
//    String chartType;
//    String chartEngine;
//    String hql;
//    int entityFilters;
//    int userFilters;
//    int sessionFilters;
//    int timeFilters;
//
//    CurrentIndicatorSummary() {}
//    CurrentIndicatorSummary(String name, String platform, String action, String chartType, String chartEngine,
//                            String hql, int entityFilters, int userFilters, int sessionFilters, int timeFilters) {
//        this.name = name;
//        this.action = action;
//        this.platform = platform;
//        this.chartEngine = chartEngine;
//        this.chartType = chartType;
//        this.hql = hql;
//        this.entityFilters = entityFilters;
//        this.userFilters = userFilters;
//        this.sessionFilters = sessionFilters;
//        this.timeFilters = timeFilters;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getPlatform() {
//        return platform;
//    }
//
//    public void setPlatform(String platform) {
//        this.platform = platform;
//    }
//
//    public String getAction() {
//        return action;
//    }
//
//    public void setAction(String action) {
//        this.action = action;
//    }
//
//    public String getChartType() {
//        return chartType;
//    }
//
//    public void setChartType(String chartType) {
//        this.chartType = chartType;
//    }
//
//    public String getChartEngine() {
//        return chartEngine;
//    }
//
//    public void setChartEngine(String chartEngine) {
//        this.chartEngine = chartEngine;
//    }
//
//    public String getHql() {
//        return hql;
//    }
//
//    public void setHql(String hql) {
//        this.hql = hql;
//    }
//
//    public int getEntityFilters() {
//        return entityFilters;
//    }
//
//    public void setEntityFilters(int entityFilters) {
//        this.entityFilters = entityFilters;
//    }
//
//    public int getUserFilters() {
//        return userFilters;
//    }
//
//    public void setUserFilters(int userFilters) {
//        this.userFilters = userFilters;
//    }
//
//    public int getSessionFilters() {
//        return sessionFilters;
//    }
//
//    public void setSessionFilters(int sessionFilters) {
//        this.sessionFilters = sessionFilters;
//    }
//
//    public int getTimeFilters() {
//        return timeFilters;
//    }
//
//    public void setTimeFilters(int timeFilters) {
//        this.timeFilters = timeFilters;
//    }
//}

//class CompositeQuery {
//    private String parentIndName;
//    private String query;
//
//    CompositeQuery(String query,String parentIndName) {
//        this.query = query;
//        this.parentIndName = parentIndName;
//    }
//    public String getQuery() {
//        return query;
//    }
//
//    public void setQuery(String query) {
//        this.query = query;
//    }
//
//    public String getParentIndName() {
//        return parentIndName;
//    }
//
//    public void setParentIndName(String parentIndName) {
//        this.parentIndName = parentIndName;
//    }
//}