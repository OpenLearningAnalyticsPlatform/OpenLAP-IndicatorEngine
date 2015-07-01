
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

import com.indicator_engine.dao.SecurityRoleEntityDao;
import com.indicator_engine.dao.UserCredentialsDao;
import com.indicator_engine.datamodel.SecurityRoleEntity;
import com.indicator_engine.datamodel.UserCredentials;
import com.indicator_engine.model.app.LoginForm;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;


@Controller
@Scope("session")
@SessionAttributes({"loggedIn", "userName", "sid", "activationStatus","role", "admin_access"})
@SuppressWarnings({"unused", "unchecked"})
public class LoginController {
    static Logger log = Logger.getLogger(LoginController.class.getName());
    @Autowired
    private SessionFactory factory;
    @Autowired
    private ApplicationContext appContext;
    @Autowired
    private BCryptPasswordEncoder encoder;

    @RequestMapping(value = {"/", "/welcome**"}, method = RequestMethod.GET)
    public ModelAndView LoginController() {

        ModelAndView model = new ModelAndView();
        model.setViewName("app/welcome");
        return model;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String getLogin(Map<String, Object> model) {
        LoginForm loginForm = new LoginForm();
        model.put("loginForm", loginForm);
        return "app/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView processLoginForm(@Valid @ModelAttribute("loginForm") LoginForm loginForm, BindingResult bindingResult, HttpSession session) {

        ModelAndView model;
        if(bindingResult.hasErrors()) {
            return new ModelAndView("app/login");
        }
        boolean authid = false;
        String user_role = "INVALID";
        String admin_role = "NO";
        boolean activation_status = false;
        String sessionUserName = null;
        String loginMsg = null;
        UserCredentialsDao userDetailsBean = (UserCredentialsDao) appContext.getBean("userDetails");
        SecurityRoleEntityDao securityRoleEntityBean = (SecurityRoleEntityDao) appContext.getBean("userRoleDetails");
        String username = loginForm.getUserName();
        String password = loginForm.getPassword();
        List<UserCredentials> selectedUserList = userDetailsBean.searchByUserName(username);
        for (UserCredentials eachuser : selectedUserList) {
            log.info("---------------------------------------------------");
            log.info(eachuser.getUname());
            log.info(eachuser.getPassword());
            if (username.equals(eachuser.getUname())) {
                if (encoder.matches(password, eachuser.getPassword())) {
                    authid = true;
                    sessionUserName = username;
                    if (eachuser.getActivation_status()) {
                        activation_status = true;
                        List<SecurityRoleEntity> roleEntity = securityRoleEntityBean.searchRolesByID(eachuser.getUid());
                        for (SecurityRoleEntity roles : roleEntity)
                        {
                            if (roles.getRole().equals("ROLE_ADMIN"))
                                admin_role = "YES";
                            if( roles.getRole().equals("ROLE_USER"))
                                user_role = "ROLE_USER";
                        }
                        break;
                    }
                }
            }
        }
        log.info("---------------------------------------------------");
        log.info("Debug Login : \n");
        log.info("Debug Login : Auth ID : \t" + authid);
        log.info("Debug Login : Activation Status : \t" + activation_status);
        log.info("Debug Login : Session User Name : \t" + sessionUserName);
        log.info("Debug Login : Role : \t" + user_role);
        if (authid && activation_status) {
            String sid = session.getId();
            model = new ModelAndView("app/home");
            model.addObject("sid", sid);
            model.addObject("loggedIn", "true");
            model.addObject("userName", sessionUserName);
            model.addObject("activationStatus", "true");
            model.addObject("role", user_role);
            model.addObject("admin_access", admin_role);
        }
        else if (authid && !activation_status) {
            String sid = session.getId();
            model = new ModelAndView("app/activate");
            model.addObject("sid", sid);
            model.addObject("loggedIn", "true");
            model.addObject("userName", sessionUserName);
            model.addObject("activationStatus", "false");
            model.addObject("role", user_role);
            model.addObject("admin_access", admin_role);
        }
        else
            model = new ModelAndView("app/login");
        log.info("Debug Login : Selected Model : \t" + model);
        return model;
    }

    @RequestMapping(value = "logoff", method = RequestMethod.GET)
    public ModelAndView getLogOff() {
        return new ModelAndView("app/logoff");
    }

}

