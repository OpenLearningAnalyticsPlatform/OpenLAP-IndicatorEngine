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

package com.indicator_engine.validator;

import com.indicator_engine.dao.UserCredentialsDao;
import com.indicator_engine.datamodel.UserCredentials;
import com.indicator_engine.misc.ApplicationContextProvider;
import com.indicator_engine.model.app.RegistrationForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Tanmaya Mahapatra on 28-04-2015.
 */
public class NotExistingEmailValidator implements ConstraintValidator<NotExistingEmail, Object> {

    static Logger log=Logger.getLogger(NotExistingEmailValidator.class.getName());

    ApplicationContext appContext = ApplicationContextProvider.getApplicationContext();

    @Override
    public void initialize(NotExistingEmail arg0){
    }

    @Override
    public boolean isValid(Object candidate,ConstraintValidatorContext arg1){

        log.info("NotExistingEmailValidator Execution Started");
        RegistrationForm user=(RegistrationForm)candidate;
        String email=user.getEmail();
        UserCredentialsDao userDetailsBean = (UserCredentialsDao) appContext.getBean("userDetails");
        boolean status = true;
        List<UserCredentials> selectedUserList = userDetailsBean.displayall();
        for (UserCredentials eachuser : selectedUserList) {
            if (email.equals(eachuser.getUp().getEmailid())) {
                status = false;
                break;
            }
        }
        return status;

    }
}
