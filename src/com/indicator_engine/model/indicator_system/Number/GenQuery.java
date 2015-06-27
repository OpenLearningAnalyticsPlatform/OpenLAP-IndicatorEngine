package com.indicator_engine.model.indicator_system.Number;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Tanmaya Mahapatra on 07-05-2015.
 */
public class GenQuery implements Serializable {
    private static final AtomicInteger count = new AtomicInteger(0);
    private final long queryID;
    private final String query;
    private final String indicatorName;
    private GenIndicatorProps genIndicatorProps = new GenIndicatorProps();

    private IndicatorXMLData indicatorXMLData = new IndicatorXMLData() ;

    public GenQuery(String query, String indicatorName,long qid ) {
        this.query = query;
        this.indicatorName = indicatorName;
        queryID = qid;
    }
    public GenQuery(String query, String indicatorName,long qid, IndicatorXMLData indicatorXMLData ) {
        this.query = query;
        this.indicatorName = indicatorName;
        queryID = qid;
        this.indicatorXMLData = indicatorXMLData;
    }
    public GenQuery(String query, String indicatorName,long qid,
                    long props_id, Timestamp last_executionTime, int totalExecutions) {
        this.query = query;
        this.indicatorName = indicatorName;
        queryID = qid;
        this.genIndicatorProps.setProps_id(props_id);
        this.genIndicatorProps.setTotalExecutions(totalExecutions);
        this.genIndicatorProps.setLast_executionTime(last_executionTime);
    }
    public static AtomicInteger getCount() {
        return count;
    }

    public long getQueryID() {
        return queryID;
    }

    public String getQuery() {
        return query;
    }

    public String getIndicatorName() {
        return indicatorName;
    }

    public GenIndicatorProps getGenIndicatorProps() {
        return genIndicatorProps;
    }

    public void setGenIndicatorProps(GenIndicatorProps genIndicatorProps) {
        this.genIndicatorProps = genIndicatorProps;
    }

    public void setGenIndicatorProps(long props_id, Timestamp last_executionTime, int totalExecutions) {
        this.genIndicatorProps.setProps_id(props_id);
        this.genIndicatorProps.setTotalExecutions(totalExecutions);
        this.genIndicatorProps.setLast_executionTime(last_executionTime);
    }

    public IndicatorXMLData getIndicatorXMLData() {
        return indicatorXMLData;
    }

    public void setIndicatorXMLData(IndicatorXMLData indicatorXMLData) {
        this.indicatorXMLData = indicatorXMLData;
    }

    public void setIndicatorXMLData(List<String> source, String action, String platform, String major,
                                    String minor, String type,  List<EntityValues> entityValues,
                                    List<UserSearchSpecifications> userSpecifications,
                                    List<SessionSpecifications> sessionSpecifications,
                                    List<TimeSearchSpecifications> timeSpecifications,
                                    String selectedChartType, String selectedChartEngine) {

        this.indicatorXMLData.setSource(source);
        this.indicatorXMLData.setAction(action);
        this.indicatorXMLData.setPlatform(platform);
        this.indicatorXMLData.setMajor(major);
        this.indicatorXMLData.setMinor(minor);
        this.indicatorXMLData.setType(type);
        this.indicatorXMLData.setEntityValues(entityValues);
        this.indicatorXMLData.setUserSpecifications(userSpecifications);
        this.indicatorXMLData.setSessionSpecifications(sessionSpecifications);
        this.indicatorXMLData.setTimeSpecifications(timeSpecifications);
        this.indicatorXMLData.setSelectedChartEngine(selectedChartEngine);
        this.indicatorXMLData.setSelectedChartType(selectedChartType);

    }
}
