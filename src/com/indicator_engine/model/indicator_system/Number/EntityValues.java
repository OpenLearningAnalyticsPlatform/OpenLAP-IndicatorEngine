package com.indicator_engine.model.indicator_system.Number;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tanmaya Mahapatra on 24-04-2015.
 */
@SuppressWarnings({"unused", "unchecked"})
public class EntityValues implements Serializable {
    private String key;
    private String eValues;
    private String type;
    public EntityValues(){}
    public EntityValues(String key, String type, String eValues){
        this.eValues = eValues;
        this.type = type;
        this.key = key;
    }

    public EntityValues(String eValues){
        this.eValues=eValues;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String geteValues() {
        return eValues;
    }

    public void seteValues(String eValues) {
        this.eValues = eValues;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}