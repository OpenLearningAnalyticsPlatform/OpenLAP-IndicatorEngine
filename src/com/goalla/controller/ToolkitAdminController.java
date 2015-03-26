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

import com.goalla.dao.GLACategoryDao;
import com.goalla.dao.GLAEntityDao;
import com.goalla.dao.GLAEventDao;
import com.goalla.dao.GLAUserDao;
import com.goalla.datamodel.GLACategory;
import com.goalla.datamodel.GLAEntity;
import com.goalla.datamodel.GLAEvent;
import com.goalla.datamodel.GLAUser;
import com.goalla.model.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by Tanmaya Mahapatra on 18-03-2015.
 */
@Controller
@RequestMapping(value="/toolkit")
public class ToolkitAdminController {

    @Autowired
    private ApplicationContext appContext;
    static Logger log = Logger.getLogger(ToolkitAdminController.class.getName());


    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public ModelAndView getToolkitAdmin() {
        return new ModelAndView("admin/toolkitadmin");
    }

    @RequestMapping(value = "/add_userdata", method = RequestMethod.GET)
    public String getAddUserData(Map<String, Object> model) {

        AdminAddUserDataForm addUserDataForm = new AdminAddUserDataForm();
        model.put("addUserDataForm", addUserDataForm);
        return "admin/add_user_data";
    }

    @RequestMapping(value = "/add_userdata", method = RequestMethod.POST)
    public String processAddUserData(Map<String, Object> model, @ModelAttribute("addUserDataForm") AdminAddUserDataForm addUserDataForm, HttpSession session) {
        GLAUserDao glauserBean = (GLAUserDao) appContext.getBean("glaUser");
        glauserBean.add(new GLAUser(addUserDataForm.getUsername(),addUserDataForm.getPassword(),addUserDataForm.getEmail()));
        addUserDataForm.setUsername(null);
        addUserDataForm.setEmail(null);
        addUserDataForm.setPassword(null);
        model.put("addUserDataForm", addUserDataForm);
        return "admin/add_user_data";
    }

    @RequestMapping(value = "/add_categorydata", method = RequestMethod.GET)
    public String getAddCategoryData(Map<String, Object> model) {

        AdminAddCategoryDataForm addCategoryDataForm = new AdminAddCategoryDataForm();
        model.put("addCategoryDataForm", addCategoryDataForm);
        return "admin/add_categorydata";
    }

    @RequestMapping(value = "/add_categorydata", method = RequestMethod.POST)
    public String processAddCategoryData(Map<String, Object> model, @ModelAttribute("addCategoryDataForm") AdminAddCategoryDataForm addCategoryDataForm) {
        GLACategoryDao glacategoryBean = (GLACategoryDao) appContext.getBean("glaCategory");
        glacategoryBean.add(new GLACategory(addCategoryDataForm.getSelectedMajor(),addCategoryDataForm.getMinor(),addCategoryDataForm.getSelectedType()));
        addCategoryDataForm.setSelectedMajor(null);
        addCategoryDataForm.setSelectedType(null);
        addCategoryDataForm.setMinor(null);
        model.put("addCategoryDataForm", addCategoryDataForm);
        return "admin/add_categorydata";
    }
    @RequestMapping(value = "/add_eventdata", method = RequestMethod.GET)
    public String getAddEventData(Map<String, Object> model) {
        AdminAddEventDataForm addEventDataForm = new AdminAddEventDataForm();
        GLACategoryDao glacategoryBean = (GLACategoryDao) appContext.getBean("glaCategory");
        GLAUserDao glauserBean = (GLAUserDao) appContext.getBean("glaUser");
        addEventDataForm.setUser(glauserBean.selectAllUsers());
        addEventDataForm.setCategory(glacategoryBean.selectAllMinors());
        model.put("addEventDataForm",addEventDataForm);
        return "admin/add_eventdata";
    }

