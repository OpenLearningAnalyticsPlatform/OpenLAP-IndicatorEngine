package com.indicator_engine.model.indicator_system.Number;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tanmaya Mahapatra on 26-04-2015.
 */
@SuppressWarnings({"unused", "unchecked"})
public class SessionSpecifications implements Serializable {
    private List<String> session = new ArrayList<>();
    private String type;

    public SessionSpecifications(){}
    public SessionSpecifications(String type, List<String> session) {
        this.type = type;
        this.session = session;
    }

    public List<String> getSession() {
        return session;
    }

    public void setSession(List<String> session) {
        this.session = session;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
