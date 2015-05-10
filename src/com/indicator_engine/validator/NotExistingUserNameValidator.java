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

package com.indicator_engine.validator;

import com.indicator_engine.controller.LoginController;
import com.indicator_engine.dao.UserCredentialsDao;
import com.indicator_engine.datamodel.UserCredentials;
import com.indicator_engine.misc.ApplicationContextProvider;
import com.indicator_engine.model.app.RegistrationForm;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

/**
 * Created by Tanmaya Mahapatra on 28-04-2015.
 */
@SuppressWarnings({"unused", "unchecked"})
public class NotExistingUserNameValidator implements ConstraintValidator<NotExistingUserName, Object> {
    static Logger log = Logger.getLogger(NotExistingUserNameValidator.class.getName());

    ApplicationContext appContext = ApplicationContextProvider.getApplicationContext();

    @Override
    public void initialize(NotExistingUserName arg0) {
    }

    @Override
    public boolean isValid(Object candidate, ConstraintValidatorContext arg1) {

        log.info("NotExistingUserNameValidator Execution Started");
        RegistrationForm user = (RegistrationForm) candidate;
        String userName = user.getUserName();
        UserCredentialsDao userDetailsBean = (UserCredentialsDao) appContext.getBean("userDetails");
        boolean status = true;
        List<UserCredentials> selectedUserList = userDetailsBean.searchByUserName(userName);
        for (UserCredentials eachuser : selectedUserList) {
            if (userName.equals(eachuser.getUname())) {
                status = false;
                break;
            }
        }
        log.info("NotExistingUserNameValidator Execution Finished : " + status);
        return status;

    }
}
