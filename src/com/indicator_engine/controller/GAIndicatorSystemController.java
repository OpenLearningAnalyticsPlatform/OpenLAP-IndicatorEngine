package com.indicator_engine.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.indicator_engine.dao.*;
import com.indicator_engine.graphgenerator.cewolf.PageViewCountData;
import com.indicator_engine.indicator_system.IndicatorPreProcessing;
import com.indicator_engine.indicator_system.Number.OperationNumberProcessorDao;
import com.indicator_engine.model.app.SearchQuestionForm;
import com.indicator_engine.model.indicator_system.Number.*;
import de.rwthaachen.openlap.analyticsengine.core.dtos.response.IndicatorPreviewResponse;
import de.rwthaachen.openlap.dataset.OpenLAPPortConfig;
import de.rwthaachen.openlap.dataset.OpenLAPPortMapping;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.util.*;

@Controller
@Scope("session")
@SessionAttributes({"loggedIn", "userName", "rid", "sid", "activationStatus","role", "admin_access"})
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
                      @RequestParam(value="dsid", required = false) String dsid,
                      Model model) {
        Gson gson = new Gson();
        EntitySpecification entitySpecificationBean = (EntitySpecification) appContext.getBean("entitySpecifications");

        boolean hasEntity = false;
        String indType = entitySpecificationBean.getCurrentIndicator().getIndicatorType();
        if(indType.equals("simple") || indType.equals("composite")) {
            dsid = "0";
        }

        if(entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getIndicatorDataset().size()<= 0)
            entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getIndicatorDataset().put(dsid, new IndicatorDataset());

        Iterator<EntityValues> entityValues = entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getIndicatorDataset().get(dsid).getEntityValues().iterator();

        while(entityValues.hasNext()) {
            EntityValues aEntityValue = entityValues.next();
            if (aEntityValue.getKey().equals(key) && aEntityValue.geteValues().equals(value)) {
                hasEntity = true;
                break;
            }
        }
        if (!hasEntity) {
            entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getIndicatorDataset().get(dsid).getEntityValues().add(new EntityValues(key, value, title));
        }

        return gson.toJson(entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getIndicatorDataset().get(dsid).getEntityValues());
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
    String  deleteEntities(Model model, @RequestParam(value="filter", required = true) String filter,
                           @RequestParam(value="dsid", required = false) String dsid) {
        Gson gson = new Gson();
        EntitySpecification entitySpecificationBean = (EntitySpecification) appContext.getBean("entitySpecifications");

        String indType = entitySpecificationBean.getCurrentIndicator().getIndicatorType();
        if(indType.equals("simple") || indType.equals("composite"))
            dsid = "0";

        if(entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getIndicatorDataset().containsKey(dsid)){
            if(filter.equals("ALL"))
                entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getIndicatorDataset().get(dsid).getEntityValues().clear();
            else {
                String[] filterTerms = filter.split("~");
                Iterator<EntityValues> entityValues = entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getIndicatorDataset().get(dsid).getEntityValues().iterator();
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
            entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getIndicatorDataset().put(dsid, new IndicatorDataset());
        }
        return gson.toJson(entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getIndicatorDataset().get(dsid).getEntityValues());
    }

    @RequestMapping(value = "/setUserFilter", method = RequestMethod.GET)
    public @ResponseBody
    String  addUserFilter(@RequestParam(value="userFilter", required = true) String userFilter,
                          @RequestParam(value="userHash", required = true) String userHash,
                          @RequestParam(value = "dsid", required = false, defaultValue = "0") String dsid,
                          Model model) {
        EntitySpecification entitySpecificationBean = (EntitySpecification) appContext.getBean("entitySpecifications");
        Gson gson = new Gson();

        //possible userFilter values = all, mine, notmine

        if(!entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getIndicatorDataset().containsKey(dsid))
            entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getIndicatorDataset().put(dsid, new IndicatorDataset());

        if(entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getIndicatorDataset().get(dsid).getUserSpecifications()==null)
            entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getIndicatorDataset().get(dsid).setUserSpecifications(new ArrayList<UserSearchSpecifications>());
        else
            entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getIndicatorDataset().get(dsid).getUserSpecifications().clear();

        if(userFilter.equals("mine") || userFilter.equals("notmine")) {
            //UserSearchSpecifications aUserSearchSpecifications = new UserSearchSpecifications(userFilter, userHash, "", "", "");
            UserSearchSpecifications aUserSearchSpecifications = new UserSearchSpecifications(userFilter, "xxxridxxx", "", "", "");
            entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getIndicatorDataset().get(dsid).getUserSpecifications().add(aUserSearchSpecifications);
        }

        return gson.toJson(entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getIndicatorDataset().get(dsid).getUserSpecifications());
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
                          @RequestParam(value="dsid", required = false) String dsid,
                          Model model) {
        EntitySpecification entitySpecificationBean = (EntitySpecification) appContext.getBean("entitySpecifications");
        Gson gson = new Gson();

        String indicatorType = entitySpecificationBean.getCurrentIndicator().getIndicatorType();
        if(indicatorType.equals("simple") || indicatorType.equals("composite"))
            dsid = "0";

        boolean hasTimeFilter = false;
        int timeFilterIndex = -1;

        if(!entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getIndicatorDataset().containsKey(dsid))
            entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getIndicatorDataset().put(dsid, new IndicatorDataset());

        Iterator<TimeSearchSpecifications> timeSearchSpecifications = entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getIndicatorDataset().get(dsid).getTimeSpecifications().iterator();

        while(timeSearchSpecifications.hasNext()) {
            TimeSearchSpecifications aTimeSearchSpecifications = timeSearchSpecifications.next();

            if (aTimeSearchSpecifications.getType().equals(timeType)) {
                hasTimeFilter = true;
                timeFilterIndex = entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getIndicatorDataset().get(dsid).getTimeSpecifications().indexOf(aTimeSearchSpecifications);
                break;
            }
        }

        if (!hasTimeFilter)
            entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getIndicatorDataset().get(dsid).getTimeSpecifications().add(new TimeSearchSpecifications(timeType, time));
        else
            entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getIndicatorDataset().get(dsid).getTimeSpecifications().get(timeFilterIndex).setTimestamp(time);

        return gson.toJson(entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getIndicatorDataset().get(dsid).getTimeSpecifications());
    }

    @RequestMapping(value = "/getTimeFilters", method = RequestMethod.GET)
    public @ResponseBody
    String  getTimeFilters(@RequestParam(value="dsid", required = false) String dsid, Model model) {
        Gson gson = new Gson();
        EntitySpecification entitySpecificationBean = (EntitySpecification) appContext.getBean("entitySpecifications");

        String indicatorType = entitySpecificationBean.getCurrentIndicator().getIndicatorType();
        if(indicatorType.equals("simple") || indicatorType.equals("composite"))
            dsid = "0";

        if(!entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getIndicatorDataset().containsKey(dsid))
            entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getIndicatorDataset().put(dsid, new IndicatorDataset());

        return gson.toJson(entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getIndicatorDataset().get(dsid).getTimeSpecifications());
    }

    @RequestMapping(value = "/deleteTimeFilters", method = RequestMethod.GET)
    public @ResponseBody
    String  deleteTimeFilters(Model model, @RequestParam(value="filter", required = true) String filter,
                              @RequestParam(value="dsid", required = false) String dsid) {
        Gson gson = new Gson();
        EntitySpecification entitySpecificationBean = (EntitySpecification) appContext.getBean("entitySpecifications");
        String[] filterTerms = filter.split("_");

        String indicatorType = entitySpecificationBean.getCurrentIndicator().getIndicatorType();
        if(indicatorType.equals("simple") || indicatorType.equals("composite"))
            dsid = "0";

        if(!entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getIndicatorDataset().containsKey(dsid))
            entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getIndicatorDataset().put(dsid, new IndicatorDataset());

        Iterator<TimeSearchSpecifications> timeSearchSpecifications = entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getIndicatorDataset().get(dsid).getTimeSpecifications().iterator();

        while(timeSearchSpecifications.hasNext()) {
            TimeSearchSpecifications aTimeSearchSpecifications = timeSearchSpecifications.next();

            if (aTimeSearchSpecifications.getType().equals(filterTerms[0])) {
                timeSearchSpecifications.remove();
                break;
            }
        }

        return gson.toJson(entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getIndicatorDataset().get(dsid).getTimeSpecifications());
    }

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
                              @RequestParam(value="methodParams", required = true) String methodParams,
                              HttpServletRequest request) {
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

        OperationNumberProcessorDao operationNumberProcessorBean =  (OperationNumberProcessorDao) appContext.getBean("operationNumberProcessor");
        EntitySpecification entitySpecificationBean = (EntitySpecification) appContext.getBean("entitySpecifications");

        entitySpecificationBean.setGoalId(Long.parseLong(goalId));
        entitySpecificationBean.setQuestionName(questionName);
        entitySpecificationBean.getCurrentIndicator().setIndicatorName(indicatorName);
        entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().setVisualizationType(graphType);
        entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().setVisualizationLibrary(graphEngine);
        entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().setMethodToVisualizationConfig(methodToVisualizationConfig);

        entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getIndicatorDataset().get("0").setAnalyticsMethodId(Long.parseLong(analyticsMethod));
        entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getIndicatorDataset().get("0").setAnalyticsMethodParams(methodParams);
        entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getIndicatorDataset().get("0").setQueryToMethodConfig(queryToMethodConfig);
        entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getIndicatorDataset().get("0").setEntityDisplayObjects(entityDisplayObjects);

        //If user Directly finalizes the Indicator, then we have to implicitly generate the HQL
        if(entitySpecificationBean.getCurrentIndicator().getHqlQuery().get("0") == null ) {
            operationNumberProcessorBean.computeResult(entitySpecificationBean, "0");
        }

        AnalyticsEngineController analyticsEngineController = appContext.getBean(AnalyticsEngineController.class);

        String visualizationCode;
        if(entitySpecificationBean.getCurrentIndicator().getVisualization() == null || entitySpecificationBean.getCurrentIndicator().getVisualization().isEmpty()){
                String previewResponse = analyticsEngineController.getIndicatorPreviewVisualizationCode("xxxheightxxx", "xxxwidthxxx", analyticsMethod, graphEngine, graphType, indicatorName,
                    methodMappings, visualizationMappings, selectedMethodDataColumns, methodParams, request);

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
            indicatorDataset.setRetrievableObjects(entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getIndicatorDataset().get("0").getRetrievableObjects());
            indicatorDataset.setEntityDisplayObjects(entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getIndicatorDataset().get("0").getEntityDisplayObjects());
            indicatorDataset.setAnalyticsMethodId(entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getIndicatorDataset().get("0").getAnalyticsMethodId());
            indicatorDataset.setQueryToMethodConfig(entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getIndicatorDataset().get("0").getQueryToMethodConfig());
            indicatorDataset.setAnalyticsMethodParams(entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getIndicatorDataset().get("0").getAnalyticsMethodParams());

            IndicatorParameters indicatorParameters = new IndicatorParameters();
            indicatorParameters.getIndicatorDataset().put("0", indicatorDataset);
            indicatorParameters.setVisualizationLibrary(entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getVisualizationLibrary());
            indicatorParameters.setVisualizationType(entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getVisualizationType());
            indicatorParameters.setMethodToVisualizationConfig(entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getMethodToVisualizationConfig());
            indicatorParameters.setVisualizationParams(entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getVisualizationParams());

            sessionIndicator = new SessionIndicator();
            sessionIndicator.setIndicatorName(entitySpecificationBean.getCurrentIndicator().getIndicatorName());
            sessionIndicator.setIndicatorType(entitySpecificationBean.getCurrentIndicator().getIndicatorType());
            sessionIndicator.setHqlQuery(entitySpecificationBean.getCurrentIndicator().getHqlQuery());
            sessionIndicator.setIndicatorParameters(indicatorParameters);
            sessionIndicator.setIdentifier(entitySpecificationBean.getCurrentIndicator().getIdentifier());
            sessionIndicator.setVisualization(entitySpecificationBean.getCurrentIndicator().getVisualization());
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

    @RequestMapping(value = "/deleteIndFromQn", method = RequestMethod.GET)
    public @ResponseBody
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
    String loadIndicatorFromQn(@RequestParam(value="indIdentifier" ,required = false)String indicatorIdentifier, Model model) throws CloneNotSupportedException {
        Gson gson = new Gson();
        EntitySpecification entitySpecificationBean = (EntitySpecification) appContext.getBean("entitySpecifications");
        String msg = null;
        if (indicatorIdentifier != null) {
            Iterator<SessionIndicator> sessionIndicatorIterator = entitySpecificationBean.getCurrentQuestion().getSessionIndicators().iterator();

            while (sessionIndicatorIterator.hasNext()) {
                SessionIndicator indicator = sessionIndicatorIterator.next();
                if (indicator.getIdentifier() == Integer.parseInt(indicatorIdentifier)) {
                    entitySpecificationBean.reset();
                    entitySpecificationBean.setCurrentIndicator(indicator.clone());
                    return gson.toJson(indicator);
                }
            }
        }
        return gson.toJson(null);
    }

    @RequestMapping(value = "/deleteDataFromSession", method = RequestMethod.GET)
    public @ResponseBody
    void deleteDataFromSession() {
        EntitySpecification entitySpecificationBean = (EntitySpecification) appContext.getBean("entitySpecifications");
        entitySpecificationBean.completeReset();
    }

    @RequestMapping(value = "/viewall", method = RequestMethod.GET)
    public String getIndicatorsViewAll(Map<String, Object> model) {
        SearchQuestionForm searchQuestionForm = new SearchQuestionForm();
        model.put("searchQuestionForm", searchQuestionForm);
        return  "indicator_system/viewall_indicators";
    }
}