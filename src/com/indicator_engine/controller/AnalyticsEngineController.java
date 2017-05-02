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
import com.indicator_engine.dao.GLACategoryDao;
import com.indicator_engine.dao.GLAEntityDao;
import com.indicator_engine.dao.GLAQuestionDao;
import com.indicator_engine.indicator_system.Number.OperationNumberProcessorDao;
import com.indicator_engine.model.indicator_system.Number.*;
import de.rwthaachen.openlap.analyticsengine.core.dtos.request.IndicatorPreviewRequest;
import de.rwthaachen.openlap.analyticsengine.core.dtos.request.IndicatorSaveRequest;
import de.rwthaachen.openlap.analyticsengine.core.dtos.request.QuestionSaveRequest;
import de.rwthaachen.openlap.analyticsengine.core.dtos.response.IndicatorPreviewResponse;
import de.rwthaachen.openlap.analyticsengine.core.dtos.response.IndicatorResponse;
import de.rwthaachen.openlap.analyticsengine.core.dtos.response.QuestionResponse;
import de.rwthaachen.openlap.analyticsengine.core.dtos.response.QuestionSaveResponse;
import de.rwthaachen.openlap.analyticsmethods.model.AnalyticsMethodMetadata;
import de.rwthaachen.openlap.analyticsmodules.model.AnalyticsGoal;
import de.rwthaachen.openlap.dataset.OpenLAPColumnConfigData;
import de.rwthaachen.openlap.dataset.OpenLAPPortConfig;
import de.rwthaachen.openlap.dataset.OpenLAPPortMapping;
import de.rwthaachen.openlap.visualizer.core.dtos.response.VisualizationFrameworkDetailsResponse;
import de.rwthaachen.openlap.visualizer.core.dtos.response.VisualizationFrameworksDetailsResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.jms.Session;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Type;
import java.util.*;

/**
 * Provides Methods to get data from Analytics Engine via API
 * @author Memoona Mughal
 * @since 03/05/2016
 *
 */
@Controller
@Scope("session")
@SessionAttributes({"loggedIn", "userName", "sid", "activationStatus","role", "admin_access"})
@RequestMapping(value="/engine")
@SuppressWarnings({"unused", "unchecked"})
public class AnalyticsEngineController {
    @Autowired
    private ApplicationContext appContext;

    static Logger log = Logger.getLogger(AnalyticsEngineController.class.getName());

    private String AnalyticsEngineServerURL = "http://137.226.117.226:8080";

    /**
     * Get List of all Analytics Methods
     * @return String JSON string containing ids and names of analytics methods
     */
    @RequestMapping(value = "/listAllAnalyticsMethods", method = RequestMethod.GET)
    public @ResponseBody
    String getAllAnalyticsMethods() {

        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject (
                        AnalyticsEngineServerURL + "/AnalyticsEngine/GetAnalyticsMethods",
                        String.class);

        Gson gson = new Gson();
        Type analyticsMethodMetadataListType = new TypeToken<List<AnalyticsMethodMetadata>>(){}.getType();
        List<AnalyticsMethodMetadata> analyticsMethodMetadataList = gson.fromJson(result, analyticsMethodMetadataListType);

        return gson.toJson(analyticsMethodMetadataList);
    }

    /**
     * Get List of all Goals
     * @return String JSON string containing ids and names of goals
     */
    @RequestMapping(value = "/listAllGoals", method = RequestMethod.GET)
    public @ResponseBody
    String getAllGoals() {

        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject (
                AnalyticsEngineServerURL + "/AnalyticsEngine/GetActiveGoals",
                String.class);

        Gson gson = new Gson();
        Type analyticsGoalListType = new TypeToken<List<AnalyticsGoal>>(){}.getType();
        List<AnalyticsGoal> analyticsGoalList = gson.fromJson(result, analyticsGoalListType);

        return gson.toJson(analyticsGoalList);
    }

    /**
     * Get List of all Visualization Frameworks
     * @return String JSON string containing ids and names of Visualization Frameworks
     */
    @RequestMapping(value = "/listAllVisualizationFrameworks", method = RequestMethod.GET)
    public @ResponseBody
    String getAllVisualizationFrameworks() {

        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject (
                AnalyticsEngineServerURL + "/AnalyticsEngine/GetVisualizations",
                String.class);

        Gson gson = new Gson();
        Type frameworkResponseType = new TypeToken<VisualizationFrameworksDetailsResponse>(){}.getType();
        VisualizationFrameworksDetailsResponse frameworkResponse = gson.fromJson(result, frameworkResponseType);

        return gson.toJson(frameworkResponse.getVisualizationFrameworks());
    }

    /**
     * Get List of all Visualization methods related to framework Id
     * @return String JSON string containing ids and names of Visualization Frameworks
     */
    @RequestMapping(value = "/getVisualizationMethods", method = RequestMethod.GET)
    public @ResponseBody
    String getVisualizationMethods(@RequestParam(value="frameworkId", required = true) String frameworkId) {

        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject (
                AnalyticsEngineServerURL + "/AnalyticsEngine/GetVisualizationMethods?frameworkId=" + frameworkId,
                String.class);

        Gson gson = new Gson();
        Type frameworkResponseType = new TypeToken<VisualizationFrameworkDetailsResponse>(){}.getType();
        VisualizationFrameworkDetailsResponse frameworkResponse = gson.fromJson(result, frameworkResponseType);

        return gson.toJson(frameworkResponse.getVisualizationFramework());
    }

