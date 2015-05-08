package com.indicator_engine.model.indicator_system.Number;

import org.hibernate.validator.constraints.NotEmpty;

import javax.mail.Session;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Tanmaya Mahapatra on 31-03-2015.
 */
@SuppressWarnings({"unused", "unchecked"})
public class SelectNumberParameters implements Serializable{

    private final List<String> persistenceObjects = new ArrayList<>();
    private final Map persistenceObjectsRelation = new HashMap();
    private final HashMap<String, ArrayList<String>> retrievableObjects = new HashMap<String, ArrayList<String>>();
    @NotEmpty(message = "Please Select a Persistence Object ")
    private String selectedPersistenceObject;
    private String selectedRetrievableObjects;

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
    private final List<String> entityValueTypes = new ArrayList<>();
    List<String> keys = new ArrayList<>();


    private List<UserSearchSpecifications>  userSpecifications = new ArrayList<UserSearchSpecifications>();
    private final List<String> userSearchTypes = new ArrayList<>();
    private String selecteduserSearchTypes;
    private String searchUserString;
    private String selectedUserString;
    private List<String> selectedSearchStrings = new ArrayList<>();
    private List<String> searchResults = new ArrayList<>();
    private final List<String> searchType = new ArrayList<>();
    private String selectedSearchType;

    private List<SessionSpecifications>  sessionSpecifications = new ArrayList<SessionSpecifications>();
    private final List<String> sessionSearchType = new ArrayList<>();
    private String selectedsessionSearchType;
    private String sessionSearch;

    private List<TimeSearchSpecifications>  timeSpecifications = new ArrayList<TimeSearchSpecifications>();
    private final List<String> timeSearchType = new ArrayList<>();
    private String selectedTimeSearchType;
    private String TimeSearch;
    private final List<String> timeType = new ArrayList<>();
    private  String selectedTimeType;


    private long result ;
    private String hql;
    @Size(min=3, max=50, message="Question Name must be between 3 and 50 characters")
    private String questionName;
    private final List<String> filteringType = new ArrayList<>();
    private String selectedFilteringType;

    public void reset(){
        // Reset everything to your default values
        this.persistenceObjects.clear();
        this.persistenceObjectsRelation.clear();
        this.retrievableObjects.clear();
        this.selectedPersistenceObject = null;
        this.selectedRetrievableObjects = null;

        this.selectedSource = null;
        this.selectedPlatform  =null;
        this.selectedAction = null;
        this.source.clear();
        this.platform.clear();
        this.action.clear();

        this.majors.clear();
        this.minors.clear();
        this.type.clear();
        this.selectedMajor = null;
        this.selectedMinor = null;
        this.selectedType = null;
        this.selectedKeys = null;

        this.evalue = null;
        this.selectedentityValueTypes = null;
        this.entityValues.clear();
        this.entityValueTypes.clear();
        this.keys.clear();

        this.userSpecifications.clear();
        this.userSearchTypes.clear();
        this.selecteduserSearchTypes = null;
        this.searchUserString =null;
        this.selectedUserString = null;
        this.searchResults.clear();
        this.searchType.clear();
        this.selectedSearchType = null;

        this.sessionSpecifications.clear();
        this.sessionSearchType.clear();
        this.selectedsessionSearchType = null;
        this.sessionSearch = null;

        this.timeSpecifications.clear();
        this.timeSearchType.clear();
        this.timeType.clear();
        this.selectedTimeType = null;
        this.TimeSearch = null;
        this.selectedTimeSearchType = null;

        this.result = 0;
        this.hql = null;
        this.questionName = null;

        this.filteringType.clear();
        this.selectedFilteringType = null;

        buildDefault();
    }

