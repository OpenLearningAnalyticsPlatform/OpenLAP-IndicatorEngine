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

package com.indicator_engine.model.indicator_system.Number;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tanmaya Mahapatra on 24-04-2015.
 */
@SuppressWarnings({"unused", "unchecked"})
public class EntityValues implements Serializable,Cloneable {
    private String key;
    private String eValues;
//    private String type;
    public EntityValues(){}
//    public EntityValues(String key,String eValues, String type){
    public EntityValues(String key,String eValues){
        this.eValues = eValues;
//        this.type = type;
        this.key = key;
    }
    @Override
    public EntityValues clone() {
        EntityValues clone = null;
        try{
            clone = (EntityValues) super.clone();

        }catch(CloneNotSupportedException e){
            throw new RuntimeException(e); // won't happen
        }
        return clone;

    }

    public EntityValues(String eValues){
        this.eValues=eValues;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String geteValues() {
        return eValues;
    }

    public void seteValues(String eValues) {
        this.eValues = eValues;
    }

//    public String getType() {
//        return type;
//    }
//
//    public void setType(String type) {
//        this.type = type;
//    }
}