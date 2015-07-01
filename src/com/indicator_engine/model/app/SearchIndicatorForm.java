/*
 * Open Platform Learning Analytics : Indicator Engine
 * Copyright (C) 2015  Learning Technologies Group, RWTH
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

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
    private String selectedQuestionName;

    public String getSelectedQuestionName() {
        return selectedQuestionName;
    }

    public void setSelectedQuestionName(String selectedQuestionName) {
        this.selectedQuestionName = selectedQuestionName;
    }

    public SearchIndicatorForm(){
        searchType.add("ID");
        searchType.add("Question Name");
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