    /**
     * Get Visualization data for Indicator preview graph generation
     * @return String JSON string containing Indicator visualization code
     */
    @RequestMapping(value = "/getIndicatorPreviewVisualizationCode", method = RequestMethod.GET)
    public @ResponseBody
    String getIndicatorPreviewVisualizationCode(@RequestParam(value="height", required = true) String height,
                                         @RequestParam(value="width", required = true) String width,
                                         @RequestParam(value="analyticsMethodId", required = true) String analyticsMethodId,
                                         @RequestParam(value="EngineSelect", required = true) String engineSelectId,
                                         @RequestParam(value="selectedChartType", required = true) String selectedChartType,
                                         @RequestParam(value="indicatorNaming", required = true) String indicatorName,
                                         @RequestParam(value="methodMappings", required = true) String methodMappings,
                                         @RequestParam(value="visualizationMappings", required = true) String visualizationMappings,
                                         @RequestParam(value="selectedMethods", required = true) String selectedMethodDataColumns) {

        List<String> entityDisplayObjects = new ArrayList<>();

        if(selectedMethodDataColumns != null && !selectedMethodDataColumns.isEmpty())
            entityDisplayObjects = Arrays.asList(selectedMethodDataColumns.split("\\s*,\\s*"));

        EntitySpecification entitySpecificationBean = (EntitySpecification) appContext.getBean("entitySpecifications");
        //entitySpecificationBean.setRetrievableObjects(selectedMethodDataColumns);
        entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().setEntityDisplayObjects(entityDisplayObjects);

        //GLAEntityDao glaEntityBean = (GLAEntityDao) appContext.getBean("glaEntity");
        //GLACategoryDao glaCategoryBean = (GLACategoryDao) appContext.getBean("glaCategory");
        OperationNumberProcessorDao operationNumberProcessorBean =  (OperationNumberProcessorDao) appContext.getBean("operationNumberProcessor");
        entitySpecificationBean.getCurrentIndicator().setIndicatorName(indicatorName);
        entitySpecificationBean.getCurrentIndicator().setIndicatorType("simple");
        //GLACategory glaCategory = glaCategoryBean.loadCategoryByName(entitySpecificationBean.getSelectedMinor());
        //entitySpecificationBean.setSelectedMajor(glaCategory.getMajor());
        //entitySpecificationBean.setSelectedType(glaCategory.getType());
        operationNumberProcessorBean.computeResult(entitySpecificationBean, "0");


        IndicatorPreviewRequest indicatorPreviewRequest = new IndicatorPreviewRequest();
        indicatorPreviewRequest.getAnalyticsMethodId().put("0", Long.parseLong(analyticsMethodId));
        indicatorPreviewRequest.setVisualizationMethodId(Long.parseLong(selectedChartType));
        indicatorPreviewRequest.setVisualizationFrameworkId(Long.parseLong(engineSelectId));
        indicatorPreviewRequest.getQuery().put("0", entitySpecificationBean.getCurrentIndicator().getHqlQuery().get("0"));
        indicatorPreviewRequest.setIndicatorType(entitySpecificationBean.getCurrentIndicator().getIndicatorType());

        Map<String, Object> chartSizeParams = new HashMap<>();
        chartSizeParams.put("width", width);
        chartSizeParams.put("height", height);
        indicatorPreviewRequest.setAdditionalParams(chartSizeParams);

        Gson gson = new Gson();

        Type openlapPortMappingType = new TypeToken<List<OpenLAPPortMapping>>(){}.getType();
        if (!methodMappings.isEmpty()) {
            List<OpenLAPPortMapping> methodOpenLAPPortMappingList = gson.fromJson(methodMappings, openlapPortMappingType);
            OpenLAPPortConfig config = new OpenLAPPortConfig();
            for (OpenLAPPortMapping methodPortMapping : methodOpenLAPPortMappingList) {
                config.getMapping().add(methodPortMapping);
            }
            //indicatorPreviewRequest.setQueryToMethodConfig(config);
            indicatorPreviewRequest.getQueryToMethodConfig().put("0", config);
        }

        if (!visualizationMappings.isEmpty()) {
            List<OpenLAPPortMapping> visualizationOpenLAPPortMappingList = gson.fromJson(visualizationMappings, openlapPortMappingType);
            OpenLAPPortConfig config = new OpenLAPPortConfig();
            for (OpenLAPPortMapping visualizationPortMapping : visualizationOpenLAPPortMappingList) {
                config.getMapping().add(visualizationPortMapping);
            }
            indicatorPreviewRequest.setMethodToVisualizationConfig(config);
        }

        RestTemplate restTemplate = new RestTemplate();
        String url = AnalyticsEngineServerURL + "/AnalyticsEngine/GetIndicatorPreview";
        String requestJson = gson.toJson(indicatorPreviewRequest);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<String>(requestJson,headers);
        IndicatorPreviewResponse result = restTemplate.postForObject(url, entity, IndicatorPreviewResponse.class);

        if(result.isSuccess())
            entitySpecificationBean.getCurrentIndicator().setVisualization(result.getVisualizationCode());

        log.info(result);

        return gson.toJson(result);
    }

