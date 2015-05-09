package com.indicator_engine.model.app;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tanmaya Mahapatra on 09-05-2015.
 */
public class SearchIndicatorForm {

    @Pattern(regexp="^[a-zA-Z0-9]+$", message="searchField must be alphanumeric with no spaces")
    private String searchField;
    List<String> searchType =  new ArrayList<>();
    @NotNull(message = "selectedSearchType of Birth cannot be Null")
    private String selectedSearchType;
    private List<String> searchResults = new ArrayList<>();
    private String selectedIndicatorName;

    public String getSelectedIndicatorName() {
        return selectedIndicatorName;
    }

    public void setSelectedIndicatorName(String selectedIndicatorName) {
        this.selectedIndicatorName = selectedIndicatorName;
    }

    public SearchIndicatorForm(){
        searchType.add("ID");
        searchType.add("Indicator Name");
    }

    public String getSelectedSearchType() {
        return selectedSearchType;
    }

    public void setSelectedSearchType(String selectedSearchType) {
        this.selectedSearchType = selectedSearchType;
    }

    public String getSearchField() {
        return searchField;
    }

    public void setSearchField(String searchField) {
        this.searchField = searchField;
    }

    public List<String> getSearchType() {
        return searchType;
    }

    public void setSearchType(List<String> searchType) {
        this.searchType = searchType;
    }

    public List<String> getSearchResults() {
        return searchResults;
    }

    public void setSearchResults(List<String> searchResults) {
        this.searchResults = searchResults;
    }
}
