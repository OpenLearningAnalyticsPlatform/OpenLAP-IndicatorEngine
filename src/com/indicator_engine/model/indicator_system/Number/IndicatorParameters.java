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

import de.rwthaachen.openlap.analyticsmodules.model.OpenLAPDataSetMergeMapping;
import de.rwthaachen.openlap.dataset.OpenLAPPortConfig;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Tanmaya Mahapatra on 21-06-2015.
 */
@Component
@Scope("session")
public class IndicatorParameters implements Serializable {
    private AtomicInteger count = new AtomicInteger(1);

    private Map<String, IndicatorDataset> indicatorDataset;

    private String visualizationLibrary;
    private String visualizationType;
    private OpenLAPPortConfig methodToVisualizationConfig;
    private String visualizationParams;

    private List<OpenLAPDataSetMergeMapping> combineMappingList;


    public IndicatorParameters() {
        indicatorDataset = new HashMap<>();
        indicatorDataset.put("0", new IndicatorDataset());
    }

    public int addNewDataSource(){
        int newCount = count.getAndIncrement();

        indicatorDataset.put(""+newCount, new IndicatorDataset());

        return newCount;
    }

    public void removeDataSource(String dsid){
        indicatorDataset.remove(dsid);

        return;
    }

    public Map<String, IndicatorDataset> getIndicatorDataset() {
        return indicatorDataset;
    }

    public void setIndicatorDataset(Map<String, IndicatorDataset> indicatorDataset) {
        this.indicatorDataset = indicatorDataset;
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

    public OpenLAPPortConfig getMethodToVisualizationConfig() {
        return methodToVisualizationConfig;
    }

    public void setMethodToVisualizationConfig(OpenLAPPortConfig methodToVisualizationConfig) {
        this.methodToVisualizationConfig = methodToVisualizationConfig;
    }

    public String getVisualizationParams() {
        return visualizationParams;
    }

    public void setVisualizationParams(String visualizationParams) {
        this.visualizationParams = visualizationParams;
    }

    public List<OpenLAPDataSetMergeMapping> getCombineMappingList() {
        return combineMappingList;
    }

    public void setCombineMappingList(List<OpenLAPDataSetMergeMapping> combineMappingList) {
        this.combineMappingList = combineMappingList;
    }
}
