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

package com.indicator_engine.controller.webflow_validators;

import com.indicator_engine.dao.GLAIndicatorDao;
import com.indicator_engine.dao.GLAQueriesDao;
import com.indicator_engine.datamodel.GLAIndicator;
import com.indicator_engine.datamodel.GLAQueries;
import com.indicator_engine.model.indicator_system.Number.NumberIndicator;
import com.indicator_engine.model.indicator_system.Number.SelectNumberParameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.binding.message.MessageContext;
import org.springframework.binding.validation.ValidationContext;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Tanmaya Mahapatra on 28-04-2015.
 */
@Component
@SuppressWarnings({"unused", "unchecked"})
public class SelectNumberParametersValidator {
    @Autowired
    private ApplicationContext appContext;


    public void validatePersistenceObjectSelection(SelectNumberParameters selectNumberParameters, ValidationContext context) {
        MessageContext messages = context.getMessageContext();
        if (selectNumberParameters.getSelectedPersistenceObject().isEmpty() || selectNumberParameters.getSelectedPersistenceObject().length() < 3)
            messages.addMessage(new MessageBuilder().error().source("SelectedPersistenceObject").
                    defaultText("Please Select a Persistence Object").build());
    }

    public void validateObjectPropertySelection(SelectNumberParameters selectNumberParameters, ValidationContext context) {

        MessageContext messages = context.getMessageContext();
        if (selectNumberParameters.getSelectedRetrievableObjects().isEmpty())
            messages.addMessage(new MessageBuilder().error().source("SelectedRetrievableObjects").
                    defaultText("Please Select a Retrievable Object").build());
    }

    public void validateSelectEventParameters(SelectNumberParameters selectNumberParameters, ValidationContext context) {

        MessageContext messages = context.getMessageContext();
        if (selectNumberParameters.getSelectedSource().isEmpty())
            messages.addMessage(new MessageBuilder().error().source("Source").
                    defaultText("Please Select a Source").build());
        if (selectNumberParameters.getSelectedAction().isEmpty())
            messages.addMessage(new MessageBuilder().error().source("Action").
                    defaultText("Please Select an Action").build());
        if (selectNumberParameters.getSelectedPlatform().isEmpty())
            messages.addMessage(new MessageBuilder().error().source("Platform").
                    defaultText("Please Select a Platform").build());
    }

    public void validateSpecifyCategory(SelectNumberParameters selectNumberParameters, ValidationContext context) {

        MessageContext messages = context.getMessageContext();
        if (selectNumberParameters.getSelectedType().isEmpty())
            messages.addMessage(new MessageBuilder().error().source("Type").
                    defaultText("Please Select a Type").build());
        if (selectNumberParameters.getSelectedMajor().isEmpty())
            messages.addMessage(new MessageBuilder().error().source("Major").
                    defaultText("Please Select a Major").build());
        if (selectNumberParameters.getSelectedMinor().isEmpty())
            messages.addMessage(new MessageBuilder().error().source("Minor").
                    defaultText("Please Select a Minor").build());
    }

    public void validateDisplayResult(SelectNumberParameters selectNumberParameters, ValidationContext context) {
        MessageContext messages = context.getMessageContext();
        boolean status = true;
        GLAQueriesDao glaQueryBean = (GLAQueriesDao) appContext.getBean("glaQueries");
        List<GLAQueries> glaQueries = glaQueryBean.displayall();
        for (GLAQueries glaQuery : glaQueries) {
            if (selectNumberParameters.getQuestionName().equals(glaQuery.getQuestion_name())) {
                status = false;
                break;
            }
        }
        if (selectNumberParameters.getQuestionName().isEmpty())
            messages.addMessage(new MessageBuilder().error().source("Question Name").
                    defaultText("Question name cannot be empty.").build());
        if(!status){
            messages.addMessage(new MessageBuilder().error().source("Question Name").
                    defaultText("Question name already Existing. Duplicate names not allowed.").build());
        }
    }

}