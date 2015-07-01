package com.indicator_engine.model.indicator_system.Number;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tanmaya Mahapatra on 26-04-2015.
 */
@SuppressWarnings({"unused", "unchecked"})
public class SessionSpecifications implements Serializable,Cloneable {
    private String session;
    private String type;

    public SessionSpecifications(){}
    public SessionSpecifications(String type, String session) {
        this.type = type;
        this.session = session;
    }

    @Override
    public SessionSpecifications clone() {
        SessionSpecifications clone = null;
        try{
            clone = (SessionSpecifications) super.clone();

        }catch(CloneNotSupportedException e){
            throw new RuntimeException(e); // won't happen
        }
        return clone;

    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
