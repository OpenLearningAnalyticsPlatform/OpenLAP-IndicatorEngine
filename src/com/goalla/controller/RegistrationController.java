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

package com.goalla.controller;

import com.goalla.dao.UserCredentialsDao;
import com.goalla.datamodel.UserCredentials;
import com.goalla.datamodel.UserProfile;
import com.goalla.email.MailPackage;
import com.goalla.model.LoginForm;
import com.goalla.model.RegistrationForm;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.util.List;
import java.util.Map;

/**
 * Created by Tanmaya Mahapatra on 01-03-2015.
 */
@Controller
public class RegistrationController {
    static Logger log = Logger.getLogger(LoginController.class.getName());
    @Autowired
    private SessionFactory factory;
    @Autowired
    private ApplicationContext appContext;
    @Autowired
    private BCryptPasswordEncoder encoder;
    @RequestMapping(value="/register",method = RequestMethod.GET)
    public String getRegister(Map<String, Object> model){

        RegistrationForm RegisterForm = new RegistrationForm();
        model.put("RegisterForm", RegisterForm);
        return "register";
    }

    @RequestMapping(value="/register",method = RequestMethod.POST)
    public String processRegistrationForm(@ModelAttribute("RegisterForm") RegistrationForm RegisterForm,Map<String, Object> model){
        UserCredentialsDao userDetailsBean = (UserCredentialsDao) appContext.getBean("userDetails");
        MailPackage mailBean = (MailPackage) appContext.getBean("mail");
        String errorMsg;
        String uname = RegisterForm.getUserName();
        String password = RegisterForm.getPassword();
        String hashedPassword = encoder.encode(password);
        String dob = RegisterForm.getDob();
        java.sql.Date sqlStartDate = null;
        try {
            SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
            java.util.Date date = sdf1.parse(dob);
            sqlStartDate = new java.sql.Date(date.getTime());
        } catch(ParseException e){}

        String email = RegisterForm.getEmail();
        // Error Handling Not done Yet. Synchronize Java Script checks + Server Side checks. Eliminate redundant checks
        if(uname == null || email == null || password == null  )
            errorMsg = "Error : Please Check your " ;
        UserCredentials uc = new UserCredentials(uname,hashedPassword,
                new UserProfile(" "," ",sqlStartDate,
                        0, " "," "," ",0," ",email));
        userDetailsBean.add(uc);
        mailBean.sendMail(uc.getUp().getEmailid(), "OTP", uc.getOtp(), false, null);
        model.put("msg", uc.getUid());
        return "registration_success";
    }

    @RequestMapping(value = "/checkusername", method = RequestMethod.GET)
    public @ResponseBody
    String processAJAXRequest_checkUserName(
            @RequestParam(value="username", required = false) String username, Model model) {

        UserCredentialsDao userDetailsBean = (UserCredentialsDao) appContext.getBean("userDetails");
        String status ="false";
        List<UserCredentials> selectedUserList = userDetailsBean.searchByUserName(username);
        for (UserCredentials eachuser : selectedUserList) {
            if (username.equals(eachuser.getUname())) {
                status = "true";
                break;
            }
        }
        return status;

    }
    @RequestMapping(value = "/checkemail", method = RequestMethod.GET)
    public @ResponseBody
    String processAJAXRequest_checkEmail(
            @RequestParam(value="email", required = false) String email, Model model) {

        UserCredentialsDao userDetailsBean = (UserCredentialsDao) appContext.getBean("userDetails");
        String status ="false";
        List<UserCredentials> selectedUserList = userDetailsBean.displayall();
        for (UserCredentials eachuser : selectedUserList) {
            if (email.equals(eachuser.getUp().getEmailid())) {
                status = "true";
                break;
            }
        }
        return status;

    }
    @RequestMapping(value="/activate",method = RequestMethod.GET)
    public ModelAndView getLogin(){

        return new ModelAndView("activate");
    }
    @Transactional
    @RequestMapping(value = "/validateotp", method = RequestMethod.GET)
    public @ResponseBody
    String processAJAXRequest_validateOTP(
            @RequestParam(value="otp", required = false) String otp, @RequestParam(value="username", required = false) String username, Model model) {

        UserCredentialsDao userDetailsBean = (UserCredentialsDao) appContext.getBean("userDetails");
        log.info("Executing validate OTp");
        log.info(username);
        log.info(otp);
        List<UserCredentials> selectedUserList = userDetailsBean.searchByUserName(username);
        String status = "false";
        log.info(selectedUserList);
        for (UserCredentials eachuser : selectedUserList) {
            if (username.equals(eachuser.getUname())) {
                if (otp.equals(eachuser.getOtp())) {
                    eachuser.setActivation_status(true);
                    userDetailsBean.update(eachuser);
                    status = "true";
                }
            }
        }
        return status;
    }

}
