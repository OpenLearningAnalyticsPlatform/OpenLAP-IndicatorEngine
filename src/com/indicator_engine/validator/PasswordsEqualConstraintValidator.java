package com.indicator_engine.validator;

/**
 * Created by Tanmaya Mahapatra on 28-04-2015.
 */
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.indicator_engine.model.app.RegistrationForm;

public class PasswordsEqualConstraintValidator implements ConstraintValidator<PasswordsEqualConstraint, Object> {

    @Override
    public void initialize(PasswordsEqualConstraint arg0) {
    }

    @Override
    public boolean isValid(Object candidate, ConstraintValidatorContext arg1) {
        RegistrationForm user = (RegistrationForm) candidate;

        return user.getPassword().equals(user.getConfirmpassword());
    }
}