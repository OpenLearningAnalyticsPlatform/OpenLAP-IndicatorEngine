package com.indicator_engine.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tanmaya Mahapatra on 24-04-2015.
 */
public class EntityValues implements Serializable {
    private String eValues;
    private String type;
    public EntityValues(){}
    public EntityValues(String eValues){
        this.eValues=eValues;
    }

    public String geteValues() {
        return eValues;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void seteValues(String eValues) {
        this.eValues = eValues;
    }
}