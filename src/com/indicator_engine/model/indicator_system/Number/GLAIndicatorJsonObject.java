package com.indicator_engine.model.indicator_system.Number;

import com.google.gson.annotations.Expose;
import com.indicator_engine.datamodel.GLAIndicator;
import de.rwthaachen.openlap.analyticsengine.core.dtos.response.IndicatorResponse;

import java.util.List;

/**
 * Created by Tanmaya Mahapatra on 08-05-2015.
 */
@SuppressWarnings({"unused", "unchecked"})
public class GLAIndicatorJsonObject {
    @Expose
    int iTotalRecords;
    @Expose
    int iTotalDisplayRecords;

    @Expose
    String sEcho;

    @Expose
    String sColumns;
    @Expose
    List<IndicatorResponse> aaData;

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

    public List<IndicatorResponse> getAaData() {
        return aaData;
    }

    public void setAaData(List<IndicatorResponse> aaData) {
        this.aaData = aaData;
    }
}
