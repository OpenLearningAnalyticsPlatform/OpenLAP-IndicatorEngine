

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

package com.indicator_engine.controller;

import com.indicator_engine.dao.UserCredentialsDao;
import com.indicator_engine.datamodel.UserCredentials;
import com.indicator_engine.datamodel.UserProfile;
import com.indicator_engine.email.MailPackage;
import com.indicator_engine.model.app.RegistrationForm;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.ApplicationContext;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.internet.MimeMessage;
import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Tanmaya Mahapatra on 01-03-2015.
 */
@Controller
@SuppressWarnings({"unused", "unchecked"})
public class RegistrationController {
    static Logger log = Logger.getLogger(LoginController.class.getName());
    @Autowired
    private SessionFactory factory;
    @Autowired
    private ApplicationContext appContext;
    @Autowired
    private BCryptPasswordEncoder encoder;
    @Autowired
    JavaMailSender mailSender;

    /**
     * Register Property Editors
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        sdf.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
        // You can register other Custom Editors with the WebDataBinder,
        // like CustomNumberEditor for Integers and Longs, or StringTrimmerEditor for Strings
    }

    @RequestMapping(value="/register",method = RequestMethod.GET)
    public String getRegister(Map<String, Object> model){

        RegistrationForm RegisterForm = new RegistrationForm();
        model.put("RegisterForm", RegisterForm);
        return "app/register";
    }

    @RequestMapping(value="/register",method = RequestMethod.POST)
    public String processRegistrationForm(@Valid @ModelAttribute("RegisterForm") RegistrationForm RegisterForm, BindingResult bindingResult, Map<String, Object> model){
        if(bindingResult.hasErrors()) {
            return "app/register";
        }
        UserCredentialsDao userDetailsBean = (UserCredentialsDao) appContext.getBean("userDetails");
        MimeMessage message = mailSender.createMimeMessage();
        String errorMsg;
        String uname = RegisterForm.getUserName();
        String password = RegisterForm.getPassword();
        String hashedPassword = encoder.encode(password);
        Date dob = RegisterForm.getDob();
        java.sql.Date sqlStartDate = null;
        sqlStartDate = new java.sql.Date(dob.getTime());
        String email = RegisterForm.getEmail();
        // Error Handling Not done Yet. Synchronize Java Script checks + Server Side checks. Eliminate redundant checks
        if(uname == null || email == null || password == null  )
            errorMsg = "Error : Please Check your " ;
        UserCredentials uc = new UserCredentials(uname,hashedPassword,
                new UserProfile(" "," ",sqlStartDate,
                        0, " "," "," ",0," ",email));
        userDetailsBean.add(uc);
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("goalla@rwth-aachen.de");
            helper.setTo(uc.getUp().getEmailid());
            helper.setSubject("One Time Password for Verification");

            /*Map<String, Object> modelMsg = new HashMap<String, Object>();
            model.put("UserName", uc.getUname());
            model.put("EmailID", uc.getUp().getEmailid());
            model.put("DOB", uc.getUp().getDob());
            model.put("OTP", uc.getOtp());
            String emailText = VelocityEngineUtils.mergeTemplateIntoString(
                    velocityEngine, "registrationEmailTemplate.vm", "UTF-8", modelMsg); */

            helper.setText("OTP For Verification :  "+ uc.getOtp());
            /*FileSystemResource couponImage =
                   new FileSystemResource("logo.png");
            helper.addAttachment("logo.png", logoImage);
            ClassPathResource image = new ClassPathResource("rwth.png");
            helper.addInline("UniLogo", image); */
        } catch (javax.mail.MessagingException ex ){}
        mailSender.send(message);
        model.put("msg", uc.getUid());
        return "app/registration_success";
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

        return new ModelAndView("app/activate");
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
        log.info("Selected User List " +selectedUserList);
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