    /**
     * Get Visualization data for Indicator preview graph generation
     * @return String JSON string containing Indicator visualization code
     */
    @RequestMapping(value = "/getCompIndicatorPreview", method = RequestMethod.GET)
    public @ResponseBody
    String getCompIndicatorPreview(@RequestParam(value="height", required = true) String height,
                                                @RequestParam(value="width", required = true) String width,
                                                @RequestParam(value="indicatorSelect", required = true) String indicatorSelect,
                                                @RequestParam(value="EngineSelect", required = true) String engineSelectId,
                                                @RequestParam(value="selectedChartType", required = true) String selectedChartType,
                                                @RequestParam(value="indicatorNaming", required = true) String indicatorName,
                                                @RequestParam(value="visualizationMappings", required = true) String visualizationMappings) {

        Gson gson = new Gson();
        Type openlapPortMappingType = new TypeToken<List<OpenLAPPortMapping>>(){}.getType();

        String[] selectedIndicatorsID = indicatorSelect.split("\\s*,\\s*");

        EntitySpecification entitySpecificationBean = (EntitySpecification) appContext.getBean("entitySpecifications");

        OperationNumberProcessorDao operationNumberProcessorBean =  (OperationNumberProcessorDao) appContext.getBean("operationNumberProcessor");
        entitySpecificationBean.getCurrentIndicator().setIndicatorName(indicatorName);
        entitySpecificationBean.getCurrentIndicator().setIndicatorType("composite");

        IndicatorPreviewRequest indicatorPreviewRequest = new IndicatorPreviewRequest();
        indicatorPreviewRequest.setIndicatorType("composite");
        Map<String, Object> chartSizeParams = new HashMap<>();
        chartSizeParams.put("width", width);
        chartSizeParams.put("height", height);
        indicatorPreviewRequest.setAdditionalParams(chartSizeParams);

        for(String selectedIndicatorId : selectedIndicatorsID){
            int selectedIndId = Integer.parseInt(selectedIndicatorId);
            SessionIndicator selectedIndicator = null;

            for(SessionIndicator sessionIndicator : entitySpecificationBean.getCurrentQuestion().getSessionIndicators())
                if(sessionIndicator.getIdentifier() == selectedIndId)
                    selectedIndicator = sessionIndicator;

            if(selectedIndicator != null){
                indicatorPreviewRequest.getAnalyticsMethodId().put(selectedIndicator.getIndicatorName(), selectedIndicator.getIndicatorParameters().getAnalyticsMethodId().get("0"));
                indicatorPreviewRequest.getQuery().put(selectedIndicator.getIndicatorName(), selectedIndicator.getHqlQuery().get("0"));
                indicatorPreviewRequest.getQueryToMethodConfig().put(selectedIndicator.getIndicatorName(), selectedIndicator.getIndicatorParameters().getQueryToMethodConfig().get("0"));
            }
        }

        indicatorPreviewRequest.setVisualizationMethodId(Long.parseLong(selectedChartType));
        indicatorPreviewRequest.setVisualizationFrameworkId(Long.parseLong(engineSelectId));

        if (!visualizationMappings.isEmpty()) {
            List<OpenLAPPortMapping> visualizationOpenLAPPortMappingList = gson.fromJson(visualizationMappings, openlapPortMappingType);
            OpenLAPPortConfig config = new OpenLAPPortConfig();
            for (OpenLAPPortMapping visualizationPortMapping : visualizationOpenLAPPortMappingList)
                config.getMapping().add(visualizationPortMapping);
            indicatorPreviewRequest.setMethodToVisualizationConfig(config);
        }

        RestTemplate restTemplate = new RestTemplate();
        String url = AnalyticsEngineServerURL + "/AnalyticsEngine/GetCompositeIndicatorPreview";
        String requestJson = gson.toJson(indicatorPreviewRequest);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<String>(requestJson,headers);
        IndicatorPreviewResponse result = restTemplate.postForObject(url, entity, IndicatorPreviewResponse.class);

        if(result.isSuccess())
            entitySpecificationBean.getCurrentIndicator().setVisualization(result.getVisualizationCode());

        return gson.toJson(result);
    }

    @RequestMapping(value = "/finalizeCompIndicator", method = RequestMethod.GET)
    public @ResponseBody
    String finalizeCompIndicator(@RequestParam(value="indicatorSelect", required = true) String indicatorSelect,
                                   @RequestParam(value="EngineSelect", required = true) String engineSelectId,
                                   @RequestParam(value="selectedChartType", required = true) String selectedChartType,
                                   @RequestParam(value="indicatorNaming", required = true) String indicatorName,
                                   @RequestParam(value="visualizationMappings", required = true) String visualizationMappings) {

        Gson gson = new Gson();
        Type openlapPortMappingType = new TypeToken<List<OpenLAPPortMapping>>(){}.getType();

        String[] selectedIndicatorsID = indicatorSelect.split("\\s*,\\s*");

        EntitySpecification entitySpecificationBean = (EntitySpecification) appContext.getBean("entitySpecifications");

        OperationNumberProcessorDao operationNumberProcessorBean =  (OperationNumberProcessorDao) appContext.getBean("operationNumberProcessor");
        entitySpecificationBean.getCurrentIndicator().setIndicatorName(indicatorName);
        entitySpecificationBean.getCurrentIndicator().setIndicatorType("composite");

        for(String selectedIndicatorId : selectedIndicatorsID){
            int selectedIndId = Integer.parseInt(selectedIndicatorId);
            SessionIndicator selectedIndicator = null;

            for(SessionIndicator sessionIndicator : entitySpecificationBean.getCurrentQuestion().getSessionIndicators())
                if(sessionIndicator.getIdentifier() == selectedIndId)
                    selectedIndicator = sessionIndicator;

            if(selectedIndicator != null){
                entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getAnalyticsMethodId().put(selectedIndicator.getIndicatorName(), selectedIndicator.getIndicatorParameters().getAnalyticsMethodId().get("0"));
                entitySpecificationBean.getCurrentIndicator().getHqlQuery().put(selectedIndicator.getIndicatorName(), selectedIndicator.getHqlQuery().get("0"));
                entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getQueryToMethodConfig().put(selectedIndicator.getIndicatorName(), selectedIndicator.getIndicatorParameters().getQueryToMethodConfig().get("0"));
            }
        }

        entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().setVisualizationLibrary(engineSelectId);
        entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().setVisualizationType(selectedChartType);

        if (!visualizationMappings.isEmpty()) {
            List<OpenLAPPortMapping> visualizationOpenLAPPortMappingList = gson.fromJson(visualizationMappings, openlapPortMappingType);
            OpenLAPPortConfig config = new OpenLAPPortConfig();
            for (OpenLAPPortMapping visualizationPortMapping : visualizationOpenLAPPortMappingList)
                config.getMapping().add(visualizationPortMapping);

            entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().setMethodToVisualizationConfig(config);
        }

        AnalyticsEngineController analyticsEngineController = appContext.getBean(AnalyticsEngineController.class);

        String visualizationCode;
        if(entitySpecificationBean.getCurrentIndicator().getVisualization() == null || entitySpecificationBean.getCurrentIndicator().getVisualization().isEmpty()){
            String previewResponse = analyticsEngineController.getCompIndicatorPreview("xxxheightxxx", "xxxwidthxxx", indicatorSelect, engineSelectId, selectedChartType, indicatorName, visualizationMappings);

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

            sessionIndicator.setIdentifier(entitySpecificationBean.getCurrentIndicator().getIdentifier());
        } catch (CloneNotSupportedException e) {
            IndicatorDataset indicatorDataset = new IndicatorDataset();
            IndicatorParameters indicatorParameters = entitySpecificationBean.getCurrentIndicator().getIndicatorParameters();

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
            sessionQuestion.setGoalId(entitySpecificationBean.getGoalId());
            sessionQuestion.setQuestionName(entitySpecificationBean.getQuestionName());
            sessionQuestion.getSessionIndicators().add(sessionIndicator);

            entitySpecificationBean.setCurrentQuestion(sessionQuestion);
        }
        else {
            entitySpecificationBean.getCurrentQuestion().getSessionIndicators().add(sessionIndicator);
        }
        return gson.toJson(entitySpecificationBean.getCurrentQuestion());
    }

