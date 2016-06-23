package com.indicator_engine.model.indicator_system.Number;

import DataSet.OLAPPortConfiguration;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by Tanmaya Mahapatra on 08-05-2015.
 */
public class GenIndicatorProps implements Serializable {

    private long props_id;
    private Timestamp last_executionTime;
    private int totalExecutions;

    private String chartType;
    private String chartEngine;
    private String userName;
    private boolean isComposite;

    public GenIndicatorProps(){}
    public GenIndicatorProps(long props_id, Timestamp last_executionTime, int totalExecutions,
                             String chartType,String chartEngine, String userName, boolean isComposite){
        this.props_id = props_id;
        this.last_executionTime = last_executionTime;
        this.totalExecutions = totalExecutions;
        this.chartEngine = chartEngine;
        this.chartType = chartType;
        this.userName = userName;
        this.isComposite = isComposite;
    }

    public long getProps_id() {
        return props_id;
    }

    public void setProps_id(long props_id) {
        this.props_id = props_id;
    }

    public Timestamp getLast_executionTime() {
        return last_executionTime;
    }

    public void setLast_executionTime(Timestamp last_executionTime) {
        this.last_executionTime = last_executionTime;
    }

    public int getTotalExecutions() {
        return totalExecutions;
    }

    public void setTotalExecutions(int totalExecutions) {
        this.totalExecutions = totalExecutions;
    }

    public String getChartType() {
        return chartType;
    }

    public void setChartType(String chartType) {
        this.chartType = chartType;
    }

    public String getChartEngine() { return chartEngine; }

    public void setChartEngine(String chartEngine) {
        this.chartEngine = chartEngine;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isComposite() { return isComposite; }

    public void setComposite(boolean isComposite) {
        this.isComposite = isComposite;
    }
}
