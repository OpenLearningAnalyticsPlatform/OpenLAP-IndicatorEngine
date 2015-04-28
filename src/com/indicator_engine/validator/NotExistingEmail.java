package com.indicator_engine.validator;

/**
 * Created by Tanmaya Mahapatra on 28-04-2015.
 */
import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = NotExistingEmailValidator.class)
public @interface NotExistingEmail  {
    String message();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