    @RequestMapping(value = "/searchAllQuestions", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    String searchAllQuestions(HttpServletRequest request) {

        List<QuestionResponse> questionResponses = null;
        List<QuestionResponse> pagedQuestionResponses = new ArrayList<>();

        GLAQuestionJsonObject glaQuestionJsonObject = new GLAQuestionJsonObject();

        Integer idisplayStart = 0;
        Integer iSortingCols =0;
        String colName = "";
        String sortDirection = "";


        if(request.getParameter("iSortingCols") != null)
            iSortingCols = Integer.valueOf(request.getParameter("iSortingCols"));
        boolean isSorting = iSortingCols == 1;


        if (null != request.getParameter("iDisplayStart")) {
            idisplayStart = Integer.valueOf(request.getParameter("iDisplayStart"));
        }

        String searchParameter = request.getParameter("sSearch");
        searchParameter = searchParameter == null? "" : searchParameter;

        Integer pageDisplayLength = Integer.parseInt(request.getParameter("iDisplayLength"));


        if(isSorting) {
            Integer isortCol = Integer.valueOf(request.getParameter("iSortCol_0"));
            sortDirection = request.getParameter("sSortDir_0");
            switch (isortCol)
            {
                case 0:
                    colName = "id";
                    break;
                case 1:
                    colName = "name";
                    break;
                case 2:
                    colName = "indicatorCount";
                    break;
                default:
                    break;
            }
        }

        String url = AnalyticsEngineServerURL + "/AnalyticsEngine/SearchQuestions?"
                + "searchParameter=" + searchParameter
                + "&exactSearch=false&colName=" + colName
                + "&sortDirection=" + sortDirection
                + "&sort=" + isSorting;

        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject (url, String.class);

        Gson gson = new Gson();
        Type questionResponsesType = new TypeToken<List<QuestionResponse>>(){}.getType();
        questionResponses = gson.fromJson(result, questionResponsesType);

        if(idisplayStart != -1){
            Integer endRange = idisplayStart+pageDisplayLength;
            if(endRange >questionResponses.size())
                endRange = questionResponses.size();
            for(int i=idisplayStart; i<endRange; i++){
                pagedQuestionResponses.add(questionResponses.get(i));
            }
        }

        glaQuestionJsonObject.setiTotalDisplayRecords(questionResponses.size());
        glaQuestionJsonObject.setiTotalRecords(questionResponses.size());
        glaQuestionJsonObject.setAaData(pagedQuestionResponses);

        return gson.toJson(glaQuestionJsonObject);
    }

    @RequestMapping(value = "/searchAllIndicators", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    String searchAllIndicators(HttpServletRequest request) {

        List<IndicatorResponse> indicatorResponses = null;
        List<IndicatorResponse> pagedIndicatorResponses = new ArrayList<>();

        GLAIndicatorJsonObject glaIndicatorJsonObject = new GLAIndicatorJsonObject();

        Integer idisplayStart = 0;
        Integer iSortingCols =0;
        String colName = "";
        String sortDirection = "";


        if(request.getParameter("iSortingCols") != null)
            iSortingCols = Integer.valueOf(request.getParameter("iSortingCols"));
        boolean isSorting = iSortingCols == 1;


        if (null != request.getParameter("iDisplayStart")) {
            idisplayStart = Integer.valueOf(request.getParameter("iDisplayStart"));
        }

        String searchParameter = request.getParameter("sSearch");
        searchParameter = searchParameter == null? "" : searchParameter;

        Integer pageDisplayLength = Integer.parseInt(request.getParameter("iDisplayLength"));


        if(isSorting) {
            Integer isortCol = Integer.valueOf(request.getParameter("iSortCol_0"));
            sortDirection = request.getParameter("sSortDir_0");
            switch (isortCol)
            {
                case 0:
                    colName = "id";
                    break;
                case 1:
                    colName = "name";
                    break;
                case 2:
                    colName = "short_name";
                    break;
                default:
                    break;
            }
        }

        String url = AnalyticsEngineServerURL + "/AnalyticsEngine/SearchIndicators?"
                + "searchParameter=" + searchParameter
                + "&exactSearch=false&colName=" + colName
                + "&sortDirection=" + sortDirection
                + "&sort=" + isSorting;

        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject (url, String.class);

        Gson gson = new Gson();
        Type indicatorResponsesType = new TypeToken<List<IndicatorResponse>>(){}.getType();
        indicatorResponses = gson.fromJson(result, indicatorResponsesType);

        if(idisplayStart != -1){
            Integer endRange = idisplayStart+pageDisplayLength;
            if(endRange >indicatorResponses.size())
                endRange = indicatorResponses.size();
            for(int i=idisplayStart; i<endRange; i++){
                pagedIndicatorResponses.add(indicatorResponses.get(i));
            }
        }

        glaIndicatorJsonObject.setiTotalDisplayRecords(indicatorResponses.size());
        glaIndicatorJsonObject.setiTotalRecords(indicatorResponses.size());
        glaIndicatorJsonObject.setAaData(pagedIndicatorResponses);

        return gson.toJson(glaIndicatorJsonObject);
    }

    @RequestMapping(value = "/getIndicatorsByQuestionId", method = RequestMethod.GET)
    public @ResponseBody String GetIndicatorsByQuestionId(
            @RequestParam(value="questionId", required = true)long questionId) {

        String url;

        if(questionId == 0)
            url = AnalyticsEngineServerURL + "/AnalyticsEngine/SearchIndicators?"
                    + "searchParameter=&exactSearch=false&colName=id&sortDirection=asc&sort=true";
        else
            url = AnalyticsEngineServerURL + "/AnalyticsEngine/GetIndicatorsByQuestionId?questionId=" + questionId;

        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject (url, String.class);

        Gson gson = new Gson();
        Type indicatorResponsesType = new TypeToken<List<IndicatorResponse>>(){}.getType();
        List<IndicatorResponse> indicatorResponses = gson.fromJson(result, indicatorResponsesType);

        GLAIndicatorJsonObject glaIndicatorJsonObject = new GLAIndicatorJsonObject();

        glaIndicatorJsonObject.setiTotalDisplayRecords(indicatorResponses.size());
        glaIndicatorJsonObject.setiTotalRecords(indicatorResponses.size());
        glaIndicatorJsonObject.setAaData(indicatorResponses);

        return gson.toJson(glaIndicatorJsonObject);
    }

    /*@RequestMapping(value = "/getIndicatorsByQuestionId", method = RequestMethod.GET)
    public @ResponseBody
    String getIndicatorsByQuestionId(@RequestParam(value="id", required = true) String id) {

        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject (
                AnalyticsEngineServerURL + "/AnalyticsEngine/GetIndicatorsByQuestionId?questionId=1",
                String.class);

        Gson gson = new Gson();
        Type indicatorResponseListType = new TypeToken<List<IndicatorResponse>>(){}.getType();
        List<IndicatorResponse> indicatorResponseList = gson.fromJson(result, indicatorResponseListType);

        return gson.toJson(indicatorResponseList);
    }*/

    @RequestMapping(value = "/getIndicatorRequestCode", method = RequestMethod.GET)
    public @ResponseBody String GetIndicatorRequestCode(
            @RequestParam(value="indicatorId", required = true)long indicatorId) {

        String url = AnalyticsEngineServerURL + "/AnalyticsEngine/GetIndicatorRequestCode?indicatorId=" + indicatorId;;

        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject (url, String.class);

        return result;
    }

    @RequestMapping(value = "/getQuestionRequestCode", method = RequestMethod.GET)
    public @ResponseBody String GetQuestionRequestCode(
            @RequestParam(value="questionId", required = true)long questionId) {

        String url = AnalyticsEngineServerURL + "/AnalyticsEngine/GetQuestionRequestCode?questionId=" + questionId;;

        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject (url, String.class);

        return result;
    }

    @RequestMapping(value = "/loadIndicatorToSession", method = RequestMethod.GET)
    public @ResponseBody
    String  loadIndicatorToSession(@RequestParam(value="indicatorId" ,required = true)long indicatorId,
                                   @RequestParam(value="loadTemplate" ,required = false)String loadTemplate) {

        //GLAIndicatorDao glaIndicatorBean = (GLAIndicatorDao) appContext.getBean("glaIndicator");
        EntitySpecification entitySpecificationBean = (EntitySpecification) appContext.getBean("entitySpecifications");
        IndicatorParameters indicatorParameters = null;

        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject (
                AnalyticsEngineServerURL + "/AnalyticsEngine/GetIndicatorById?indicatorId=" + indicatorId,
                String.class);

        Gson gson = new Gson();
        IndicatorResponse indicatorResponse = gson.fromJson(result, IndicatorResponse.class);

        indicatorParameters = gson.fromJson(indicatorResponse.getParameters(), IndicatorParameters.class);

        entitySpecificationBean.reset();
        entitySpecificationBean.getCurrentIndicator().setIndicatorParameters(indicatorParameters);
        entitySpecificationBean.getCurrentIndicator().setIndicatorType(indicatorResponse.getIndicatorType());

//        if(loadTemplate != null && indicatorResponse.getIndicatorType().equals("simple")) {
//            IndicatorDataset indicatorDataset = new IndicatorDataset();
//
//            //indicatorDataset.setEntityValues(new ArrayList<EntityValues>());
//            Iterator<EntityValues> entityIterator = indicatorParameters.getIndicatorDataset().get("0").getEntityValues().iterator();
//            while(entityIterator.hasNext()) {
//                indicatorDataset.getEntityValues().add(entityIterator.next().clone());
//            }
//            //indicatorDataset.setUserSpecifications(new ArrayList<UserSearchSpecifications>());
//            Iterator<UserSearchSpecifications> userSpecIterator = indicatorParameters.getIndicatorDataset().get("0").getUserSpecifications().iterator();
//            while(userSpecIterator.hasNext()){
//                indicatorDataset.getUserSpecifications().add(userSpecIterator.next().clone());
//            }
//            //indicatorDataset.setSessionSpecifications(new ArrayList<SessionSpecifications>());
//            Iterator<SessionSpecifications> sessionSpecIterator = indicatorParameters.getIndicatorDataset().get("0").getSessionSpecifications().iterator();
//            while(sessionSpecIterator.hasNext()){
//                indicatorDataset.getSessionSpecifications().add(sessionSpecIterator.next().clone());
//            }
//            //indicatorDataset.setTimeSpecifications(new ArrayList<TimeSearchSpecifications>());
//            Iterator<TimeSearchSpecifications> timeSpecIterator = indicatorParameters.getIndicatorDataset().get("0").getTimeSpecifications().iterator();
//            while(timeSpecIterator.hasNext()){
//                indicatorDataset.getTimeSpecifications().add(timeSpecIterator.next().clone());
//            }
//
//            entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getIndicatorDataset().put("0", indicatorDataset);
//        }

        return gson.toJson(indicatorResponse);
    }

    /**
     * Get Visualization data for Question Indicators graph generation
     * @return String JSON string containing Indicator visualization code
     */
//    @RequestMapping(value = "/getQuestionVisualizationCode", method = RequestMethod.GET)
//    public @ResponseBody
//    String getQuestionVisualizationCode(@RequestParam(value="height", required = true) String height,
//                                                @RequestParam(value="width", required = true) String width) {
//
//        RestTemplate restTemplate = new RestTemplate();
//        String result = restTemplate.getForObject (
//                AnalyticsEngineServerURL + "/AnalyticsEngine/GetIndicatorData?tid=1&cid=15ws-14118&pid=1642113431&width=" + width + "&height=" + height + "&start=20151001&end=20160229",
//                String.class);
//
//        Gson gson = new Gson();
//        return gson.toJson(result);
//    }

    /**
     * Get data columns by category name to populate Analytics methods mapping
     * @return String JSON string containing ids, titles and description of each data column
     */
    @RequestMapping(value = "/getDataColumnsByCatName", method = RequestMethod.GET)
    public @ResponseBody
    String getDataColumnsByCatName(@RequestParam(value="categoryName", required = true) String categoryName) {

        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject (
                AnalyticsEngineServerURL + "/AnalyticsEngine/GetDataColumnsByCatName?categoryName=" + categoryName,
                String.class);

        Gson gson = new Gson();
        Type olapColumnConfigurationDataListType = new TypeToken<List<OpenLAPColumnConfigData>>(){}.getType();
        List<OpenLAPColumnConfigData> olapColumnConfigurationDataList = gson.fromJson(result, olapColumnConfigurationDataListType);

        return gson.toJson(olapColumnConfigurationDataList);
    }

    /**
     * Get data columns by category name to populate Analytics methods mapping
     * @return String JSON string containing ids, titles and description of each data column
     */
    @RequestMapping(value = "/getDataColumnsByCatIDs", method = RequestMethod.GET)
    public @ResponseBody
    String getDataColumnsByCatIDs(@RequestParam(value="categoryIDs", required = true) String categoryIDs) {

        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject (
                AnalyticsEngineServerURL + "/AnalyticsEngine/GetDataColumnsByCatID?categoryIDs=" + categoryIDs,
                String.class);

        String[] catIDs = categoryIDs.split("\\s*,\\s*");

        List<Integer> catIDList = new ArrayList<Integer>();
        for (String catID: catIDs){
            catIDList.add(Integer.parseInt(catID));
        }

        EntitySpecification entitySpecificationBean = (EntitySpecification) appContext.getBean("entitySpecifications");
        if(entitySpecificationBean.getCurrentIndicator().getIndicatorType().equals("simple"))
            if(!entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getIndicatorDataset().containsKey("0"))
                entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getIndicatorDataset().put("0", new IndicatorDataset());

            entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getIndicatorDataset().get("0").setSelectedMinor(catIDList);


        Gson gson = new Gson();
        Type olapColumnConfigurationDataListType = new TypeToken<List<OpenLAPColumnConfigData>>(){}.getType();
        List<OpenLAPColumnConfigData> olapColumnConfigurationDataList = gson.fromJson(result, olapColumnConfigurationDataListType);

        return gson.toJson(olapColumnConfigurationDataList);
    }

    @RequestMapping(value = {"/getAttributesValues"}, method = RequestMethod.GET)
    public
    @ResponseBody String GetAttributesValues(
            @RequestParam(value = "source", required = false) String source,
            @RequestParam(value = "platform", required = false) String platform,
            @RequestParam(value = "action", required = false) String action,
            @RequestParam(value="categoryIDs", required = true) String categoryIds,
            @RequestParam(value="key", required = true) String key,
            HttpServletRequest request) {

        if(source==null || source.isEmpty()) source = ""; else source = "&source=" + source ;
        if(platform==null || platform.isEmpty()) platform = ""; else platform = "&platform=" + source ;
        if(action==null || action.isEmpty()) action = ""; else action = "&action=" + action ;

        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject (
                AnalyticsEngineServerURL + "/AnalyticsEngine/GetAttributesValues?categoryIds=" + categoryIds + "&key=" + key + action + source + platform,
                String.class);

        return result;
    }

    /**
     * Get analytics method inputs to populate Analytics methods mapping
     * @return String JSON string containing ids, titles and description of each input
     */
    @RequestMapping(value = "/getAnalyticsMethodInputs", method = RequestMethod.GET)
    public @ResponseBody
    String getAnalyticsMethodInputs(@RequestParam(value="id", required = true) String id) {

        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject (
                AnalyticsEngineServerURL + "/AnalyticsEngine/GetAnalyticsMethodInputs?id=" + id,
                String.class);

        Gson gson = new Gson();
        Type olapColumnConfigurationDataListType = new TypeToken<List<OpenLAPColumnConfigData>>(){}.getType();
        List<OpenLAPColumnConfigData> olapColumnConfigurationDataList = gson.fromJson(result, olapColumnConfigurationDataListType);

        return gson.toJson(olapColumnConfigurationDataList);
    }

    /**
     * Get visualization method inputs to populate visualization mapping
     * @return String JSON string containing ids, titles and description of each input
     */
    @RequestMapping(value = "/getVisualizationMethodInputs", method = RequestMethod.GET)
    public @ResponseBody
    String getVisualizationMethodInputs(@RequestParam(value="frameworkId", required = true) String frameworkId,
                                            @RequestParam(value="methodId", required = true) String methodId) {

        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject (
                AnalyticsEngineServerURL + "/AnalyticsEngine/GetVisualizationMethodInputs?frameworkId=" + frameworkId + "&methodId=" + methodId,
                String.class);

        Gson gson = new Gson();
        Type olapColumnConfigurationDataListType = new TypeToken<List<OpenLAPColumnConfigData>>(){}.getType();
        List<OpenLAPColumnConfigData> olapColumnConfigurationDataList = gson.fromJson(result, olapColumnConfigurationDataListType);

        return gson.toJson(olapColumnConfigurationDataList);
    }

    /**
     * Get analytics method inputs to populate visualization mapping
     * @return String JSON string containing ids, titles and description of each input
     */
    @RequestMapping(value = "/getAnalyticsMethodOutputs", method = RequestMethod.GET)
    public @ResponseBody
    String getAnalyticsMethodOutputs(@RequestParam(value="id", required = true) String id) {

        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject (
                AnalyticsEngineServerURL + "/AnalyticsEngine/GetAnalyticsMethodOutputs?id=" + id,
                String.class);

        Gson gson = new Gson();
        Type olapColumnConfigurationDataListType = new TypeToken<List<OpenLAPColumnConfigData>>(){}.getType();
        List<OpenLAPColumnConfigData> olapColumnConfigurationDataList = gson.fromJson(result, olapColumnConfigurationDataListType);

        return gson.toJson(olapColumnConfigurationDataList);
    }

    /**
     * Get all questions saved in the database
     * @return String JSON string containing ids, names and indicator count of each question
     */
    @RequestMapping(value = "/getQuestions", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    String getQuestions(HttpServletRequest request) {

        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject (
                AnalyticsEngineServerURL + "/GetQuestions",
                String.class);

        Gson gson = new Gson();
        Type questionResponseListType = new TypeToken<List<QuestionResponse>>(){}.getType();
        List<QuestionResponse> questionResponseList = gson.fromJson(result, questionResponseListType);
        return gson.toJson(questionResponseList);
    }

    /**
     * Get List of all Event Sources
     * @return String JSON string containing ids and names of Event Sources
     */
    @RequestMapping(value = "/listAllEventSources", method = RequestMethod.GET)
    public @ResponseBody
    String getAllEventSources() {

        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(
                AnalyticsEngineServerURL + "/AnalyticsEngine/GetEventSources",
                String.class);

        return result;
    }

    /**
     * Get List of all Event Platforms
     * @return String JSON string containing ids and names of Event Platforms
     */
    @RequestMapping(value = "/listAllEventPlatforms", method = RequestMethod.GET)
    public @ResponseBody
    String getAllEventPlatforms() {

        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(
                AnalyticsEngineServerURL + "/AnalyticsEngine/GetEventPlatforms",
                String.class);

        return result;
    }

    /**
     * Get List of all Event Actions
     * @return String JSON string containing ids and names of Event Actions
     */
    @RequestMapping(value = "/listAllEventActions", method = RequestMethod.GET)
    public @ResponseBody
    String getAllEventActions() {

        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(
                AnalyticsEngineServerURL + "/AnalyticsEngine/GetEventActions",
                String.class);

        return result;
    }

    /**
     * Get List of all Event Sessions
     * @return String JSON string containing ids and names of Event Sessions
     */
    @RequestMapping(value = "/listAllEventSessions", method = RequestMethod.GET)
    public @ResponseBody
    String getAllEventSessions() {

        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(
                AnalyticsEngineServerURL + "/AnalyticsEngine/GetEventSessions",
                String.class);

        return result;
    }

    /**
     * Get List of all Event Timestamps
     * @return String JSON string containing ids and names of Event Timestamps
     */
    @RequestMapping(value = "/listAllEventTimestamps", method = RequestMethod.GET)
    public @ResponseBody
    String getAllEventTimestamps() {

        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject (
                AnalyticsEngineServerURL + "/AnalyticsEngine/GetEventTimestamps",
                String.class);

        return result;
    }

    /**
     * Get List of all Event Timestamps
     * @return String JSON string containing ids and names of Event Timestamps
     */
    @RequestMapping(value = "/getDistinctCategories", method = RequestMethod.GET)
    public @ResponseBody String GetDistinctCategories(
            @RequestParam(value = "source", required = false) String source,
            @RequestParam(value = "platform", required = false) String platform,
            @RequestParam(value = "action", required = false) String action) {

        if(source==null) source = "";
        if(platform==null) platform = "";
        if(action==null) action = "";

        List<String> sources = Arrays.asList(source.split("\\s*,\\s*"));
        List<String> platforms = Arrays.asList(platform.split("\\s*,\\s*"));
        List<String> actions = Arrays.asList(action.split("\\s*,\\s*"));


        EntitySpecification entitySpecificationBean = (EntitySpecification) appContext.getBean("entitySpecifications");

        if(entitySpecificationBean.getCurrentIndicator().getIndicatorType().equals("simple")){
            if(!entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getIndicatorDataset().containsKey("0"))
                entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getIndicatorDataset().put("0", new IndicatorDataset());

            entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getIndicatorDataset().get("0").setSelectedAction(actions);
            entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getIndicatorDataset().get("0").setSelectedPlatform(platforms);
            entitySpecificationBean.getCurrentIndicator().getIndicatorParameters().getIndicatorDataset().get("0").setSelectedSource(sources);
        }
            //entitySpecificationBean.getIndicatorDataset().get("0").setSelectedMinor(catIDList);



        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject (
                AnalyticsEngineServerURL + "/AnalyticsEngine/GetDistinctCategories?action="+ action + "&source=" + source + "&platform="+platform,
                String.class);

        return result;
    }

    @RequestMapping(value = {"/RequestAnalyticsGoal"}, method = RequestMethod.GET)
    public @ResponseBody String SaveGoal(@RequestParam(value="name", required = true) String name,
                           @RequestParam(value="description", required = true) String description,
                           @RequestParam(value="author", required = true) String author) {

        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject (
                AnalyticsEngineServerURL + "/AnalyticsEngine/SaveGoal?name="+ name + "&description=" + description + "&author="+author,
                String.class);

        return result;
    }

    @RequestMapping(value = "/saveQuestionIndicators", method = RequestMethod.GET)
    public @ResponseBody
    String  saveQnToDB(@RequestParam(value="userName" ,required = true) String userName,
                       @RequestParam(value="goalId", required = true) String goalId,
                       @RequestParam(value="questionName", required = true) String questionName,
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
        for (SessionIndicator sessionIndicator : entitySpecificationBean.getCurrentQuestion().getSessionIndicators()) {
            indicatorSaveRequest = new IndicatorSaveRequest();
            indicatorSaveRequest.setName(sessionIndicator.getIndicatorName());
            indicatorSaveRequest.setCreatedBy(userName);
            indicatorSaveRequest.setIndicatorType(sessionIndicator.getIndicatorType());

            for (Map.Entry<String, String> hqlQuery : sessionIndicator.getHqlQuery().entrySet())
                indicatorSaveRequest.getQuery().put(hqlQuery.getKey(), hqlQuery.getValue());

            for (Map.Entry<String, OpenLAPPortConfig> queryToMethodConfig : sessionIndicator.getIndicatorParameters().getQueryToMethodConfig().entrySet())
                indicatorSaveRequest.getQueryToMethodConfig().put(queryToMethodConfig.getKey(), queryToMethodConfig.getValue());

            for (Map.Entry<String, Long> methodId : sessionIndicator.getIndicatorParameters().getAnalyticsMethodId().entrySet())
                indicatorSaveRequest.getAnalyticsMethodId().put(methodId.getKey(), methodId.getValue());

            for (Map.Entry<String, String> hqlQuery : sessionIndicator.getHqlQuery().entrySet())
                indicatorSaveRequest.getServerID().put(hqlQuery.getKey(), 0L);

//            if (sessionIndicator.getIndicatorType().equals("simple")){
//                indicatorSaveRequest.getQuery().put("0", sessionIndicator.getHqlQuery().get("0"));
//
//                indicatorSaveRequest.getQueryToMethodConfig().put("0", sessionIndicator.getIndicatorParameters().getQueryToMethodConfig().get("0"));
//                indicatorSaveRequest.getServerID().put("0", 0L);
//                indicatorSaveRequest.getAnalyticsMethodId().put("0", sessionIndicator.getIndicatorParameters().getAnalyticsMethodId().get("0"));
//            }

            indicatorSaveRequest.setMethodToVisualizationConfig(sessionIndicator.getIndicatorParameters().getMethodToVisualizationConfig());
            indicatorSaveRequest.setIndicatorClientID(sessionIndicator.getIdentifier());

            indicatorSaveRequest.setVisualizationFrameworkId(Long.parseLong(sessionIndicator.getIndicatorParameters().getVisualizationLibrary()));
            indicatorSaveRequest.setVisualizationMethodId(Long.parseLong(sessionIndicator.getIndicatorParameters().getVisualizationType()));
            indicatorSaveRequest.setParameters(gson.toJson(sessionIndicator.getIndicatorParameters()));
            indicatorSaveRequestList.add(indicatorSaveRequest);
        }
        questionSaveRequest.setIndicators(indicatorSaveRequestList);

        RestTemplate restTemplate = new RestTemplate();
        String url = AnalyticsEngineServerURL + "/AnalyticsEngine/SaveQuestionAndIndicators";
        //String url = AnalyticsEngineServerURL + "/AnalyticsEngine/SaveQuestionAndIndicatorsDummy";
        String requestJson = gson.toJson(questionSaveRequest);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<String>(requestJson,headers);
        QuestionSaveResponse result = restTemplate.postForObject(url, entity, QuestionSaveResponse.class);

        //entitySpecificationBean.setSessionQuestionContainer(new SessionQuestion());
        entitySpecificationBean.completeReset();

        return gson.toJson(result);
    }

    @RequestMapping(value = "/validateIndicatorName", method = RequestMethod.GET)
    public @ResponseBody
    String ValidateIndicatorName(
            @RequestParam(value="name", required = true) String name,
            @RequestParam(value="index", required = false, defaultValue = "-1") String index,
            Model model) {

        String status = "available";
        EntitySpecification entitySpecificationBean = (EntitySpecification) appContext.getBean("entitySpecifications");

        int targetIndicatorIndex = Integer.parseInt(index);

        if (name.isEmpty())
            status = "invalid";
        else{

            RestTemplate restTemplate = new RestTemplate();
            Boolean result = restTemplate.getForObject (
                    AnalyticsEngineServerURL + "/AnalyticsEngine/ValidateIndicatorName?name="+ name,
                    Boolean.class);
            if(!result)
                status = "server";

            //int sessionIndex = 0;

            List<SessionIndicator> sessionIndicators =entitySpecificationBean.getCurrentQuestion().getSessionIndicators();

            for(SessionIndicator sessionIndicator : sessionIndicators) {

                if(sessionIndicator.getIndicatorName().equals(name) && targetIndicatorIndex != sessionIndicator.getIdentifier()) {
                    status = "session";
                    break;
                }
                //sessionIndex++;
            }
        }
        return status;
    }

    @RequestMapping(value = "/validateQuestionName", method = RequestMethod.GET)
    public @ResponseBody
    String ValidateQuestionName(
            @RequestParam(value="name", required = true) String name, Model model) {
        String status = "available";

        if (name.isEmpty())
            status = "invalid";
        else{

            RestTemplate restTemplate = new RestTemplate();
            Boolean result = restTemplate.getForObject (
                    AnalyticsEngineServerURL + "/AnalyticsEngine/ValidateQuestionName?name="+ name,
                    Boolean.class);
            if(!result)
                status = "server";
        }
        return status;
    }


    @RequestMapping(value = {"/GetIndicatorDataHQL/", "/GetIndicatorDataHQL"}, method = RequestMethod.GET)
    public
    @ResponseBody
    String GetIndicatorDataHQL(
            @RequestParam(value = "tid", required = true) String triadID,
            @RequestParam Map<String, String> params) {

        try {

            String paramString = "";


            for (Map.Entry<String, String> entry : params.entrySet()) {
                paramString = paramString + "&" +entry.getKey() + "=" + entry.getValue();
            }

            String url = AnalyticsEngineServerURL + "/AnalyticsEngine/GetIndicatorDataHQL?tid="+ triadID + paramString;

            log.info("Executing indicator request for tid="+ triadID + paramString);

            RestTemplate restTemplate = new RestTemplate();
            String result = restTemplate.getForObject(url, String.class);

            return result;
        } catch (Exception exc) {
            return exc.getMessage();
        }
    }
}