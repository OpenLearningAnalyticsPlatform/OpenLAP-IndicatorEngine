package com.indicator_engine.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tanmaya Mahapatra on 31-03-2015.
 */
public class SelectNumberParameters implements Serializable {

    List<String> minors =  new ArrayList<>();
    List<String> keys =  new ArrayList<>();
    private String selectedMinor;
    private String selectedKeys;
    List<String> events =  new ArrayList<>();
    private String selectedEvents;
    private int result;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public List<String> getEvents() {
        return events;
    }

    public void setEvents(List<String> events) {
        this.events = events;
    }

    public String getSelectedEvents() {
        return selectedEvents;
    }

    public void setSelectedEvents(String selectedEvents) {
        this.selectedEvents = selectedEvents;
    }

    public List<String> getKeys() {
        return keys;
    }

    public void setKeys(List<String> keys) {
        this.keys = keys;
    }

    public String getSelectedKeys() {
        return selectedKeys;
    }

    public void setSelectedKeys(String selectedKeys) {
        this.selectedKeys = selectedKeys;
    }

    public List<String> getMinors() {
        return minors;
    }

    public void setMinors(List<String> minors) {
        this.minors = minors;
    }

    public String getSelectedMinor() {
        return selectedMinor;
    }

    public void setSelectedMinor(String selectedMinor) {
        this.selectedMinor = selectedMinor;
    }
}
