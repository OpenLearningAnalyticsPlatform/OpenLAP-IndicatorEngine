package com.indicator_engine.model.indicator_system;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tanmaya Mahapatra on 09-05-2015.
 */
public class IndicatorDeletionForm {
    private String indName;


    private List<String> deletionList = new ArrayList<String>();
    @NotNull
    private List<String> selectedList = new ArrayList<String>();

    public List<String> getDeletionList() {
        return deletionList;
    }

    public void setDeletionList(List<String> deletionList) {
        this.deletionList = deletionList;
    }

    public List<String> getSelectedList() {
        return selectedList;
    }

    public String getIndName() {
        return indName;
    }

    public void setIndName(String indName) {
        this.indName = indName;
    }

    public void setSelectedList(List<String> selectedList) {
        this.selectedList = selectedList;
    }
}
