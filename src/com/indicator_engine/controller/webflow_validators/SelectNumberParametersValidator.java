package com.indicator_engine.controller.webflow_validators;

import com.indicator_engine.model.indicator_system.Number.SelectNumberParameters;
import org.springframework.binding.message.MessageContext;
import org.springframework.binding.validation.ValidationContext;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.stereotype.Component;

/**
 * Created by Tanmaya Mahapatra on 28-04-2015.
 */
@Component
@SuppressWarnings({"unused", "unchecked"})
public class SelectNumberParametersValidator {

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
    public void validateSelectEntities(SelectNumberParameters selectNumberParameters, ValidationContext context) {

        MessageContext messages = context.getMessageContext();
    }

    public void validateUserSpecifications(SelectNumberParameters selectNumberParameters, ValidationContext context) {

        MessageContext messages = context.getMessageContext();
    }

    public void validateSessionSpecifications(SelectNumberParameters selectNumberParameters, ValidationContext context) {

        MessageContext messages = context.getMessageContext();
    }

    public void validateOtherSpecifications(SelectNumberParameters selectNumberParameters, ValidationContext context) {

        MessageContext messages = context.getMessageContext();
    }

    public void validateSelectFiltering(SelectNumberParameters selectNumberParameters, ValidationContext context) {

        MessageContext messages = context.getMessageContext();
    }








}