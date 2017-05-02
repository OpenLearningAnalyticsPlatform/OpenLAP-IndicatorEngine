/*
 * Open Learning Analytics Platform (OpenLAP) : Indicator Engine

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

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

/**
 * Provides Methods to renders form and stores data to the server via API
 * @author Memoona Mughal
 * @since 20/06/2016
 *
 */
@Controller
@Scope("session")
@SessionAttributes({"loggedIn", "userName", "sid", "activationStatus","role", "admin_access"})
@RequestMapping(value="/admin")
@SuppressWarnings({"unused", "unchecked"})
public class AdminController {

    /**
     * Renders Analytics Method Jar submission form
     */
    @RequestMapping(value = "/analytics_method/new", method = RequestMethod.GET)
    public @ResponseBody
    ModelAndView analytics_method_new() {

        return new ModelAndView("indicator_system/admin/analytics_method");
    }

    /**
     * Renders Visualization Jar submission form
     */
    @RequestMapping(value = "/visualization/new", method = RequestMethod.GET)
    public @ResponseBody
    ModelAndView visualization_new() {

        return new ModelAndView("indicator_system/admin/visualization");
    }

}
