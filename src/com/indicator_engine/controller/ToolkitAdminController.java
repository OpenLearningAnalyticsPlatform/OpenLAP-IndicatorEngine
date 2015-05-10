
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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
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
@RequestMapping(value="/toolkit")
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

        GLAUserDao glauserBean = (GLAUserDao) appContext.getBean("glaUser");
        List<GLAUser> glaUserList = null;
        Integer pageNumber = 0;
        if (null != request.getParameter("iDisplayStart"))
            pageNumber = (Integer.valueOf(request.getParameter("iDisplayStart"))/10)+1;
        //Fetch search parameter
        String searchParameter = request.getParameter("sSearch");
        //Fetch Page display length
        Integer pageDisplayLength = Integer.parseInt(request.getParameter("iDisplayLength"));

        //Create page list data
        if(searchParameter == null || searchParameter.isEmpty()) {
            glaUserList = glauserBean.loadAll();
        }
        else {
            glaUserList = glauserBean.searchLikeUsers(searchParameter);
        }
        if(pageNumber !=-1){
            Integer startRange = ((pageNumber-1)*pageDisplayLength)+1;
            Integer endRange = pageDisplayLength;
            glaUserList = glauserBean.loadUsersRange(startRange,endRange);
        }

        glaUserJsonObject userJsonObject = new glaUserJsonObject();
        //Set Total display record
        userJsonObject.setiTotalDisplayRecords(glauserBean.getTotalUsers());
        //Set Total record
        userJsonObject.setiTotalRecords(glauserBean.getTotalUsers());
        userJsonObject.setAaData(glaUserList);
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        String json2 = gson.toJson(userJsonObject);
        return json2;
    }

    @RequestMapping(value = "/fetchGlaOperationsData.web", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    String fetchglaOperations(HttpServletRequest request) throws IOException {
        //Fetch the page number from client
        Integer pageNumber = 0;
        if (null != request.getParameter("iDisplayStart"))
            pageNumber = (Integer.valueOf(request.getParameter("iDisplayStart"))/10)+1;

        //Fetch search parameter
        String searchParameter = request.getParameter("sSearch");

        //Fetch Page display length
        Integer pageDisplayLength = Integer.valueOf(request.getParameter("iDisplayLength"));

        //Create page list data
        GLAOperationsDao glaOperationsBean = (GLAOperationsDao) appContext.getBean("glaOperations");
        List<GLAOperations> glaOperationsList = glaOperationsBean.loadOperationsRange(pageDisplayLength);
        //Here is server side pagination logic. Based on the page number you could make call
        //to the data base create new list and send back to the client. For demo IndicatorPreProcessing am shuffling
        //the same list to show data randomly
        // Paging & searching Logic still has to be done
        if (pageNumber == 1) {
            Collections.shuffle(glaOperationsList);
        }else if (pageNumber == 2) {
            Collections.shuffle(glaOperationsList);
        }else {
            Collections.shuffle(glaOperationsList);
        }
        //Search functionality: Returns filtered list based on search parameter
        //personsList = getListBasedOnSearchParameter(searchParameter,personsList);
        GLAOperationsJSONObj operationsJsonObject = new GLAOperationsJSONObj();
        //Set Total display record
        operationsJsonObject.setiTotalDisplayRecords(glaOperationsBean.getTotalOperations());
        //Set Total record
        operationsJsonObject.setiTotalRecords(glaOperationsBean.getTotalOperations());
        operationsJsonObject.setAaData(glaOperationsList);
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        String json2 = gson.toJson(operationsJsonObject);
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
        //to the data base create new list and send back to the client. For demo IndicatorPreProcessing am shuffling
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
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
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
        //to the data base create new list and send back to the client. For demo IndicatorPreProcessing am shuffling
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
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
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
        //to the data base create new list and send back to the client. For demo IndicatorPreProcessing am shuffling
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
            GLACategory selectedglaCategory = glacategoryBean.loadcategoryByname(glaevents[i].getCATEGORY());
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




