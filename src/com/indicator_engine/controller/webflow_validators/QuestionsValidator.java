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

import com.indicator_engine.dao.GLAQuestionDao;
import com.indicator_engine.datamodel.GLAQuestion;
import com.indicator_engine.model.indicator_system.Number.Questions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageContext;
import org.springframework.binding.validation.ValidationContext;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Tanmaya Mahapatra on 08-05-2015.
 */
@Component
@SuppressWarnings({"unused", "unchecked"})
public class QuestionsValidator {
    @Autowired
    private ApplicationContext appContext;

    public void validateQuestionName(Questions questions, ValidationContext context) {

        MessageContext messages = context.getMessageContext();
        boolean status = true;
        GLAQuestionDao glaQuestionBean = (GLAQuestionDao) appContext.getBean("glaQuestions");
        List<GLAQuestion> glaQuestions = glaQuestionBean.displayAll(null, null, false);
        for (GLAQuestion gQuestion : glaQuestions) {
            if (questions.getQuestionName().equals(gQuestion.getQuestion_name())) {
                status = false;
                break;
            }
        }
        if(!status){
            messages.addMessage(new MessageBuilder().error().source("Question Name").
                    defaultText("Question name already Existing. Duplicate names not allowed.").build());
        }
    }
}
