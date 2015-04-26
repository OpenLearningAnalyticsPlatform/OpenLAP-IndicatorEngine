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

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Tanmaya Mahapatra on 23-03-2015.
 */
@Controller
@RequestMapping(value="/indicators")
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

}
