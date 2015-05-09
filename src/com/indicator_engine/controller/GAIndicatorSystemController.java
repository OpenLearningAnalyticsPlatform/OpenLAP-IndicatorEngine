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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.indicator_engine.dao.GLAIndicatorDao;
import com.indicator_engine.dao.GLAUserDao;
import com.indicator_engine.datamodel.GLAIndicator;
import com.indicator_engine.model.indicator_system.Number.GLAIndicatorJsonObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * Created by Tanmaya Mahapatra on 23-03-2015.
 */
@Controller
@RequestMapping(value="/indicators")
@SuppressWarnings({"unused", "unchecked"})
public class GAIndicatorSystemController {

    @Autowired
    private ApplicationContext appContext;
    static Logger log = Logger.getLogger(GAIndicatorSystemController.class.getName());

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public ModelAndView getIndicatorsHome() {
        return new ModelAndView("indicator_system/indicators_home");
    }


    @RequestMapping(value = "/viewall", method = RequestMethod.GET)
    public ModelAndView getIndicatorsViewAll() {
        return new ModelAndView("indicator_system/viewall_indicators");
    }
    @RequestMapping(value = "/modify", method = RequestMethod.GET)
    public ModelAndView getIndicatorsModify() {
        return new ModelAndView("indicator_system/modify_indicator");
    }
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ModelAndView getIndicatorsDelete() {
        return new ModelAndView("indicator_system/delete_indicator");
    }
    @RequestMapping(value = "/trialrun", method = RequestMethod.GET)
    public ModelAndView getIndicatorsTrialRun() {
        return new ModelAndView("indicator_system/trial_run");
    }

    @RequestMapping(value = "/fetchExistingIndicatorsData.web", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    String fetchIndicatorData(HttpServletRequest request) throws IOException {

        GLAIndicatorDao glaIndicatorBean = (GLAIndicatorDao) appContext.getBean("glaIndicator");
        List<GLAIndicator> glaIndicatorList = null;
        //Fetch the page number from client
        Integer pageNumber = 0;
        if (null != request.getParameter("iDisplayStart"))
            pageNumber = (Integer.valueOf(request.getParameter("iDisplayStart"))/10)+1;
        //Fetch search parameter
        String searchParameter = request.getParameter("sSearch");
        //Fetch Page display length
        Integer pageDisplayLength = Integer.parseInt(request.getParameter("iDisplayLength"));
        Integer startRange = ((pageNumber-1)*pageDisplayLength)+1;
        Integer endRange = pageDisplayLength-1;
        //Create page list data
        if(searchParameter == null || searchParameter.isEmpty()) {
            glaIndicatorList = glaIndicatorBean.loadIndicatorsRange(startRange, endRange);
        }
        else
            glaIndicatorList = glaIndicatorBean.searchIndicatorsName(searchParameter);
        GLAIndicatorJsonObject glaIndicatorJsonObject = new GLAIndicatorJsonObject();
        //Set Total display record
        glaIndicatorJsonObject.setiTotalDisplayRecords(glaIndicatorBean.getTotalIndicators());
        //Set Total record
        glaIndicatorJsonObject.setiTotalRecords(glaIndicatorBean.getTotalIndicators());
        glaIndicatorJsonObject.setAaData(glaIndicatorList);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json2 = gson.toJson(glaIndicatorJsonObject);
        return json2;
    }

}
