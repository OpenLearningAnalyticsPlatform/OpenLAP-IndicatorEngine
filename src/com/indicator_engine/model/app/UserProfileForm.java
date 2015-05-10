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

import com.indicator_engine.validator.PasswordsEqualConstraint;
import com.indicator_engine.validator.Phone;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Pattern;

/**
 * Created by Tanmaya Mahapatra on 16-03-2015.
 */
@SuppressWarnings({"unused", "unchecked"})
public class UserProfileForm {


    private String uid;
    @NotEmpty(message = "First Name cannot be empty")
    @Pattern(regexp="^[a-zA-Z]+$", message="First Name must be alphabets with no spaces")
    private String fname;
    @NotEmpty(message = "Last Name cannot be empty")
    @Pattern(regexp="^[a-zA-Z]+$", message="Last Name must be alphabets with no spaces")
    private String lname;

    private String dob;
    private String email;

    @Phone(message = "Invalid characters in Phone Number")
    @Pattern(regexp = "[\\s]*[0-9]*[1-9]+",message="Phone Number should have only Positive Natural Numbers")
    private String phonenumber;

    private String address;

    @Pattern(regexp = "[\\s]*[0-9]*[1-9]+",message="ZIP should have only Positive Natural Numbers")
    private String zip;

    @NotEmpty(message = "City cannot be empty")
    @Pattern(regexp="^[a-zA-Z]+$", message="City must be alphabets with no spaces")
    private String city;

    @NotEmpty(message = "State cannot be empty")
    @Pattern(regexp="^[a-zA-Z]+$", message="State must be alphabets with no spaces")
    private String state;

    @NotEmpty(message = "Country cannot be empty")
    @Pattern(regexp="^[a-zA-Z]+$", message="Country must be alphabets with no spaces")
    private String country;

    private String password;
    private String confirmpassword;
    private boolean changepasswd;

    public String getFname() {
        return fname;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmpassword() {
        return confirmpassword;
    }

    public void setConfirmpassword(String confirmpassword) {
        this.confirmpassword = confirmpassword;
    }

    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }

    public boolean isChangepasswd() {
        return changepasswd;
    }

    public void setChangepasswd(boolean changepasswd) {
        this.changepasswd = changepasswd;
    }
}
