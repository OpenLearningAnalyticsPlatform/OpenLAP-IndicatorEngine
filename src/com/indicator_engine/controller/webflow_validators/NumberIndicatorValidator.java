package com.indicator_engine.controller.webflow_validators;

import com.indicator_engine.dao.GLAIndicatorDao;
import com.indicator_engine.datamodel.GLAIndicator;
import com.indicator_engine.model.indicator_system.Number.NumberIndicator;
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
public class NumberIndicatorValidator {
    @Autowired
    private ApplicationContext appContext;

    public void validateIndicatorName(NumberIndicator numberIndicator, ValidationContext context) {

        MessageContext messages = context.getMessageContext();
        boolean status = true;
        GLAIndicatorDao glaIndicatorBean = (GLAIndicatorDao) appContext.getBean("glaIndicator");
        List<GLAIndicator> glaIndicators = glaIndicatorBean.displayall();
        for (GLAIndicator indicator : glaIndicators) {
            if (numberIndicator.getIndicatorName().equals(indicator.getIndicator_name())) {
                status = false;
                break;
            }
        }
        if(!status){
            messages.addMessage(new MessageBuilder().error().source("Indicator Name").
                    defaultText("Indicator name already Existing. Duplicate names not allowed.").build());
        }
    }
}