    @RequestMapping(value = "/add_eventdata", method = RequestMethod.POST)
    public String processAddEventData(@ModelAttribute("addEventDataForm") AdminAddEventDataForm addEventDataForm, Map<String, Object> model) {
        log.info("Chutia");
        GLAEventDao glaEventBean = (GLAEventDao) appContext.getBean("glaEvent");
        GLACategoryDao glacategoryBean = (GLACategoryDao) appContext.getBean("glaCategory");
        GLAUserDao glauserBean = (GLAUserDao) appContext.getBean("glaUser");
        GLAUser selectedglaUser = glauserBean.loaduserByName(addEventDataForm.getSelectedUser());
        GLACategory selectedglaCategory = glacategoryBean.loadcategoryByname(addEventDataForm.getSelectedCategory());
        GLAEvent glaEvent = new GLAEvent();
        glaEvent.setSession(addEventDataForm.getSession());
        glaEvent.setAction(addEventDataForm.getSelectedAction());
        glaEvent.setPlatform(addEventDataForm.getSelectedPlatform());
        glaEvent.setTimestamp(Timestamp.valueOf(addEventDataForm.getTimestamp()));
        glaEvent.setSource(addEventDataForm.getSelectedsource());
        glaEvent.setGlaUser(selectedglaUser);
        glaEvent.setGlaCategory(selectedglaCategory);
        GLAEntity glaEntity = new GLAEntity(addEventDataForm.getEntity(), addEventDataForm.getValue());
        glaEventBean.add(glaEvent,glaEntity);
        addEventDataForm.setSession(null);
        addEventDataForm.setTimestamp(null);
        addEventDataForm.setEntity(null);
        addEventDataForm.setValue(null);
        addEventDataForm.setUser(glauserBean.selectAllUsers());
        addEventDataForm.setCategory(glacategoryBean.selectAllMinors());
        model.put("addEventDataForm",addEventDataForm);
        return "admin/add_eventdata";
    }

    @RequestMapping(value = "/add_entitydata", method = RequestMethod.GET)
    public String getAddEntityData(Map<String, Object> model) {
        AdminAddEntityDataForm aaddEntityDataForm = new AdminAddEntityDataForm();
        GLAEventDao glaEventBean = (GLAEventDao) appContext.getBean("glaEvent");
        aaddEntityDataForm.setEvent(glaEventBean.selectAllEvents());
        model.put("addEntityDataForm",aaddEntityDataForm);
        return "admin/add_entitydata";
    }

    @RequestMapping(value = "/add_entitydata", method = RequestMethod.POST)
    public String processAddEntityData(Map<String, Object> model, @ModelAttribute("addEntityDataForm") AdminAddEntityDataForm addEntityDataForm) {
        GLAEntityDao glaEntityBean = (GLAEntityDao) appContext.getBean("glaEntity");
        glaEntityBean.addWithExistingEvent(new GLAEntity(addEntityDataForm.getKey(), addEntityDataForm.getValue()), addEntityDataForm.getSelectedevent());
        addEntityDataForm.setKey(null);
        addEntityDataForm.setValue(null);
        model.put("addEntityDataForm",addEntityDataForm);
        return "admin/add_entitydata";
    }

    @RequestMapping(value = "/delete_userdata", method = RequestMethod.GET)
    public ModelAndView getDeleteUserData() {
        return new ModelAndView("admin/delete_userdata");
    }

    @RequestMapping(value = "/delete_categorydata", method = RequestMethod.GET)
    public ModelAndView getDeleteCategoryData() {
        return new ModelAndView("admin/delete_categorydata");
    }

    @RequestMapping(value = "/delete_eventdata", method = RequestMethod.GET)
    public ModelAndView getDeleteEventData() {
        return new ModelAndView("admin/delete_eventdata");
    }

    @RequestMapping(value = "/delete_entitydata", method = RequestMethod.GET)
    public ModelAndView getDeleteEntityData() {
        return new ModelAndView("admin/delete_entitydata");
    }
    @RequestMapping(value = "/view_userdata", method = RequestMethod.GET)
    public ModelAndView getViewUserData(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView("admin/view_userdata");
    }

