package com.indicator_engine.learningcontextdata;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Tanmaya Mahapatra on 22-02-2015.
 */
public class Event {
    private String action;
    private String type;
    private int timestamp;
    private String session = "";
    private List<Entity<?>> entities = new LinkedList<Entity<?>>();

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public List<Entity<?>> getEntities() {
        return entities;
    }

    public void addEntity(Entity<?> entity) {
        this.entities.add(entity);
    }

    public Event(String action, String type, int timestamp) {
        super();
        this.action = action;
        this.type = type;
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Event [action=" + action + ", type=" + type + ", timestamp="
                + timestamp + ", session=" + session + ", entities="
                + entities.toString() + "]";
    }

}