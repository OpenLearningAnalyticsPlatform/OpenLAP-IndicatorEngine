package com.indicator_engine.model.indicator_system.Number;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tanmaya Mahapatra on 26-04-2015.
 */
@Component
@Scope("session")
@SuppressWarnings({"unused", "unchecked"})
public class TimeSearchSpecifications  implements Serializable,Cloneable{
    private String type;
    //private List<String> timestamp = new ArrayList<>();
    private String timestamp;

    public TimeSearchSpecifications(){}
    public TimeSearchSpecifications(String type, String timestamp ){
        this.type = type;
        this.timestamp = timestamp;
    }

    @Override
    public TimeSearchSpecifications clone() {
        TimeSearchSpecifications clone = null;
        try{
            clone = (TimeSearchSpecifications) super.clone();

        }catch(CloneNotSupportedException e){
            throw new RuntimeException(e); // won't happen
        }
        return clone;

    }

    public String getType() { return type; }

    public void setType(String type) { this.type = type; }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
