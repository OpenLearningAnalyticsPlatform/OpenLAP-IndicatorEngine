
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


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.indicator_engine.dao.*;
import com.indicator_engine.datamodel.*;
import com.indicator_engine.model.admin.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by Tanmaya Mahapatra on 18-03-2015.
 */
@Controller
@Scope("session")
@RequestMapping(value="/toolkit")
@SessionAttributes({"loggedIn", "userName", "sid", "activationStatus","role", "admin_access"})
public class ToolkitAdminController {

    @Autowired
    private ApplicationContext appContext;
    static Logger log = Logger.getLogger(ToolkitAdminController.class.getName());


    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public ModelAndView getToolkitAdmin() {
        return new ModelAndView("admin/toolkitadmin");
    }

    @RequestMapping(value = "/data_control_center", method = RequestMethod.GET)
    public ModelAndView getDataControlCenter() {
        return new ModelAndView("admin/data_control_center");
    }
    @RequestMapping(value = "/operations_control_center", method = RequestMethod.GET)
    public ModelAndView getOperationsControlCenter() {
        return new ModelAndView("admin/operations_control_center");
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
        log.info("Entering Method : processAddEventData");
        GLAEventDao glaEventBean = (GLAEventDao) appContext.getBean("glaEvent");
        GLACategoryDao glacategoryBean = (GLACategoryDao) appContext.getBean("glaCategory");
        GLAUserDao glauserBean = (GLAUserDao) appContext.getBean("glaUser");
        GLAUser selectedglaUser = glauserBean.loaduserByName(addEventDataForm.getSelectedUser());
        GLACategory selectedglaCategory = glacategoryBean.loadCategoryByName(addEventDataForm.getSelectedCategory());
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

        GLAUserDao glauserBean = (GLAUserDao) appContext.getBean("glaUser");
        List<GLAUser> glaUserList = null;
        List<GLAUser> pageGlaUserList = new ArrayList<>();
        Integer idisplayStart = 0;
        Integer iSortingCols =0;
        if(null != request.getParameter("iSortingCols"))
            iSortingCols = Integer.valueOf(request.getParameter("iSortingCols"));
        glaUserJsonObject userJsonObject = new glaUserJsonObject();
        if (null != request.getParameter("iDisplayStart")) {
            idisplayStart = Integer.valueOf(request.getParameter("iDisplayStart"));
        }
        //Fetch search parameter
        String searchParameter = request.getParameter("sSearch");
        //Fetch Page display length
        Integer pageDisplayLength = Integer.parseInt(request.getParameter("iDisplayLength"));
        //Create page list data
        if(searchParameter == null || searchParameter.isEmpty()) {
            String colName = null;
            String sortDirection =null;
            if(iSortingCols == 1 ) {
                Integer isortCol = Integer.valueOf(request.getParameter("iSortCol_0"));
                sortDirection = request.getParameter("sSortDir_0");
                if(isortCol == 0)
                    colName = "id";
                else if(isortCol == 1)
                    colName = "username";
                else if(isortCol == 2)
                    colName = "password";
                else if(isortCol == 3)
                    colName = "email";
                glaUserList = glauserBean.loadAll(colName, sortDirection,true);
            }
            else
                glaUserList = glauserBean.loadAll(colName, sortDirection,false);
            if(idisplayStart != -1){
                Integer endRange = idisplayStart+pageDisplayLength;
                if(endRange >glaUserList.size())
                    endRange = glaUserList.size();
                for(int i=idisplayStart; i<endRange; i++){
                    pageGlaUserList.add(glaUserList.get(i));
                }
            }
            //Set Total display record
            userJsonObject.setiTotalDisplayRecords(glauserBean.getTotalUsers());
            //Set Total record
            userJsonObject.setiTotalRecords(glauserBean.getTotalUsers());
            userJsonObject.setAaData(pageGlaUserList);
        }
        else {
            String colName = null;
            String sortDirection =null;
            if(iSortingCols == 1 ) {
                Integer isortCol = Integer.valueOf(request.getParameter("iSortCol_0"));
                sortDirection = request.getParameter("sSortDir_0");
                if (isortCol == 0)
                    colName = "id";
                else if (isortCol == 1)
                    colName = "username";
                else if (isortCol == 2)
                    colName = "password";
                else if (isortCol == 3)
                    colName = "email";
                glaUserList = glauserBean.searchLikeUsers(searchParameter,colName, sortDirection,true);
            }
            else
                glaUserList = glauserBean.searchLikeUsers(searchParameter,colName, sortDirection,false);
            if(idisplayStart != -1) {
                Integer endRange = idisplayStart+pageDisplayLength;
                Integer startRange = idisplayStart;
                if(startRange > glaUserList.size())
                    startRange = 0;
                if (endRange > glaUserList.size())
                    endRange = glaUserList.size();
                for (int i = startRange; i <endRange; i++) {
                    pageGlaUserList.add(glaUserList.get(i));
                }
            }
            //Set Total display record
            userJsonObject.setiTotalDisplayRecords(glaUserList.size());
            //Set Total record
            userJsonObject.setiTotalRecords(glaUserList.size());
            userJsonObject.setAaData(pageGlaUserList);
        }
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        String json2 = gson.toJson(userJsonObject);
        return json2;
    }

