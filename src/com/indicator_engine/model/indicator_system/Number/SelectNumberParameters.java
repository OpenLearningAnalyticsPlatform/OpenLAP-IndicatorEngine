package com.indicator_engine.model.indicator_system.Number;

import javax.mail.Session;
import java.io.Serializable;
import java.util.ArrayList;
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


    List<String> minors =  new ArrayList<>();
    List<String> majors =  new ArrayList<>();
    List<String> type =  new ArrayList<>();
    private String selectedMinor;
    private String selectedMajor;
    private String selectedType;


    private String selectedKeys;
    private String evalue;
    private String selectedentityValueTypes;
    private List<EntityValues>  entityValues = new ArrayList<EntityValues>();
    private List<String> entityValueTypes = new ArrayList<>();
    List<String> keys = new ArrayList<>();


    private List<UserSearchSpecifications>  userSpecifications = new ArrayList<UserSearchSpecifications>();
    private List<String> userSearchTypes = new ArrayList<>();
    private String selecteduserSearchTypes;
    private String searchUserString;
    private List<String> selectedSearchStrings = new ArrayList<>();
    private List<String> searchResults = new ArrayList<>();

    private List<SessionSpecifications>  sessionSpecifications = new ArrayList<SessionSpecifications>();
    private List<String> sessionSearchType = new ArrayList<>();
    private String selectedsessionSearchType;
    private String sessionSearch;

    private List<TimeSearchSpecifications>  timeSpecifications = new ArrayList<TimeSearchSpecifications>();
    private List<String> timeSearchType = new ArrayList<>();
    private String selectedTimeSearchType;
    private String TimeSearch;
    private List<String> timeType = new ArrayList<>();
    private  String selectedTimeType;


    private long result;
    private String hql;


    public SelectNumberParameters(){
        entityValueTypes.add("Text");
        entityValueTypes.add("Number");
        entityValueTypes.add("Regex");
        userSearchTypes.add("UserName");
        userSearchTypes.add("UserEmail");
        sessionSearchType.add("ALL");
        sessionSearchType.add("SEARCH LIKE");
        timeSearchType.add("ALL");
        timeSearchType.add("LIKE");
        timeSearchType.add("EXACT");
        timeType.add("EXACT");
        timeType.add("RANGE");

    }

    public List<String> getSelectedSearchStrings() {
        return selectedSearchStrings;
    }

    public void setSelectedSearchStrings(List<String> selectedSearchStrings) {
        this.selectedSearchStrings = selectedSearchStrings;
    }

    public List<String> getSearchResults() {
        return searchResults;
    }

    public void setSearchResults(List<String> searchResults) {
        this.searchResults = searchResults;
    }

    public String getSelecteduserSearchTypes() {
        return selecteduserSearchTypes;
    }

    public void setSelecteduserSearchTypes(String selecteduserSearchTypes) {
        this.selecteduserSearchTypes = selecteduserSearchTypes;
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

    public String getEvalue() {
        return evalue;
    }

    public void setEvalue(String evalue) {
        this.evalue = evalue;
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

    public List<String> getUserSearchTypes() {
        return userSearchTypes;
    }

    public void setUserSearchTypes(List<String> userSearchTypes) {
        this.userSearchTypes = userSearchTypes;
    }

    public String getSearchUserString() {
        return searchUserString;
    }

    public void setSearchUserString(String searchUserString) {
        this.searchUserString = searchUserString;
    }

    public String getSelectedentityValueTypes() {
        return selectedentityValueTypes;
    }

    public void setSelectedentityValueTypes(String selectedentityValueTypes) {
        this.selectedentityValueTypes = selectedentityValueTypes;
    }

    public List<TimeSearchSpecifications> getTimeSpecifications() {
        return timeSpecifications;
    }

    public void setTimeSpecifications(List<TimeSearchSpecifications> timeSpecifications) {
        this.timeSpecifications = timeSpecifications;
    }

    public List<String> getSessionSearchType() {
        return sessionSearchType;
    }

    public void setSessionSearchType(List<String> sessionSearchType) {
        this.sessionSearchType = sessionSearchType;
    }

    public String getSelectedsessionSearchType() {
        return selectedsessionSearchType;
    }

    public void setSelectedsessionSearchType(String selectedsessionSearchType) {
        this.selectedsessionSearchType = selectedsessionSearchType;
    }

    public String getSessionSearch() {
        return sessionSearch;
    }

    public void setSessionSearch(String sessionSearch) {
        this.sessionSearch = sessionSearch;
    }

    public List<String> getTimeSearchType() {
        return timeSearchType;
    }

    public void setTimeSearchType(List<String> timeSearchType) {
        this.timeSearchType = timeSearchType;
    }

    public String getSelectedTimeSearchType() {
        return selectedTimeSearchType;
    }

    public void setSelectedTimeSearchType(String selectedTimeSearchType) {
        this.selectedTimeSearchType = selectedTimeSearchType;
    }

    public String getTimeSearch() {
        return TimeSearch;
    }

    public void setTimeSearch(String timeSearch) {
        TimeSearch = timeSearch;
    }

    public List<String> getTimeType() {
        return timeType;
    }

    public void setTimeType(List<String> timeType) {
        this.timeType = timeType;
    }

    public String getSelectedTimeType() {
        return selectedTimeType;
    }

    public void setSelectedTimeType(String selectedTimeType) {
        this.selectedTimeType = selectedTimeType;
    }
}




