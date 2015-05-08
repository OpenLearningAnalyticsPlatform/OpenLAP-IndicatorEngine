package com.indicator_engine.model.indicator_system.Number;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by Tanmaya Mahapatra on 08-05-2015.
 */
public class GenIndicatorProps implements Serializable {

    private long props_id;
    private Timestamp last_executionTime;
    private int totalExecutions;

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
}
