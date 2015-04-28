package com.indicator_engine.model.indicator_system.Number;

import java.io.Serializable;

/**
 * Created by Tanmaya Mahapatra on 25-04-2015.
 */
@SuppressWarnings({"unused", "unchecked"})
public class OtherSearchSpecifications implements Serializable {
    private String timestamp;
    private String session;

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }
}
