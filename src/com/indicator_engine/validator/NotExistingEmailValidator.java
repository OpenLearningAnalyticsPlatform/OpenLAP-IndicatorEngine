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
