package com.indicator_engine.model.indicator_system.Number;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tanmaya Mahapatra on 26-04-2015.
 */
public class TimeSearchSpecifications  implements Serializable{
    private String type;
    private List<String> timestamp = new ArrayList<>();

    public TimeSearchSpecifications(){}
    public TimeSearchSpecifications(String type, List<String> timestamp ){
        this.type = type;
        this.timestamp = timestamp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(List<String> timestamp) {
        this.timestamp = timestamp;
    }
}
