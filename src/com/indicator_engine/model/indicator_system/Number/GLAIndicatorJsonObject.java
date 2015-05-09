package com.indicator_engine.model.indicator_system.Number;

import com.indicator_engine.datamodel.GLAIndicator;

import java.util.List;

/**
 * Created by Tanmaya Mahapatra on 08-05-2015.
 */
@SuppressWarnings({"unused", "unchecked"})
public class GLAIndicatorJsonObject {
    int iTotalRecords;

    int iTotalDisplayRecords;

    String sEcho;

    String sColumns;
    List<GLAIndicator> aaData;

    public int getiTotalRecords() {
        return iTotalRecords;
    }

    public void setiTotalRecords(int iTotalRecords) {
        this.iTotalRecords = iTotalRecords;
    }

    public int getiTotalDisplayRecords() {
        return iTotalDisplayRecords;
    }

    public void setiTotalDisplayRecords(int iTotalDisplayRecords) {
        this.iTotalDisplayRecords = iTotalDisplayRecords;
    }

    public String getsEcho() {
        return sEcho;
    }

    public void setsEcho(String sEcho) {
        this.sEcho = sEcho;
    }

    public String getsColumns() {
        return sColumns;
    }

    public void setsColumns(String sColumns) {
        this.sColumns = sColumns;
    }

    public List<GLAIndicator> getAaData() {
        return aaData;
    }

    public void setAaData(List<GLAIndicator> aaData) {
        this.aaData = aaData;
    }
}
