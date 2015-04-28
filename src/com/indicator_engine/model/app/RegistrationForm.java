/*
 *
 *  * Copyright (C) 2015  Tanmaya Mahapatra
 *  *
 *  * This program is free software; you can redistribute it and/or
 *  * modify it under the terms of the GNU General Public License
 *  * as published by the Free Software Foundation; either version 2
 *  * of the License, or (at your option) any later version.
 *  *
 *  * This program is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public License
 *  * along with this program; if not, write to the Free Software
 *  * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *
 */

package com.indicator_engine.model.app;

import com.indicator_engine.validator.NotExistingEmail;
import com.indicator_engine.validator.NotExistingUserName;
import com.indicator_engine.validator.PasswordsEqualConstraint;
import com.indicator_engine.validator.Year;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Created by Tanmaya Mahapatra on 16-03-2015.
 */
@SuppressWarnings({"unused", "unchecked"})
@PasswordsEqualConstraint(message = "Passwords are not equal")
@NotExistingUserName(message = "User Name already Exists")
@NotExistingEmail(message = "Email ID already Exists")
public class RegistrationForm {

    @Size(min=3, max=20, message="Username must be between 3 and 20 characters")
    @Pattern(regexp="^[a-zA-Z0-9]+$", message="Username must be alphanumeric with no spaces")
    private String userName;


    @Size(min=6, max=20,message="The password must be at least 6 characters long.")
    private String password;


    @Pattern(regexp="[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,4}",
            message="Invalid email address.")
    @NotEmpty(message = "Email Address cannot be empty")
    private String email;



    @DateTimeFormat(pattern="dd-MM-yyyy")
    @NotNull(message = "Date of Birth cannot be Null")
    @Past(message = "Date of Birth Cannot be a Future Date.")
    private Date dob;


    @Size(min=6, max=20,message="The confirm password must be at least 6 characters long.")
    private String confirmpassword;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getConfirmpassword() {
        return confirmpassword;
    }

    public void setConfirmpassword(String confirmpassword) {
        this.confirmpassword = confirmpassword;
    }
}
