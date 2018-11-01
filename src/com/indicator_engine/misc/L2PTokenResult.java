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

package com.indicator_engine.misc;

import java.util.List;

/**
 * Created by arham on 8/25/2017.
 */
public class L2PTokenResult {

    public String UserId;
    public String CourseId;
    public String UserRoles;
    public String System;
    public Boolean Success;
    public List<KeyValuePair> Details;

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getCourseId() {
        return CourseId;
    }

    public void setCourseId(String courseId) {
        CourseId = courseId;
    }

    public String getUserRoles() {
        return UserRoles;
    }

    public void setUserRoles(String userRoles) {
        UserRoles = userRoles;
    }

    public String getSystem() {
        return System;
    }

    public void setSystem(String system) {
        System = system;
    }

    public Boolean getSuccess() {
        return Success;
    }

    public void setSuccess(Boolean success) {
        Success = success;
    }

    public List<KeyValuePair> getDetails() {
        return Details;
    }

    public void setDetails(List<KeyValuePair> details) {
        Details = details;
    }

    public String getDetailsValue(String key) {
        for (KeyValuePair pair : Details) {
            if(pair.getKey().equals(key ))
                return pair.getValue();
        }
        return "";
    }
}
