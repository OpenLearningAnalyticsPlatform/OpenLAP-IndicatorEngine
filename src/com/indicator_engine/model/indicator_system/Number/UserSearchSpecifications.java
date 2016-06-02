package com.indicator_engine.model.indicator_system.Number;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.comparator.BooleanComparator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tanmaya Mahapatra on 25-04-2015.
 */
@Component
@Scope("session")
@SuppressWarnings({"unused", "unchecked"})
public class UserSearchSpecifications implements Serializable,Cloneable {
    private String key;
    private String value;
    private String userSearchType;
    private String searchPattern;
    private String userSearch ;

    public UserSearchSpecifications() {}
    public UserSearchSpecifications(String userSearchType, String userSearch, String searchPattern){
        this.userSearchType = userSearchType;
        this.userSearch = userSearch;
        this.searchPattern =searchPattern;
    }

    public UserSearchSpecifications(String key, String value, String userSearchType, String userSearch, String searchPattern){
        this.key = key;
        this.value = value;
        this.userSearchType = userSearchType;
        this.userSearch = userSearch;
        this.searchPattern =searchPattern;
    }

    @Override
    public UserSearchSpecifications clone() {
        UserSearchSpecifications clone = null;
        try{
            clone = (UserSearchSpecifications) super.clone();

        }catch(CloneNotSupportedException e){
            throw new RuntimeException(e); // won't happen
        }
        return clone;

    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
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