    public void buildDefault() {
        this.persistenceObjects.add("GLAEntity");
        this.persistenceObjects.add("H");
        this.persistenceObjectsRelation.put("GLAEntity", "GLAEntity");
        this.persistenceObjectsRelation.put("GLAEntity.key","key");
        this.persistenceObjectsRelation.put("GLAEntity.value","value");
        this.persistenceObjectsRelation.put("GLAEntity.action","glaEvent.action");
        this.persistenceObjectsRelation.put("GLAEntity.source","glaEvent.source");
        this.persistenceObjectsRelation.put("GLAEntity.platform","glaEvent.platform");
        this.persistenceObjectsRelation.put("GLAEntity.major","glaEvent.glaCategory.major");
        this.persistenceObjectsRelation.put("GLAEntity.minor","glaEvent.glaCategory.minor");
        this.persistenceObjectsRelation.put("GLAEntity.type","glaEvent.glaCategory.type");
        this.retrievableObjects.put("GLAEntity", new ArrayList<String>());
        this.retrievableObjects.get("GLAEntity").add(" COUNT(*) ");
        this.entityValueTypes.add("Text");
        this.entityValueTypes.add("Number");
        this.entityValueTypes.add("Regex");
        this.userSearchTypes.add("UserName");
        this.userSearchTypes.add("UserEmail");
        this.sessionSearchType.add("ALL");
        this.sessionSearchType.add("SEARCH LIKE");
        this.timeSearchType.add("ALL");
        this.timeSearchType.add("LIKE");
        this.timeSearchType.add("EXACT");
        this.timeType.add("EXACT");
        this.timeType.add("RANGE");
        this.searchType.add("EXACT");
        this.searchType.add("REGEX");
        this.filteringType.add("AND");
        this.filteringType.add("OR");

    }

    public SelectNumberParameters(){

        buildDefault();

    }

    public String getSelectedUserString() {
        return selectedUserString;
    }

    public void setSelectedUserString(String selectedUserString) {
        this.selectedUserString = selectedUserString;
    }

    public List<String> getSource() {
        return source;
    }

    public List<String> getPlatform() {
        return platform;
    }

    public List<String> getAction() {
        return action;
    }

    public String getSelectedSource() {
        return selectedSource;
    }

    public String getSelectedPlatform() {
        return selectedPlatform;
    }

    public String getSelectedAction() {
        return selectedAction;
    }

    public List<String> getMinors() {
        return minors;
    }

    public HashMap<String, ArrayList<String>> getRetrievableObjects() {
        return retrievableObjects;
    }

    public String getSelectedRetrievableObjects() {
        return selectedRetrievableObjects;
    }


    public List<String> getMajors() {
        return majors;
    }

    public List<String> getType() {
        return type;
    }

    public String getSelectedMinor() {
        return selectedMinor;
    }

    public String getSelectedMajor() {
        return selectedMajor;
    }

    public String getSelectedType() {
        return selectedType;
    }

    public String getSelectedKeys() {
        return selectedKeys;
    }

    public String getEvalue() {
        return evalue;
    }

    public String getSelectedentityValueTypes() {
        return selectedentityValueTypes;
    }

    public List<EntityValues> getEntityValues() {
        return entityValues;
    }

    public List<String> getEntityValueTypes() {
        return entityValueTypes;
    }

    public List<String> getKeys() {
        return keys;
    }

    public List<UserSearchSpecifications> getUserSpecifications() {
        return userSpecifications;
    }

    public List<String> getUserSearchTypes() {
        return userSearchTypes;
    }

    public String getSelecteduserSearchTypes() {
        return selecteduserSearchTypes;
    }

    public String getSearchUserString() {
        return searchUserString;
    }

    public List<String> getSelectedSearchStrings() {
        return selectedSearchStrings;
    }

    public List<String> getSearchResults() {
        return searchResults;
    }

    public List<String> getSearchType() {
        return searchType;
    }

    public List<String> getPersistenceObjects() {
        return persistenceObjects;
    }

    public String getSelectedPersistenceObject() {
        return selectedPersistenceObject;
    }

    public Map getPersistenceObjectsRelation() {
        return persistenceObjectsRelation;
    }

    public String getSelectedSearchType() {
        return selectedSearchType;
    }

    public List<SessionSpecifications> getSessionSpecifications() {
        return sessionSpecifications;
    }

    public List<String> getSessionSearchType() {
        return sessionSearchType;
    }

    public String getSelectedsessionSearchType() {
        return selectedsessionSearchType;
    }

    public String getSessionSearch() {
        return sessionSearch;
    }

    public List<TimeSearchSpecifications> getTimeSpecifications() {
        return timeSpecifications;
    }

    public List<String> getTimeSearchType() {
        return timeSearchType;
    }

    public String getSelectedTimeSearchType() {
        return selectedTimeSearchType;
    }

    public String getTimeSearch() {
        return TimeSearch;
    }

