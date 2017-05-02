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

package com.indicator_engine.datamodel;

import javax.persistence.*;

/**
 * Created by Tanmaya Mahapatra on 26-02-2015.
 */

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "user_profile")
public final class UserProfile implements Serializable {
    @Id
    @Column(name = "profile_id")
    @GeneratedValue
    private long id;
    @Column(name = "fname")
    private String fname;
    @Column(name = "lname")
    private String lname;
    @Temporal(TemporalType.DATE)
    @Column(name = "dob", nullable = false)
    private Date dob;
    private long phone;
    private String str_address;
    private String state;
    private String country;
    private int zip;
    private String city;
    @Column(name = "emailid", length =320, nullable = false)
    private String emailid;

    public UserProfile(){}

    public UserProfile(String fname, String lname, Date dob, long phone, String str_address, String state, String country, int zip, String city, String emailid) {
        this.fname = fname;
        this.lname = lname;
        this.dob = dob;
        this.phone = phone;
        this.str_address = str_address;
        this.state = state;
        this.country = country;
        this.zip = zip;
        this.city = city;
        this.emailid = emailid;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public String getStr_address() {
        return str_address;
    }

    public void setStr_address(String str_address) {
        this.str_address = str_address;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getZip() {
        return zip;
    }

    public void setZip(int zip) {
        this.zip = zip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

}
