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

package com.indicator_engine.model.indicator_system.Number;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tanmaya Mahapatra on 21-06-2015.
 */
@Component
@Scope("session")
public class IndicatorXMLData implements Serializable {

    private List<String> source;
    private String platform;
    private String action;

    private String minor;
    private String major;
    private String type;


    private List<EntityValues> entityValues = new ArrayList<EntityValues>();
    private List<UserSearchSpecifications> userSpecifications = new ArrayList<UserSearchSpecifications>();
    private List<SessionSpecifications> sessionSpecifications = new ArrayList<SessionSpecifications>();
    private List<TimeSearchSpecifications> timeSpecifications = new ArrayList<TimeSearchSpecifications>();

    private String selectedChartType;
    private String selectedChartEngine;

    public IndicatorXMLData() {}

    public IndicatorXMLData(List<String> source, String platform, String action, String minor,
                            String major, String type,  List<EntityValues> entityValues,
                            List<UserSearchSpecifications> userSpecifications,
                            List<SessionSpecifications> sessionSpecifications,
                            List<TimeSearchSpecifications> timeSpecifications,
                            String selectedChartType, String selectedChartEngine)
    {
        this.source = (source);
        this.action = action;
        this.platform = platform;
        this.major = major;
        this.minor = minor;
        this.type = type;
        this.entityValues = entityValues;
        this.userSpecifications = userSpecifications;
        this.sessionSpecifications = sessionSpecifications;
        this.timeSpecifications = timeSpecifications;
        this.selectedChartEngine = selectedChartEngine;
        this.selectedChartType = selectedChartType;
    }



    public List<String> getSource() {
        return source;
    }

    public void setSource(List<String> source) {
        this.source = source;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getMinor() {
        return minor;
    }

    public void setMinor(String minor) {
        this.minor = minor;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<EntityValues> getEntityValues() {
        return entityValues;
    }

    public void setEntityValues(List<EntityValues> entityValues) {
        this.entityValues = entityValues;
    }

    public List<UserSearchSpecifications> getUserSpecifications() {
        return userSpecifications;
    }

    public void setUserSpecifications(List<UserSearchSpecifications> userSpecifications) {
        this.userSpecifications = userSpecifications;
    }

    public List<SessionSpecifications> getSessionSpecifications() {
        return sessionSpecifications;
    }

    public void setSessionSpecifications(List<SessionSpecifications> sessionSpecifications) {
        this.sessionSpecifications = sessionSpecifications;
    }

    public List<TimeSearchSpecifications> getTimeSpecifications() {
        return timeSpecifications;
    }

    public void setTimeSpecifications(List<TimeSearchSpecifications> timeSpecifications) {
        this.timeSpecifications = timeSpecifications;
    }

    public String getSelectedChartType() {
        return selectedChartType;
    }

    public void setSelectedChartType(String selectedChartType) {
        this.selectedChartType = selectedChartType;
    }

    public String getSelectedChartEngine() {
        return selectedChartEngine;
    }

    public void setSelectedChartEngine(String selectedChartEngine) {
        this.selectedChartEngine = selectedChartEngine;
    }
}
