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
