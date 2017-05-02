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
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Tanmaya Mahapatra on 21-06-2015.
 */
@Component
@Scope("session")
public class IndicatorParameters implements Serializable {

    private Map<String, IndicatorDataset> indicatorDataset;

    private Map<String, Long> analyticsMethodId;

    private String visualizationLibrary;
    private String visualizationType;

    private String retrievableObjects;
    private List<String> entityDisplayObjects;

    private Map<String, OpenLAPPortConfig> queryToMethodConfig;
    private OpenLAPPortConfig methodToVisualizationConfig;

    public IndicatorParameters() {
        indicatorDataset = new HashMap<>();
        analyticsMethodId = new HashMap<>();
        queryToMethodConfig = new HashMap<>();
    }

    public Map<String, IndicatorDataset> getIndicatorDataset() {
        return indicatorDataset;
    }

    public void setIndicatorDataset(Map<String, IndicatorDataset> indicatorDataset) {
        this.indicatorDataset = indicatorDataset;
    }

    public Map<String, Long> getAnalyticsMethodId() {
        return analyticsMethodId;
    }

    public void setAnalyticsMethodId(Map<String, Long> analyticsMethodId) {
        this.analyticsMethodId = analyticsMethodId;
    }

    public String getVisualizationLibrary() {
        return visualizationLibrary;
    }

    public void setVisualizationLibrary(String visualizationLibrary) {
        this.visualizationLibrary = visualizationLibrary;
    }

    public String getVisualizationType() {
        return visualizationType;
    }

    public void setVisualizationType(String visualizationType) {
        this.visualizationType = visualizationType;
    }

    public String getRetrievableObjects() {
        return retrievableObjects;
    }

    public void setRetrievableObjects(String retrievableObjects) {
        this.retrievableObjects = retrievableObjects;
    }

    public List<String> getEntityDisplayObjects() {
        return entityDisplayObjects;
    }

    public void setEntityDisplayObjects(List<String> entityDisplayObjects) {
        this.entityDisplayObjects = entityDisplayObjects;
    }

    public Map<String, OpenLAPPortConfig> getQueryToMethodConfig() {
        return queryToMethodConfig;
    }

    public void setQueryToMethodConfig(Map<String, OpenLAPPortConfig> queryToMethodConfig) {
        this.queryToMethodConfig = queryToMethodConfig;
    }

    public OpenLAPPortConfig getMethodToVisualizationConfig() {
        return methodToVisualizationConfig;
    }

    public void setMethodToVisualizationConfig(OpenLAPPortConfig methodToVisualizationConfig) {
        this.methodToVisualizationConfig = methodToVisualizationConfig;
    }
}
