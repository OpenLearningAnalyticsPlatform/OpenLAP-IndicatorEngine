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

package com.indicator_engine.controller;

import com.indicator_engine.dao.UserCredentialsDao;
import com.indicator_engine.datamodel.UserCredentials;
import com.indicator_engine.datamodel.UserProfile;
import com.indicator_engine.model.UserProfileForm;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;

/**
 * Created by Tanmaya Mahapatra on 16-03-2015.
 */
@Controller
@RequestMapping(value="/home")
public class GAToolKitDashBoardController {
    static Logger log = Logger.getLogger(LoginController.class.getName());
    @Autowired
    private SessionFactory factory;
    @Autowired
    private ApplicationContext appContext;

    @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public ModelAndView getDashboard() {
        return new ModelAndView("app/home");
    }

    @RequestMapping(value = "/user_profile", method = RequestMethod.GET)
    public String getUserProfile(HttpSession session, Map<String, Object> model) {
        UserProfileForm UprofileForm = new UserProfileForm();
        UserProfile up;
        UserCredentialsDao userDetailsBean = (UserCredentialsDao) appContext.getBean("userDetails");
        String userName = (String) session.getAttribute("userName");
        List<UserCredentials> selectedUserList = userDetailsBean.searchByUserName(userName);
        for (UserCredentials eachuser : selectedUserList) {
            up = eachuser.getUp();
            UprofileForm.setFname(up.getFname());
            UprofileForm.setLname(up.getLname());
            UprofileForm.setDob(String.valueOf(up.getDob()));
            UprofileForm.setEmail(up.getEmailid());
            UprofileForm.setState(up.getState());
            UprofileForm.setPhonenumber(String.valueOf(up.getPhone()));
            UprofileForm.setAddress(up.getStr_address());
            UprofileForm.setZip(String.valueOf(up.getZip()));
            UprofileForm.setCity(up.getCity());
            UprofileForm.setCountry(up.getCountry());
            UprofileForm.setUid(String.valueOf(up.getId()));
            UprofileForm.setPassword("*******");
            UprofileForm.setConfirmpassword("*******");
            UprofileForm.setChangepasswd(false);
        }
        model.put("UprofileForm", UprofileForm);
        return "app/user_profile";
    }

    @RequestMapping(value = "/update_user_profile", method = RequestMethod.POST)
    public String updateUserProfile(HttpSession session, @ModelAttribute("UprofileForm") UserProfileForm UprofileForm, Map<String, Object> model) {

        UserProfile up;
        UserCredentialsDao userDetailsBean = (UserCredentialsDao) appContext.getBean("userDetails");
        String userName = (String) session.getAttribute("userName");
        List<UserCredentials> selectedUserList = userDetailsBean.searchByUserName(userName);
        for (UserCredentials eachuser : selectedUserList) {
            if (!eachuser.getUp().getFname().equals(UprofileForm.getFname()))
                eachuser.getUp().setFname(UprofileForm.getFname());
            if (!eachuser.getUp().getLname().equals(UprofileForm.getLname()))
                eachuser.getUp().setLname(UprofileForm.getLname());
            if (!eachuser.getUp().getCity().equals(UprofileForm.getCity()))
                eachuser.getUp().setCity(UprofileForm.getCity());
            if (!eachuser.getUp().getCountry().equals(UprofileForm.getCountry()))
                eachuser.getUp().setCountry(UprofileForm.getCountry());
            if (!(eachuser.getUp().getZip() == Integer.parseInt(UprofileForm.getZip())))
                eachuser.getUp().setZip(Integer.parseInt(UprofileForm.getZip()));
            if (!(eachuser.getUp().getPhone() == Long.parseLong(UprofileForm.getPhonenumber())))
                eachuser.getUp().setPhone(Long.parseLong(UprofileForm.getPhonenumber()));
            if (!eachuser.getUp().getStr_address().equals(UprofileForm.getAddress()))
                eachuser.getUp().setStr_address(UprofileForm.getAddress());
            if (!eachuser.getUp().getState().equals(UprofileForm.getState()))
                eachuser.getUp().setState(UprofileForm.getState());
            if (UprofileForm.isChangepasswd()) {
                if (UprofileForm.getPassword().equals(UprofileForm.getConfirmpassword())) {
                    if (!eachuser.getPassword().equals(UprofileForm.getConfirmpassword()))
                        eachuser.setPassword(UprofileForm.getConfirmpassword());
                }
            }
            userDetailsBean.update(eachuser);
        }
        for (UserCredentials eachuser : selectedUserList) {
            up = eachuser.getUp();
            UprofileForm.setFname(up.getFname());
            UprofileForm.setLname(up.getLname());
            UprofileForm.setDob(String.valueOf(up.getDob()));
            UprofileForm.setEmail(up.getEmailid());
            UprofileForm.setPhonenumber(String.valueOf(up.getPhone()));
            UprofileForm.setAddress(up.getStr_address());
            UprofileForm.setZip(String.valueOf(up.getZip()));
            UprofileForm.setCity(up.getCity());
            UprofileForm.setState(up.getState());
            UprofileForm.setCountry(up.getCountry());
            UprofileForm.setUid(String.valueOf(up.getId()));
            UprofileForm.setPassword("*******");
            UprofileForm.setConfirmpassword("*******");
            UprofileForm.setChangepasswd(false);
        }
        model.put("UprofileForm", UprofileForm);
        return "app/user_profile";
    }

}
