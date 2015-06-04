package com.indicator_engine.model.indicator_system.Number;

import java.io.Serializable;
import java.sql.Timestamp;
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

    public GenQuery(String query, String indicatorName,long qid ) {
        this.query = query;
        this.indicatorName = indicatorName;
        queryID = qid;
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
}
