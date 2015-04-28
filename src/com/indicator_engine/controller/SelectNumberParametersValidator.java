package com.indicator_engine.controller;

import com.indicator_engine.model.indicator_system.Number.SelectNumberParameters;
import org.springframework.binding.message.MessageContext;
import org.springframework.binding.validation.ValidationContext;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.stereotype.Component;

/**
 * Created by Tanmaya Mahapatra on 28-04-2015.
 */
@Component
public class SelectNumberParametersValidator {

    public void validatePersistenceObjectSelection(SelectNumberParameters selectNumberParameters, ValidationContext context) {
        MessageContext messages = context.getMessageContext();
        if (selectNumberParameters.getSelectedPersistenceObject().isEmpty() || selectNumberParameters.getSelectedPersistenceObject().length() < 3)
            messages.addMessage(new MessageBuilder().error().source("checkinDate").
                    defaultText("Please Select a Persistence Object").build());
    }

}