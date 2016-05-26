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
import com.google.gson.reflect.TypeToken;
import com.indicator_engine.dao.GLACategoryDao;
import com.indicator_engine.dao.GLAEntityDao;
import com.indicator_engine.datamodel.GLACategory;
import com.indicator_engine.indicator_system.Number.OperationNumberProcessorDao;
import com.indicator_engine.model.indicator_system.Number.EntitySpecification;
import de.rwthaachen.openlap.analyticsengine.core.dtos.request.IndicatorPreviewRequest;
import de.rwthaachen.openlap.analyticsengine.core.dtos.response.IndicatorPreviewResponse;
import de.rwthaachen.openlap.analyticsmethods.model.AnalyticsMethodMetadata;
import de.rwthaachen.openlap.analyticsmodules.model.AnalyticsGoal;
import de.rwthaachen.openlap.visualizer.core.dtos.response.VisualizationFrameworksDetailsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;
import java.util.List;

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

    /**
     * Get List of all Analytics Methods
     * @return String JSON string containing ids and names of analytics methods
     */
    @RequestMapping(value = "/listAllAnalyticsMethods", method = RequestMethod.GET)
    public @ResponseBody
    String getAllAnalyticsMethods() {

        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject (
                        "http://137.226.117.226:8080/AnalyticsEngine/GetAnalyticsMethods",
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
                "http://137.226.117.226:8080/AnalyticsEngine/GetGoals",
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
                "http://137.226.117.226:8080/AnalyticsEngine/GetVisualizations",
                String.class);

        Gson gson = new Gson();
        Type frameworkResponseType = new TypeToken<VisualizationFrameworksDetailsResponse>(){}.getType();
        VisualizationFrameworksDetailsResponse frameworkResponse = gson.fromJson(result, frameworkResponseType);

        return gson.toJson(frameworkResponse.getVisualizationFrameworks());
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
                                         @RequestParam(value="indicatorNaming", required = true) String indicatorName) {


        EntitySpecification entitySpecificationBean = (EntitySpecification) appContext.getBean("entitySpecifications");
        GLAEntityDao glaEntityBean = (GLAEntityDao) appContext.getBean("glaEntity");
        GLACategoryDao glaCategoryBean = (GLACategoryDao) appContext.getBean("glaCategory");
        OperationNumberProcessorDao operationNumberProcessorBean =  (OperationNumberProcessorDao) appContext.getBean("operationNumberProcessor");
        entitySpecificationBean.setIndicatorName(indicatorName);
        entitySpecificationBean.setComposite(false);
        GLACategory glaCategory = glaCategoryBean.loadCategoryByName(entitySpecificationBean.getSelectedMinor());
        entitySpecificationBean.setSelectedMajor(glaCategory.getMajor());
        entitySpecificationBean.setSelectedType(glaCategory.getType());
        operationNumberProcessorBean.computeResult(entitySpecificationBean);


        IndicatorPreviewRequest indicatorPreviewRequest = new IndicatorPreviewRequest();
        indicatorPreviewRequest.setAnalyticsMethodId(Long.parseLong(analyticsMethodId));
        indicatorPreviewRequest.setVisualizationMethodId(Long.parseLong(selectedChartType));
        indicatorPreviewRequest.setVisualizationFrameworkId(Long.parseLong(engineSelectId));
        //        entitySpecificationBean.setretrievable
        indicatorPreviewRequest.setQuery(entitySpecificationBean.getHql());
//        indicatorPreviewRequest.setMethodToVisualizationConfig();

        Gson gson = new Gson();
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://137.226.117.226:8080/AnalyticsEngine/GetIndicatorPreview";
        String requestJson = gson.toJson(indicatorPreviewRequest);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<String>(requestJson,headers);
        IndicatorPreviewResponse result = restTemplate.postForObject(url, entity, IndicatorPreviewResponse.class);

        return gson.toJson(result.getVisualizationCode());
    }

    /**
     * Get Visualization data for Question Indicators graph generation
     * @return String JSON string containing Indicator visualization code
     */
    @RequestMapping(value = "/getQuestionVisualizationCode", method = RequestMethod.GET)
    public @ResponseBody
    String getQuestionVisualizationCode(@RequestParam(value="height", required = true) String height,
                                                @RequestParam(value="width", required = true) String width) {

        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject (
                "http://137.226.117.226:8080/AnalyticsEngine/GetIndicatorData?tid=1&cid=15ws-14118&pid=1642113431&width=" + width + "&height=" + height + "&start=20151001&end=20160229",
                String.class);

        Gson gson = new Gson();
        return gson.toJson(result);
    }
}