    @RequestMapping(value = "/view_categorydata", method = RequestMethod.GET)
    public ModelAndView getViewCategoryData() {
        return new ModelAndView("admin/view_categorydata");
    }

    @RequestMapping(value = "/view_eventdata", method = RequestMethod.GET)
    public ModelAndView getViewEventData() {
        return new ModelAndView("admin/view_eventdata");
    }

    @RequestMapping(value = "/view_entitydata", method = RequestMethod.GET)
    public ModelAndView getViewEntityData() {
        return new ModelAndView("admin/view_entitydata");
    }

    @RequestMapping(value = "/fetchGlauserData.web", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    String fetchglaUserData(HttpServletRequest request) throws IOException {
        //Fetch the page number from client
        Integer pageNumber = 0;
        if (null != request.getParameter("iDisplayStart"))
            pageNumber = (Integer.valueOf(request.getParameter("iDisplayStart"))/10)+1;

        //Fetch search parameter
        String searchParameter = request.getParameter("sSearch");

        //Fetch Page display length
        Integer pageDisplayLength = Integer.valueOf(request.getParameter("iDisplayLength"));

        //Create page list data
        GLAUserDao glauserBean = (GLAUserDao) appContext.getBean("glaUser");
        List<GLAUser> glaUserListList = glauserBean.loadUsersRange(pageDisplayLength);
        //Here is server side pagination logic. Based on the page number you could make call
        //to the data base create new list and send back to the client. For demo I am shuffling
        //the same list to show data randomly
        // Paging & searching Logic still has to be done
        if (pageNumber == 1) {
            Collections.shuffle(glaUserListList);
        }else if (pageNumber == 2) {
            Collections.shuffle(glaUserListList);
        }else {
            Collections.shuffle(glaUserListList);
        }
        //Search functionality: Returns filtered list based on search parameter
        //personsList = getListBasedOnSearchParameter(searchParameter,personsList);
        glaUserJsonObject userJsonObject = new glaUserJsonObject();
        //Set Total display record
        userJsonObject.setiTotalDisplayRecords(glauserBean.getTotalUsers());
        //Set Total record
        userJsonObject.setiTotalRecords(glauserBean.getTotalUsers());
        userJsonObject.setAaData(glaUserListList);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json2 = gson.toJson(userJsonObject);
        return json2;
    }

    @RequestMapping(value = "/fetchGlaEventData.web", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    String fetchglaEventData(HttpServletRequest request) throws IOException {
        //Fetch the page number from client
        Integer pageNumber = 0;
        if (null != request.getParameter("iDisplayStart"))
            pageNumber = (Integer.valueOf(request.getParameter("iDisplayStart"))/10)+1;

        //Fetch search parameter
        String searchParameter = request.getParameter("sSearch");

        //Fetch Page display length
        Integer pageDisplayLength = Integer.valueOf(request.getParameter("iDisplayLength"));

        //Create page list data
        GLAEventDao glaEventBean = (GLAEventDao) appContext.getBean("glaEvent");
        List<GLAEvent> glaEventList = glaEventBean.loadEventRange(pageDisplayLength);
        //Here is server side pagination logic. Based on the page number you could make call
        //to the data base create new list and send back to the client. For demo I am shuffling
        //the same list to show data randomly
        // Paging & searching Logic still has to be done
        if (pageNumber == 1) {
            Collections.shuffle(glaEventList);
        }else if (pageNumber == 2) {
            Collections.shuffle(glaEventList);
        }else {
            Collections.shuffle(glaEventList);
        }
        //Search functionality: Returns filtered list based on search parameter
        //personsList = getListBasedOnSearchParameter(searchParameter,personsList);
        GLAEventJsonObject glaCategoryJsonObject = new GLAEventJsonObject();
        //Set Total display record
        glaCategoryJsonObject.setiTotalDisplayRecords(glaEventBean.getTotalEvents());
        //Set Total record
        glaCategoryJsonObject.setiTotalRecords(glaEventBean.getTotalEvents());
        glaCategoryJsonObject.setAaData(glaEventList);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json2 = gson.toJson(glaCategoryJsonObject);
        return json2;
    }

    @RequestMapping(value = "/fetchGlaCategoryData.web", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    String fetchglaCategoryData(HttpServletRequest request) throws IOException {
        //Fetch the page number from client
        Integer pageNumber = 0;
        if (null != request.getParameter("iDisplayStart"))
            pageNumber = (Integer.valueOf(request.getParameter("iDisplayStart"))/10)+1;

        //Fetch search parameter
        String searchParameter = request.getParameter("sSearch");

        //Fetch Page display length
        Integer pageDisplayLength = Integer.valueOf(request.getParameter("iDisplayLength"));

        //Create page list data
        GLACategoryDao glacategoryBean = (GLACategoryDao) appContext.getBean("glaCategory");
        List<GLACategory> glaCategoryList = glacategoryBean.loadCategoryRange(pageDisplayLength);
        //Here is server side pagination logic. Based on the page number you could make call
        //to the data base create new list and send back to the client. For demo I am shuffling
        //the same list to show data randomly
        // Paging & searching Logic still has to be done
        if (pageNumber == 1) {
            Collections.shuffle(glaCategoryList);
        }else if (pageNumber == 2) {
            Collections.shuffle(glaCategoryList);
        }else {
            Collections.shuffle(glaCategoryList);
        }
        //Search functionality: Returns filtered list based on search parameter
        //personsList = getListBasedOnSearchParameter(searchParameter,personsList);
        GLACategoryJsonObject glaCategoryJsonObject = new GLACategoryJsonObject();
        //Set Total display record
        glaCategoryJsonObject.setiTotalDisplayRecords(glacategoryBean.getTotalCategories());
        //Set Total record
        glaCategoryJsonObject.setiTotalRecords(glacategoryBean.getTotalCategories());
        glaCategoryJsonObject.setAaData(glaCategoryList);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json2 = gson.toJson(glaCategoryJsonObject);
        return json2;
    }

    @RequestMapping(value = "/fetchGlaEntityData.web", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    String fetchglaEntityData(HttpServletRequest request) throws IOException {
        //Fetch the page number from client
        Integer pageNumber = 0;
        if (null != request.getParameter("iDisplayStart"))
            pageNumber = (Integer.valueOf(request.getParameter("iDisplayStart"))/10)+1;

        //Fetch search parameter
        String searchParameter = request.getParameter("sSearch");

        //Fetch Page display length
        Integer pageDisplayLength = Integer.valueOf(request.getParameter("iDisplayLength"));

        //Create page list data
        GLAEntityDao glaEntityBean = (GLAEntityDao) appContext.getBean("glaEntity");
        List<GLAEntity> glaEntityList = glaEntityBean.loadEntitesRange(pageDisplayLength);
        //Here is server side pagination logic. Based on the page number you could make call
        //to the data base create new list and send back to the client. For demo I am shuffling
        //the same list to show data randomly
        // Paging & searching Logic still has to be done
        if (pageNumber == 1) {
            Collections.shuffle(glaEntityList);
        }else if (pageNumber == 2) {
            Collections.shuffle(glaEntityList);
        }else {
            Collections.shuffle(glaEntityList);
        }
        //Search functionality: Returns filtered list based on search parameter
        //personsList = getListBasedOnSearchParameter(searchParameter,personsList);
        GLAEntityJsonObject glaEntityJsonObject = new GLAEntityJsonObject();
        //Set Total display record
        glaEntityJsonObject.setiTotalDisplayRecords(glaEntityBean.getTotalEntities());
        //Set Total record
        glaEntityJsonObject.setiTotalRecords(glaEntityBean.getTotalEntities());
        glaEntityJsonObject.setAaData(glaEntityList);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json2 = gson.toJson(glaEntityJsonObject);
        return json2;
    }



}