    public List<String> getTimeType() {
        return timeType;
    }

    public String getSelectedTimeType() {
        return selectedTimeType;
    }

    public long getResult() {
        return result;
    }

    public String getHql() {
        return hql;
    }

    public List<String> getFilteringType() {
        return filteringType;
    }

    public String getSelectedFilteringType() {
        return selectedFilteringType;
    }

    public void setSource(List<String> source) {
        this.source = source;
    }

    public void setPlatform(List<String> platform) {
        this.platform = platform;
    }

    public void setAction(List<String> action) {
        this.action = action;
    }

    public void setSelectedSource(String selectedSource) {
        this.selectedSource = selectedSource;
    }

    public void setSelectedPlatform(String selectedPlatform) {
        this.selectedPlatform = selectedPlatform;
    }

    public void setSelectedAction(String selectedAction) {
        this.selectedAction = selectedAction;
    }

    public void setMinors(List<String> minors) {
        this.minors = minors;
    }

    public void setMajors(List<String> majors) {
        this.majors = majors;
    }

    public void setType(List<String> type) {
        this.type = type;
    }

    public void setSelectedMinor(String selectedMinor) {
        this.selectedMinor = selectedMinor;
    }

    public void setSelectedMajor(String selectedMajor) {
        this.selectedMajor = selectedMajor;
    }

    public void setSelectedType(String selectedType) {
        this.selectedType = selectedType;
    }

    public void setSelectedKeys(String selectedKeys) {
        this.selectedKeys = selectedKeys;
    }

    public void setEvalue(String evalue) {
        this.evalue = evalue;
    }

    public void setSelectedentityValueTypes(String selectedentityValueTypes) {
        this.selectedentityValueTypes = selectedentityValueTypes;
    }

    public void setEntityValues(List<EntityValues> entityValues) {
        this.entityValues = entityValues;
    }

    public void setKeys(List<String> keys) {
        this.keys = keys;
    }

    public void setUserSpecifications(List<UserSearchSpecifications> userSpecifications) {
        this.userSpecifications = userSpecifications;
    }

    public void setSelecteduserSearchTypes(String selecteduserSearchTypes) {
        this.selecteduserSearchTypes = selecteduserSearchTypes;
    }

    public void setSearchUserString(String searchUserString) {
        this.searchUserString = searchUserString;
    }

    public void setSelectedSearchStrings(List<String> selectedSearchStrings) {
        this.selectedSearchStrings = selectedSearchStrings;
    }

    public void setSearchResults(List<String> searchResults) {
        this.searchResults = searchResults;
    }

    public void setSelectedSearchType(String selectedSearchType) {
        this.selectedSearchType = selectedSearchType;
    }

    public void setSessionSpecifications(List<SessionSpecifications> sessionSpecifications) {
        this.sessionSpecifications = sessionSpecifications;
    }

    public void setSelectedsessionSearchType(String selectedsessionSearchType) {
        this.selectedsessionSearchType = selectedsessionSearchType;
    }

    public void setSessionSearch(String sessionSearch) {
        this.sessionSearch = sessionSearch;
    }

    public void setTimeSpecifications(List<TimeSearchSpecifications> timeSpecifications) {
        this.timeSpecifications = timeSpecifications;
    }

    public void setSelectedTimeSearchType(String selectedTimeSearchType) {
        this.selectedTimeSearchType = selectedTimeSearchType;
    }

    public void setTimeSearch(String timeSearch) {
        TimeSearch = timeSearch;
    }

    public void setSelectedTimeType(String selectedTimeType) {
        this.selectedTimeType = selectedTimeType;
    }

    public void setResult(long result) {
        this.result = result;
    }

    public void setHql(String hql) {
        this.hql = hql;
    }

    public void setSelectedFilteringType(String selectedFilteringType) {
        this.selectedFilteringType = selectedFilteringType;
    }

    public void setSelectedPersistenceObject(String selectedPersistenceObject) {
        this.selectedPersistenceObject = selectedPersistenceObject;
    }

    public void setSelectedRetrievableObjects(String selectedRetrievableObjects) {
        this.selectedRetrievableObjects = selectedRetrievableObjects;
    }

    public String getQuestionName() {
        return questionName;
    }

    public void setQuestionName(String questionName) {
        this.questionName = questionName;
    }
}




