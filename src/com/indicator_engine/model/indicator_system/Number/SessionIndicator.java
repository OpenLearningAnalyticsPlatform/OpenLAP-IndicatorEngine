package com.indicator_engine.model.indicator_system.Number;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Tanmaya Mahapatra on 07-05-2015.
 */
public class SessionIndicator implements Serializable, Cloneable {
    private static final AtomicInteger count = new AtomicInteger(0);

    private int identifier;
    private String indicatorName;
    private String indicatorType;
    private String visualization;

    //private long loadedIndicatorID;

    private Map<String, String> hqlQuery;

    private IndicatorParameters indicatorParameters;

    public SessionIndicator() {
        this.identifier = count.getAndIncrement();
        hqlQuery = new HashMap<>();
        indicatorParameters = new IndicatorParameters();
    }

    public SessionIndicator(String indicatorName, String indicatorType, String visualization, Map<String, String> hqlQuery, IndicatorParameters indicatorParameters) {
        this.identifier = count.getAndIncrement();
        this.indicatorName = indicatorName;
        this.indicatorType = indicatorType;
        this.visualization = visualization;
        this.hqlQuery = hqlQuery;
        this.indicatorParameters = indicatorParameters;
    }

    public static AtomicInteger getCount() {
        return count;
    }

    public int getIdentifier() {
        return identifier;
    }

    public void setIdentifier(int identifier) {
        this.identifier = identifier;
    }

    public String getIndicatorName() {
        return indicatorName;
    }

    public void setIndicatorName(String indicatorName) {
        this.indicatorName = indicatorName;
    }

    public String getIndicatorType() {
        return indicatorType;
    }

    public void setIndicatorType(String indicatorType) {
        this.indicatorType = indicatorType;
    }

    public String getVisualization() {
        return visualization;
    }

    public void setVisualization(String visualization) {
        this.visualization = visualization;
    }

    public Map<String, String> getHqlQuery() {
        return hqlQuery;
    }

    public void setHqlQuery(Map<String, String> hqlQuery) {
        this.hqlQuery = hqlQuery;
    }

    public IndicatorParameters getIndicatorParameters() {
        return indicatorParameters;
    }

    public void setIndicatorParameters(IndicatorParameters indicatorParameters) {
        this.indicatorParameters = indicatorParameters;
    }

//    public long getLoadedIndicatorID() {
//        return loadedIndicatorID;
//    }
//
//    public void setLoadedIndicatorID(long loadedIndicatorID) {
//        this.loadedIndicatorID = loadedIndicatorID;
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SessionIndicator that = (SessionIndicator) o;

        if (identifier != that.identifier) return false;
        if (indicatorName != null ? !indicatorName.equals(that.indicatorName) : that.indicatorName != null)
            return false;
        if (indicatorType != null ? !indicatorType.equals(that.indicatorType) : that.indicatorType != null)
            return false;
        if (visualization != null ? !visualization.equals(that.visualization) : that.visualization != null)
            return false;
        if (hqlQuery != null ? !hqlQuery.equals(that.hqlQuery) : that.hqlQuery != null) return false;
        return indicatorParameters != null ? indicatorParameters.equals(that.indicatorParameters) : that.indicatorParameters == null;

    }

    @Override
    public int hashCode() {
        int result = identifier;
        result = 31 * result + (indicatorName != null ? indicatorName.hashCode() : 0);
        result = 31 * result + (indicatorType != null ? indicatorType.hashCode() : 0);
        result = 31 * result + (visualization != null ? visualization.hashCode() : 0);
        result = 31 * result + (hqlQuery != null ? hqlQuery.hashCode() : 0);
        result = 31 * result + (indicatorParameters != null ? indicatorParameters.hashCode() : 0);
        return result;
    }

    @Override
    public SessionIndicator clone() throws CloneNotSupportedException {
        return (SessionIndicator)super.clone();
    }

    //    public SessionIndicator(String query, String indicatorName,long qid ) {
