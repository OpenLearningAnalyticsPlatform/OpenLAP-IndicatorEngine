package com.goalla.learningcontextdata;

/**
 * Created by Tanmaya Mahapatra on 22-02-2015.
 */
public class Entity<T> {
    private String key;
    private T value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public Entity(String key, T value) {
        super();
        this.key = key;
        this.value = value;
    }

    @Override
    public String toString() {
        return "Entity [key=" + key + ", value=" + value + "]";
    }

}