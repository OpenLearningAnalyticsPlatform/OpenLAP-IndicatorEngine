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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tanmaya Mahapatra on 10-06-2015.
 */
public class EntitySpecification implements Serializable {


    List<String> source =  new ArrayList<>();
    List<String> platform =  new ArrayList<>();
    List<String> action =  new ArrayList<>();

    private List<String> selectedSource;
    private String selectedPlatform;
    private String selectedAction;
    List<String> minors =  new ArrayList<>();
    List<String> majors =  new ArrayList<>();
    List<String> type =  new ArrayList<>();
    private String selectedMinor;
    private String selectedMajor;
    private String selectedType;


    private List<String> keys = new ArrayList<>();
    private String evalue;
    private String selectedKeys;
    private final List<String> entityValueTypes = new ArrayList<>();
    private String selectedentityValueTypes;
    private List<EntityValues>  entityValues = new ArrayList<EntityValues>();

    public EntitySpecification(){
        this.evalue = null;
        this.selectedentityValueTypes = null;
        this.entityValues.clear();
        this.entityValueTypes.clear();
        buildDefault();
    }

    public void buildDefault() {
        this.entityValueTypes.add("Text");
        this.entityValueTypes.add("Number");
        this.entityValueTypes.add("Regex");
    }

    public List<String> getKeys() {
        return keys;
    }

    public void setKeys(List<String> keys) {
        this.keys = keys;
    }

    public String getSelectedKeys() {
        return selectedKeys;
    }

    public void setSelectedKeys(String selectedKeys) {
        this.selectedKeys = selectedKeys;
    }

    public List<String> getEntityValueTypes() {
        return entityValueTypes;
    }

    public String getSelectedentityValueTypes() {
        return selectedentityValueTypes;
    }

    public void setSelectedentityValueTypes(String selectedentityValueTypes) {
        this.selectedentityValueTypes = selectedentityValueTypes;
    }

    public List<EntityValues> getEntityValues() {
        return entityValues;
    }

    public void setEntityValues(List<EntityValues> entityValues) {
        this.entityValues = entityValues;
    }

    public String getEvalue() {
        return evalue;
    }

    public void setEvalue(String evalue) {
        this.evalue = evalue;
    }

    public List<String> getSource() {
        return source;
    }

    public void setSource(List<String> source) {
        this.source = source;
    }

    public List<String> getPlatform() {
        return platform;
    }

    public void setPlatform(List<String> platform) {
        this.platform = platform;
    }

    public List<String> getAction() {
        return action;
    }

    public void setAction(List<String> action) {
        this.action = action;
    }

    public List<String> getSelectedSource() {
        return selectedSource;
    }

    public void setSelectedSource(List<String> selectedSource) {
        this.selectedSource = selectedSource;
    }

    public String getSelectedPlatform() {
        return selectedPlatform;
    }

    public void setSelectedPlatform(String selectedPlatform) {
        this.selectedPlatform = selectedPlatform;
    }

    public String getSelectedAction() {
        return selectedAction;
    }

    public void setSelectedAction(String selectedAction) {
        this.selectedAction = selectedAction;
    }

    public List<String> getMinors() {
        return minors;
    }

    public void setMinors(List<String> minors) {
        this.minors = minors;
    }

    public List<String> getMajors() {
        return majors;
    }

    public void setMajors(List<String> majors) {
        this.majors = majors;
    }

    public List<String> getType() {
        return type;
    }

    public void setType(List<String> type) {
        this.type = type;
    }

    public String getSelectedMinor() {
        return selectedMinor;
    }

    public void setSelectedMinor(String selectedMinor) {
        this.selectedMinor = selectedMinor;
    }

    public String getSelectedMajor() {
        return selectedMajor;
    }

    public void setSelectedMajor(String selectedMajor) {
        this.selectedMajor = selectedMajor;
    }

    public String getSelectedType() {
        return selectedType;
    }

    public void setSelectedType(String selectedType) {
        this.selectedType = selectedType;
    }
}
