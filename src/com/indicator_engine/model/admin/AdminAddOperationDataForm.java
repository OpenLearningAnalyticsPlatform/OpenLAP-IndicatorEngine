package com.indicator_engine.model.admin;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Pattern;

/**
 * Created by Tanmaya Mahapatra on 01-04-2015.
 */
@SuppressWarnings({"unused", "unchecked"})
public class AdminAddOperationDataForm {

    @NotEmpty(message = "Operations cannot be empty")
    @Pattern(regexp="^[a-zA-Z]+$", message="Operations must be alphabets with no spaces")
    private String operations;

    public String getOperations() {
        return operations;
    }

    public void setOperations(String operations) {
        this.operations = operations;
    }
}
