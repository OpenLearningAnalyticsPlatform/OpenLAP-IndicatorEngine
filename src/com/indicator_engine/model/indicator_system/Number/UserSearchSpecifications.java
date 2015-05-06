package com.indicator_engine.model.indicator_system.Number;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tanmaya Mahapatra on 25-04-2015.
 */
@SuppressWarnings({"unused", "unchecked"})
public class UserSearchSpecifications implements Serializable {
    private String userSearchType;
    private String searchPattern;
    private String userSearch ;

    public UserSearchSpecifications() {}
    public UserSearchSpecifications(String userSearchType, String userSearch, String searchPattern){
        this.userSearchType = userSearchType;
        this.userSearch = userSearch;
        this.searchPattern =searchPattern;
    }

    public String getUserSearchType() {
        return userSearchType;
    }

    public void setUserSearchType(String userSearchType) {
        this.userSearchType = userSearchType;
    }

    public String getUserSearch() {
        return userSearch;
    }

    public void setUserSearch(String userSearch) {
        this.userSearch = userSearch;
    }

    public String getSearchPattern() {
        return searchPattern;
    }

    public void setSearchPattern(String searchPattern) {
        this.searchPattern = searchPattern;
    }
}
