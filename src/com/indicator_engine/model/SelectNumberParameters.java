package com.indicator_engine.model;

import org.springframework.security.core.userdetails.User;
import org.springframework.util.AutoPopulatingList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Tanmaya Mahapatra on 31-03-2015.
 */
public class SelectNumberParameters implements Serializable{

    List<String> source =  new ArrayList<>();
    List<String> platform =  new ArrayList<>();
    List<String> action =  new ArrayList<>();
    private String selectedSource;
    private String selectedPlatform;
    private String selectedAction;
    private String timestamp;
    private String session;


    List<String> minors =  new ArrayList<>();
    List<String> majors =  new ArrayList<>();
    List<String> type =  new ArrayList<>();
    private String selectedMinor;
    private String selectedMajor;
    private String selectedType;

    List<String> keys = new ArrayList<>();
    private String selectedKeys;
    private String value;
    private List<EntityValues>  entityValues = new ArrayList<EntityValues>();
    private List<String> entityValueTypes = new ArrayList<>();

    private long result;
    private String hql;
    public SelectNumberParameters(){
        entityValueTypes.add("Text");
        entityValueTypes.add("Number");
        entityValueTypes.add("Regex");
    }

    public List<String> getEntityValueTypes() {
        return entityValueTypes;
    }

    public void setEntityValueTypes(List<String> entityValueTypes) {
        this.entityValueTypes = entityValueTypes;
    }

    public String getHql() {
        return hql;
    }

    public void setHql(String hql) {
        this.hql = hql;
    }

    public String getValue() {
        return value;
    }


    public void setValue(String value) {
        this.value = value;
    }

    public long getResult() {
        return result;
    }

    public void setResult(long result) {
        this.result = result;
    }

    public List<String> getMinors() {
        return minors;
    }

    public void setMinors(List<String> minors) {
        this.minors = minors;
    }

    public String getSelectedMinor() {
        return selectedMinor;
    }

    public void setSelectedMinor(String selectedMinor) {
        this.selectedMinor = selectedMinor;
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

    public String getSelectedSource() {
        return selectedSource;
    }

    public void setSelectedSource(String selectedSource) {
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

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
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

    public List<EntityValues> getEntityValues() {
        return entityValues;
    }

    public void setEntityValues(List<EntityValues> entityValues) {
        this.entityValues = entityValues;
    }
}



