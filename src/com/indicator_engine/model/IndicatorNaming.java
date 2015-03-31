package com.indicator_engine.model;

import java.io.Serializable;

/**
 * Created by Tanmaya Mahapatra on 31-03-2015.
 */
public class IndicatorNaming implements Serializable{
    private String indicatorName;

    public String getIndicatorName() {
        return indicatorName;
    }

    public void setIndicatorName(String indicatorName) {
        this.indicatorName = indicatorName;
    }
}