    @RequestMapping(value = "/fetchGlaOperationsData.web", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    String fetchglaOperations(HttpServletRequest request) throws IOException {

        GLAOperationsDao glaOperationsBean = (GLAOperationsDao) appContext.getBean("glaOperations");
        GLAOperationsJSONObj operationsJsonObject = new GLAOperationsJSONObj();
        List<GLAOperations>  glaOperationsList = null;
        List<GLAOperations>  pageGlaOperationsList = new ArrayList<>();
        Integer idisplayStart = 0;
        Integer iSortingCols =0;
        if(null != request.getParameter("iSortingCols"))
            iSortingCols = Integer.valueOf(request.getParameter("iSortingCols"));
        if (null != request.getParameter("iDisplayStart"))
            idisplayStart = Integer.valueOf(request.getParameter("iDisplayStart"));
        String searchParameter = request.getParameter("sSearch");
        Integer pageDisplayLength = Integer.parseInt(request.getParameter("iDisplayLength"));
        if(searchParameter == null || searchParameter.isEmpty()) {
            String colName = null;
            String sortDirection =null;
            if(iSortingCols == 1 ) {
                Integer isortCol = Integer.valueOf(request.getParameter("iSortCol_0"));
                sortDirection = request.getParameter("sSortDir_0");
                if (isortCol == 0)
                    colName = "id";
                else if (isortCol == 1)
                    colName = "operations";
                glaOperationsList = glaOperationsBean.loadAllOperations(colName, sortDirection,true);
            }
            else
                glaOperationsList = glaOperationsBean.loadAllOperations(colName, sortDirection,false);
            if(idisplayStart != -1){
                Integer endRange = idisplayStart+pageDisplayLength;
                if(endRange >glaOperationsList.size())
                    endRange = glaOperationsList.size();
                for(int i=idisplayStart; i<endRange; i++){
                    pageGlaOperationsList.add(glaOperationsList.get(i));
                }
            }
            //Set Total display record
            operationsJsonObject.setiTotalDisplayRecords(glaOperationsBean.getTotalOperations());
            //Set Total record
            operationsJsonObject.setiTotalRecords(glaOperationsBean.getTotalOperations());
            operationsJsonObject.setAaData(pageGlaOperationsList);
        }
        else {
            String colName = null;
            String sortDirection =null;
            if(iSortingCols == 1 ) {
                Integer isortCol = Integer.valueOf(request.getParameter("iSortCol_0"));
                sortDirection = request.getParameter("sSortDir_0");
                if (isortCol == 0)
                    colName = "id";
                else if (isortCol == 1)
                    colName = "operations";
                glaOperationsList = glaOperationsBean.searchOperationsByName(searchParameter, false,colName, sortDirection,true);
            }
            else
                glaOperationsList = glaOperationsBean.searchOperationsByName(searchParameter, false,colName, sortDirection,false);
            if(idisplayStart != -1) {
                Integer endRange = idisplayStart+pageDisplayLength;
                Integer startRange = idisplayStart;
                if(startRange > glaOperationsList.size())
                    startRange = 0;
                if (endRange > glaOperationsList.size())
                    endRange = glaOperationsList.size();
                for (int i = startRange; i <endRange; i++) {
                    pageGlaOperationsList.add(glaOperationsList.get(i));
                }
            }
            //Set Total display record
            operationsJsonObject.setiTotalDisplayRecords(glaOperationsList.size());
            //Set Total record
            operationsJsonObject.setiTotalRecords(glaOperationsList.size());
            operationsJsonObject.setAaData(pageGlaOperationsList);
        }
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        String json2 = gson.toJson(operationsJsonObject);
        return json2;
    }

    @RequestMapping(value = "/fetchGlaEventData.web", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    String fetchglaEventData(HttpServletRequest request) throws IOException {

        GLAEventDao glaEventBean = (GLAEventDao) appContext.getBean("glaEvent");
        List<GLAEvent> glaEventList = null;
        List<GLAEvent> pageGlaEventList = new ArrayList<>();
        Integer idisplayStart = 0;
        Integer iSortingCols =0;
        if(null != request.getParameter("iSortingCols"))
            iSortingCols = Integer.valueOf(request.getParameter("iSortingCols"));
        GLAEventJsonObject glaCategoryJsonObject = new GLAEventJsonObject();
        if (null != request.getParameter("iDisplayStart")) {
            idisplayStart = Integer.valueOf(request.getParameter("iDisplayStart"));
        }
        //Fetch search parameter
        String searchParameter = request.getParameter("sSearch");
        //Fetch Page display length
        Integer pageDisplayLength = Integer.parseInt(request.getParameter("iDisplayLength"));
        //Create page list data
        if(searchParameter == null || searchParameter.isEmpty()) {
            String colName = null;
            String sortDirection =null;
            if(iSortingCols == 1 ) {
                Integer isortCol = Integer.valueOf(request.getParameter("iSortCol_0"));
                sortDirection = request.getParameter("sSortDir_0");
                if (isortCol == 0)
                    colName = "id";
                else if (isortCol == 1)
                    colName = "action";
                else if (isortCol == 2)
                    colName = "session";
                else if (isortCol == 3)
                    colName = "platform";
                else if (isortCol == 4)
                    colName = "timestamp";
                else if(isortCol == 5)
                    colName = "source";
                else if(isortCol == 6)
                    colName = "glaCategory.id";
                else if(isortCol == 7)
                    colName = "glaUser.id";
                glaEventList = glaEventBean.loadAllEvents(colName, sortDirection,true);
            }
            else
                glaEventList = glaEventBean.loadAllEvents(colName, sortDirection,false);
            if(idisplayStart != -1){
                Integer endRange = idisplayStart+pageDisplayLength;
                if(endRange >glaEventList.size())
                    endRange = glaEventList.size();
                for(int i=idisplayStart; i<endRange; i++){
                    pageGlaEventList.add(glaEventList.get(i));
                }
            }
            //Set Total display record
            glaCategoryJsonObject.setiTotalDisplayRecords(glaEventBean.getTotalEvents());
            //Set Total record
            glaCategoryJsonObject.setiTotalRecords(glaEventBean.getTotalEvents());
            glaCategoryJsonObject.setAaData(pageGlaEventList);
        }
        else {
            String colName = null;
            String sortDirection =null;
            if(iSortingCols == 1 ) {
                Integer isortCol = Integer.valueOf(request.getParameter("iSortCol_0"));
                sortDirection = request.getParameter("sSortDir_0");
                if (isortCol == 0)
                    colName = "id";
                else if (isortCol == 1)
                    colName = "action";
                else if (isortCol == 2)
                    colName = "session";
                else if (isortCol == 3)
                    colName = "platform";
                else if (isortCol == 4)
                    colName = "timestamp";
                else if(isortCol == 5)
                    colName = "source";
                else if(isortCol == 6)
                    colName = "glaCategory.id";
                else if(isortCol == 7)
                    colName = "glaUser.id";
                glaEventList = glaEventBean.searchEventsByAction(searchParameter, false,colName, sortDirection,true);
            }
            else
                glaEventList = glaEventBean.searchEventsByAction(searchParameter, false,colName, sortDirection,false);
            if(idisplayStart != -1) {
                Integer endRange = idisplayStart+pageDisplayLength;
                Integer startRange = idisplayStart;
                if(startRange > glaEventList.size())
                    startRange = 0;
                if (endRange > glaEventList.size())
                    endRange = glaEventList.size();
                for (int i = startRange; i <endRange; i++) {
                    pageGlaEventList.add(glaEventList.get(i));
                }
            }
            //Set Total display record
            glaCategoryJsonObject.setiTotalDisplayRecords(glaEventList.size());
            //Set Total record
            glaCategoryJsonObject.setiTotalRecords(glaEventList.size());
            glaCategoryJsonObject.setAaData(pageGlaEventList);
        }
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        String json2 = gson.toJson(glaCategoryJsonObject);
        return json2;
    }

    @RequestMapping(value = "/fetchGlaCategoryData.web", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    String fetchglaCategoryData(HttpServletRequest request) throws IOException {
        GLACategoryDao glacategoryBean = (GLACategoryDao) appContext.getBean("glaCategory");
        List<GLACategory> glaCategoryList =null;
        List<GLACategory> pageGlaCategoryList = new ArrayList<>();
        Integer idisplayStart = 0;
        Integer iSortingCols =0;
        if(null != request.getParameter("iSortingCols"))
            iSortingCols = Integer.valueOf(request.getParameter("iSortingCols"));
        GLACategoryJsonObject glaCategoryJsonObject = new GLACategoryJsonObject();
        if (null != request.getParameter("iDisplayStart")) {
            idisplayStart = Integer.valueOf(request.getParameter("iDisplayStart"));
        }
        String searchParameter = request.getParameter("sSearch");
        Integer pageDisplayLength = Integer.parseInt(request.getParameter("iDisplayLength"));
        if(searchParameter == null || searchParameter.isEmpty()) {
            String colName = null;
            String sortDirection =null;
            if(iSortingCols == 1 ) {
                Integer isortCol = Integer.valueOf(request.getParameter("iSortCol_0"));
                sortDirection = request.getParameter("sSortDir_0");
                if (isortCol == 0)
                    colName = "id";
                else if (isortCol == 1)
                    colName = "type";
                else if (isortCol == 2)
                    colName = "major";
                else if (isortCol == 3)
                    colName = "minor";
                glaCategoryList = glacategoryBean.loadAll(colName, sortDirection,true);
            }
            else
                glaCategoryList = glacategoryBean.loadAll(colName, sortDirection,false);
            if(idisplayStart != -1){
                Integer endRange = idisplayStart+pageDisplayLength;
                if(endRange >glaCategoryList.size())
                    endRange = glaCategoryList.size();
                for(int i=idisplayStart; i<endRange; i++){
                    pageGlaCategoryList.add(glaCategoryList.get(i));
                }
            }
            //Set Total display record
            glaCategoryJsonObject.setiTotalDisplayRecords(glacategoryBean.getTotalCategories());
            //Set Total record
            glaCategoryJsonObject.setiTotalRecords(glacategoryBean.getTotalCategories());
            glaCategoryJsonObject.setAaData(pageGlaCategoryList);
        }
        else {
            String colName = null;
            String sortDirection =null;
            if(iSortingCols == 1 ) {
                Integer isortCol = Integer.valueOf(request.getParameter("iSortCol_0"));
                sortDirection = request.getParameter("sSortDir_0");
                if (isortCol == 0)
                    colName = "id";
                else if (isortCol == 1)
                    colName = "type";
                else if (isortCol == 2)
                    colName = "major";
                else if (isortCol == 3)
                    colName = "minor";
                glaCategoryList = glacategoryBean.searchCategoryByMinor(searchParameter,false,colName, sortDirection,true);
            }
            else
                glaCategoryList = glacategoryBean.searchCategoryByMinor(searchParameter,false,colName, sortDirection,false);
            if(idisplayStart != -1) {
                Integer endRange = idisplayStart+pageDisplayLength;
                Integer startRange = idisplayStart;
                if(startRange > glaCategoryList.size())
                    startRange = 0;
                if (endRange > glaCategoryList.size())
                    endRange = glaCategoryList.size();
                for (int i = startRange; i <endRange; i++) {
                    pageGlaCategoryList.add(glaCategoryList.get(i));
                }
            }
            glaCategoryJsonObject.setiTotalDisplayRecords(glaCategoryList.size());
            glaCategoryJsonObject.setiTotalRecords(glaCategoryList.size());
            glaCategoryJsonObject.setAaData(pageGlaCategoryList);
        }
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        String json2 = gson.toJson(glaCategoryJsonObject);
        return json2;
    }

    @RequestMapping(value = "/fetchGlaEntityData.web", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    String fetchglaEntityData(HttpServletRequest request) throws IOException {
        GLAEntityDao glaEntityBean = (GLAEntityDao) appContext.getBean("glaEntity");
        List<GLAEntity> glaEntityList = null;
        List<GLAEntity> pageGlaEntityList = new ArrayList<>();
        Integer idisplayStart = 0;
        Integer iSortingCols =0;
        if(null != request.getParameter("iSortingCols"))
            iSortingCols = Integer.valueOf(request.getParameter("iSortingCols"));
        GLAEntityJsonObject glaEntityJsonObject = new GLAEntityJsonObject();
        if (null != request.getParameter("iDisplayStart")) {
            idisplayStart = Integer.valueOf(request.getParameter("iDisplayStart"));
        }
        //Fetch search parameter
        String searchParameter = request.getParameter("sSearch");
        //Fetch Page display length
        Integer pageDisplayLength = Integer.parseInt(request.getParameter("iDisplayLength"));
        //Create page list data
        if(searchParameter == null || searchParameter.isEmpty()) {
            String colName = null;
            String sortDirection =null;
            if(iSortingCols == 1 ) {
                Integer isortCol = Integer.valueOf(request.getParameter("iSortCol_0"));
                sortDirection = request.getParameter("sSortDir_0");
                if (isortCol == 0)
                    colName = "entityId";
                else if (isortCol == 1)
                    colName = "key";
                else if (isortCol == 2)
                    colName = "value";
                else if (isortCol == 3)
                    colName = "events.id";
                else if (isortCol == 4)
                    colName = "users.id";
                else if (isortCol == 5)
                    colName = "category.id";
                if(colName == "value")
                    glaEntityList = glaEntityBean.loadAll(colName,sortDirection,false);
                else
                    glaEntityList = glaEntityBean.loadAll(colName,sortDirection,true);
            }
            else
                glaEntityList = glaEntityBean.loadAll(colName,sortDirection,false);
            if(idisplayStart != -1){
                Integer endRange = idisplayStart+pageDisplayLength;
                if(endRange >glaEntityList.size())
                    endRange = glaEntityList.size();
                for(int i=idisplayStart; i<endRange; i++){
                    pageGlaEntityList.add(glaEntityList.get(i));
                }
            }
            //Set Total display record
            glaEntityJsonObject.setiTotalDisplayRecords(glaEntityBean.getTotalEntities());
            //Set Total record
            glaEntityJsonObject.setiTotalRecords(glaEntityBean.getTotalEntities());
            glaEntityJsonObject.setAaData(pageGlaEntityList);
        }
        else {
            String colName = null;
            String sortDirection =null;
            if(iSortingCols == 1 ) {
                Integer isortCol = Integer.valueOf(request.getParameter("iSortCol_0"));
                sortDirection = request.getParameter("sSortDir_0");
                if (isortCol == 0)
                    colName = "entityId";
                else if (isortCol == 1)
                    colName = "key";
                else if (isortCol == 2)
                    colName = "value";
                else if (isortCol == 3)
                    colName = "events.id";
                else if (isortCol == 4)
                    colName = "users.id";
                else if (isortCol == 5)
                    colName = "category.id";
                if(colName == "value")
                    glaEntityList = glaEntityBean.searchEntitiesByKey(searchParameter, false,colName,sortDirection,false);
                else
                    glaEntityList = glaEntityBean.searchEntitiesByKey(searchParameter, false,colName,sortDirection,true);
            }
            else
                glaEntityList = glaEntityBean.searchEntitiesByKey(searchParameter, false,colName,sortDirection,false);
            if(idisplayStart != -1) {
                Integer endRange = idisplayStart+pageDisplayLength;
                Integer startRange = idisplayStart;
                if(startRange > glaEntityList.size())
                    startRange = 0;
                if (endRange > glaEntityList.size())
                    endRange = glaEntityList.size();
                for (int i = startRange; i <endRange; i++) {
                    pageGlaEntityList.add(glaEntityList.get(i));
                }
            }
            //Set Total display record
            glaEntityJsonObject.setiTotalDisplayRecords(glaEntityList.size());
            //Set Total record
            glaEntityJsonObject.setiTotalRecords(glaEntityList.size());
            glaEntityJsonObject.setAaData(pageGlaEntityList);
        }
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        String json2 = gson.toJson(glaEntityJsonObject);
        return json2;
    }

    @RequestMapping(value = "/add_json_user", method = RequestMethod.GET)
    public @ResponseBody String loadUserfromJSON() {
        Gson userGson = new Gson();
        GLAUSERDTO[] glausers= null;
        GLAUserDao glauserBean = (GLAUserDao) appContext.getBean("glaUser");
        try{
            glausers = userGson.fromJson(new FileReader("c:\\gla_User.json"), GLAUSERDTO[].class);

        } catch(FileNotFoundException e){}
        System.out.println(userGson.toJson(glausers));
        for(int i = 0; i < glausers.length; i++) {
            glauserBean.add(new GLAUser(glausers[i].getName(), glausers[i].getPassword(), glausers[i].getEmail()));
        }
        return  userGson.toJson(glausers);
    }

    @RequestMapping(value = "/add_json_category", method = RequestMethod.GET)
    public @ResponseBody String loadCategoryfromJSON() {
        Gson categoryGson = new Gson();
        GLACATEGORYDTO[] glacategory= null;
        GLACategoryDao glacategoryBean = (GLACategoryDao) appContext.getBean("glaCategory");
        try{
            glacategory = categoryGson.fromJson(new FileReader("c:\\gla_Category.json"), GLACATEGORYDTO[].class);

        } catch(FileNotFoundException e){}

        for(int i = 0; i < glacategory.length; i++) {
            glacategoryBean.add(new GLACategory(glacategory[i].getMAJOR(),glacategory[i].getMINOR(),glacategory[i].getTYPE()));
        }
        return  categoryGson.toJson(glacategory);
    }

    @RequestMapping(value = "/add_json_events", method = RequestMethod.GET)
    public @ResponseBody String loadEventsfromJSON() {
        Gson eventGson = new Gson();
        GLAEVENTDTO[] glaevents= null;
        GLAUserDao glauserBean = (GLAUserDao) appContext.getBean("glaUser");
        GLACategoryDao glacategoryBean = (GLACategoryDao) appContext.getBean("glaCategory");
        GLAEventDao glaEventBean = (GLAEventDao) appContext.getBean("glaEvent");
        try{
            glaevents = eventGson.fromJson(new FileReader("c:\\gla_Event.json"), GLAEVENTDTO[].class);

        } catch(FileNotFoundException e){}

        for(int i =0 ; i< glaevents.length ; i++) {

            GLAUser selectedglaUser = glauserBean.loaduserByName(glaevents[i].getUSERNAME());
            GLACategory selectedglaCategory = glacategoryBean.loadCategoryByName(glaevents[i].getCATEGORY());
            GLAEvent glaEvent = new GLAEvent();
            glaEvent.setSession(glaevents[i].getSESSION());
            glaEvent.setAction(glaevents[i].getACTION());
            glaEvent.setPlatform(glaevents[i].getPLATFORM());
            glaEvent.setTimestamp(Timestamp.valueOf(glaevents[i].getTIMESTAMP()));
            glaEvent.setSource(glaevents[i].getSOURCE());
            glaEvent.setGlaUser(selectedglaUser);
            glaEvent.setGlaCategory(selectedglaCategory);
            GLAEntity glaEntity = new GLAEntity(glaevents[i].getKEY(), glaevents[i].getVALUE());
            glaEventBean.add(glaEvent,glaEntity);
        }

        return  eventGson.toJson(glaevents);
    }

    @RequestMapping(value = "/add_json_entity", method = RequestMethod.GET)
    public @ResponseBody String loadEntityfromJSON() {
        Gson entityGson = new Gson();
        GLAENTITYDTO[] glaentity= null;
        GLAEntityDao glaEntityBean = (GLAEntityDao) appContext.getBean("glaEntity");
        try{
            glaentity = entityGson.fromJson(new FileReader("c:\\gla_Entity.json"), GLAENTITYDTO[].class);

        } catch(FileNotFoundException e){}
        for(int i =0 ; i< glaentity.length ; i++) {
            glaEntityBean.addWithExistingEvent(new GLAEntity(glaentity[i].getKEY(), glaentity[i].getVALUE()), glaentity[i].getEVENT_ID());

        }
        return  entityGson.toJson(glaentity);

    }

    // Handling of Operations

    @RequestMapping(value = "/add_operation", method = RequestMethod.GET)
    public String getAddOperations(Map<String, Object> model) {
        AdminAddOperationDataForm addOperationDataForm = new AdminAddOperationDataForm();
        model.put("addOperationDataForm", addOperationDataForm);
        return "admin/add_operation";
    }

    @RequestMapping(value = "/add_operation", method = RequestMethod.POST)
    public String processAddOperations (Map<String, Object> model, @ModelAttribute("addOperationDataForm") AdminAddOperationDataForm addOperationDataForm) {
        GLAOperationsDao glaOperationsBean = (GLAOperationsDao) appContext.getBean("glaOperations");
        GLAOperations glaOperations = new GLAOperations(addOperationDataForm.getOperations());
        glaOperationsBean.addNewOperation(glaOperations);
        model.put("addOperationDataForm", addOperationDataForm);
        return "admin/add_operation";
    }

    @RequestMapping(value = "/view_operations", method = RequestMethod.GET)
    public ModelAndView getViewOperations() {
        return new ModelAndView("admin/view_operations");
    }


}
class GLAENTITYDTO
{
    String KEY;
    String VALUE;
    String EVENT_ID;

    public String getKEY() {
        return KEY;
    }

    public String getVALUE() {
        return VALUE;
    }

    public String getEVENT_ID() {
        return EVENT_ID;
    }
}

class GLAUSERDTO
{
    String Password;
    String FName;
    String LName;
    String Name;
    String Email;

    public String getPassword() {
        return Password;
    }

    public String getFName() {
        return FName;
    }

    public String getLName() {
        return LName;
    }

    public String getName() {
        return Name;
    }

    public String getEmail() {
        return Email;
    }
}

class GLACATEGORYDTO
{
    String TYPE;
    String MAJOR;
    String MINOR;

    public String getTYPE() {
        return TYPE;
    }

    public String getMAJOR() {
        return MAJOR;
    }

    public String getMINOR() {
        return MINOR;
    }
}

class GLAEVENTDTO
{
    String SOURCE;
    String ACTION;
    String PLATFORM;
    String SESSION;
    String TIMESTAMP;
    String KEY;
    String VALUE;
    String USERNAME;
    String CATEGORY;

    public String getSOURCE() {
        return SOURCE;
    }

    public String getACTION() {
        return ACTION;
    }

    public String getPLATFORM() {
        return PLATFORM;
    }

    public String getSESSION() {
        return SESSION;
    }

    public String getTIMESTAMP() {
        return TIMESTAMP;
    }

    public String getKEY() {
        return KEY;
    }

    public String getVALUE() {
        return VALUE;
    }

    public String getUSERNAME() {
        return USERNAME;
    }

    public String getCATEGORY() {
        return CATEGORY;
    }
}