//        this.identifier = count.getAndIncrement();
//        this.query = query;
//        this.indicatorName = indicatorName;
//        queryID = qid;
//        this.visualization = "";
//    }
//    public SessionIndicator(String query, String indicatorName,long qid, IndicatorXMLData indicatorXMLData, GenIndicatorProps genIndicatorProps, String visualization) {
//        this.identifier = count.getAndIncrement();
//        this.query = query;
//        this.indicatorName = indicatorName;
//        queryID = qid;
//        this.indicatorXMLData = indicatorXMLData;
//        this.genIndicatorProps = genIndicatorProps;
//        this.visualization = visualization;
//    }
//    public SessionIndicator(String query, String indicatorName,long qid,
//                    long props_id, Timestamp last_executionTime, int totalExecutions) {
//        this.identifier = count.getAndIncrement();
//        this.query = query;
//        this.indicatorName = indicatorName;
//        queryID = qid;
//        this.genIndicatorProps.setProps_id(props_id);
//        this.genIndicatorProps.setTotalExecutions(totalExecutions);
//        this.genIndicatorProps.setLast_executionTime(last_executionTime);
//        this.visualization = "";
//    }
//
//    public int getIdentifier() {
//        return identifier;
//    }
//
//    public String getVisualization() {
//        return visualization;
//    }
//
//    public static AtomicInteger getCount() {
//        return count;
//    }
//
//    public long getQueryID() {
//        return queryID;
//    }
//
//    public String getQuery() {
//        return query;
//    }
//
//    public String getIndicatorName() {
//        return indicatorName;
//    }
//
//    public GenIndicatorProps getGenIndicatorProps() {
//        return genIndicatorProps;
//    }
//
//    public void setGenIndicatorProps(GenIndicatorProps genIndicatorProps) {
//        this.genIndicatorProps = genIndicatorProps;
//    }
//
//    public void setGenIndicatorProps(long props_id, Timestamp last_executionTime, int totalExecutions,
//                                     String chartType,String chartEngine, String userName) {
//        this.genIndicatorProps.setProps_id(props_id);
//        this.genIndicatorProps.setTotalExecutions(totalExecutions);
//        this.genIndicatorProps.setLast_executionTime(last_executionTime);
//        this.genIndicatorProps.setChartEngine(chartEngine);
//        this.genIndicatorProps.setChartType(chartType);
//        this.genIndicatorProps.setUserName(userName);
//    }
//
//    public IndicatorXMLData getIndicatorXMLData() {
//        return indicatorXMLData;
//    }
//
//    public void setIndicatorXMLData(IndicatorXMLData indicatorXMLData) {
//        this.indicatorXMLData = indicatorXMLData;
//    }
//
//    public void setIndicatorXMLData(List<String> source, List<String> action, List<String> platform, String major,
//                                    List<Integer> minor, String type,  List<EntityValues> entityValues,
//                                    List<UserSearchSpecifications> userSpecifications,
//                                    List<SessionSpecifications> sessionSpecifications,
//                                    List<TimeSearchSpecifications> timeSpecifications,
//                                    String selectedChartType, String selectedChartEngine) {
//
//        this.indicatorXMLData.setSource(source);
//        this.indicatorXMLData.setAction(action);
//        this.indicatorXMLData.setPlatform(platform);
//        this.indicatorXMLData.setMajor(major);
//        this.indicatorXMLData.setMinor(minor);
//        this.indicatorXMLData.setType(type);
//        this.indicatorXMLData.setEntityValues(entityValues);
//        this.indicatorXMLData.setUserSpecifications(userSpecifications);
//        this.indicatorXMLData.setSessionSpecifications(sessionSpecifications);
//        this.indicatorXMLData.setTimeSpecifications(timeSpecifications);
//        this.indicatorXMLData.setSelectedChartEngine(selectedChartEngine);
//        this.indicatorXMLData.setSelectedChartType(selectedChartType);
//
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//
//        SessionIndicator genQuery = (SessionIndicator) o;
//
//        if (queryID != genQuery.queryID) return false;
//        if (!query.equals(genQuery.query)) return false;
//        if (!indicatorName.equals(genQuery.indicatorName)) return false;
//        if (genIndicatorProps != null ? !genIndicatorProps.equals(genQuery.genIndicatorProps) : genQuery.genIndicatorProps != null)
//            return false;
//        return !(indicatorXMLData != null ? !indicatorXMLData.equals(genQuery.indicatorXMLData) : genQuery.indicatorXMLData != null);
//
//    }
//
//    @Override
//    public int hashCode() {
//        int result = (int) (queryID ^ (queryID >>> 32));
//        result = 31 * result + query.hashCode();
//        result = 31 * result + indicatorName.hashCode();
//        result = 31 * result + (genIndicatorProps != null ? genIndicatorProps.hashCode() : 0);
//        result = 31 * result + (indicatorXMLData != null ? indicatorXMLData.hashCode() : 0);
//        return result;
//    }
}
