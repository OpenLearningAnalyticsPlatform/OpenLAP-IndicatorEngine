package com.indicator_engine.model.admin;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Tanmaya Mahapatra on 23-03-2015.
 */
@SuppressWarnings({"unused", "unchecked"})
public class AdminAddCategoryDataForm {


    List<String> type =  new ArrayList<>();
    List<String> major = new ArrayList<>();
    private String minor;
    private String selectedType;
    private String selectedMajor;

    public AdminAddCategoryDataForm() {
        type.add("PRIVATE");
        type.add("ACADEMIC");
        major.add("ENVIRONMENTAL");
        major.add("BIOLOGICAL");
        major.add("ACTIVITY");
    }

    public List<String> getType() {
        return type;
    }

    public void setType(List<String> type) {
        this.type = type;
    }

    public List<String> getMajor() {
        return major;
    }

    public void setMajor(List<String> major) {
        this.major = major;
    }

    public String getMinor() {
        return minor;
    }

    public void setMinor(String minor) {
        this.minor = minor;
    }

    public String getSelectedType() {
        return selectedType;
    }

    public void setSelectedType(String selectedType) {
        this.selectedType = selectedType;
    }

    public String getSelectedMajor() {
        return selectedMajor;
    }

    public void setSelectedMajor(String selectedMajor) {
        this.selectedMajor = selectedMajor;
    }
}
