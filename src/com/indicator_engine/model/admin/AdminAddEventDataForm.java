/*
 * Open Learning Analytics Platform (OpenLAP) : Indicator Engine

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

package com.indicator_engine.model.admin;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tanmaya Mahapatra on 23-03-2015.
 */
@SuppressWarnings({"unused", "unchecked"})
public class AdminAddEventDataForm {

    private String session;
    private String selectedAction;
    private String selectedPlatform;
    private String selectedsource;
    private String timestamp;
    private String selectedUser;
    private String selectedCategory;
    private String entity;
    private String value;
    List<String> action =  new ArrayList<>();
    List<String> platform = new ArrayList<>();
    List<String> source = new ArrayList<>();
    List<String> user = new ArrayList<>();
    List<String> category = new ArrayList<>();


    public AdminAddEventDataForm(){
        action.add("START");
        action.add("END");
        action.add("UPDATE");
        platform.add("MOBILE");
        platform.add("STATIONARY");
        platform.add("WEBBASED");
        source.add("27");

    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getSelectedAction() {
        return selectedAction;
    }

    public void setSelectedAction(String selectedAction) {
        this.selectedAction = selectedAction;
    }

    public String getSelectedPlatform() {
        return selectedPlatform;
    }

    public void setSelectedPlatform(String selectedPlatform) {
        this.selectedPlatform = selectedPlatform;
    }

    public String getSelectedsource() {
        return selectedsource;
    }

    public void setSelectedsource(String selectedsource) {
        this.selectedsource = selectedsource;
    }

    public String getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(String selectedUser) {
        this.selectedUser = selectedUser;
    }

    public String getSelectedCategory() {
        return selectedCategory;
    }

    public void setSelectedCategory(String selectedCategory) {
        this.selectedCategory = selectedCategory;
    }

    public List<String> getAction() {
        return action;
    }

    public void setAction(List<String> action) {
        this.action = action;
    }

    public List<String> getPlatform() {
        return platform;
    }

    public void setPlatform(List<String> platform) {
        this.platform = platform;
    }

    public List<String> getSource() {
        return source;
    }

    public void setSource(List<String> source) {
        this.source = source;
    }

    public List<String> getUser() {
        return user;
    }

    public void setUser(List<String> user) {
        this.user = user;
    }

    public List<String> getCategory() {
        return category;
    }

    public void setCategory(List<String> category) {
        this.category = category;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
