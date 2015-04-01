package com.indicator_engine.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tanmaya Mahapatra on 31-03-2015.
 */
public class IndicatorDefnOperationForm implements Serializable{

    private List<String> operation =  new ArrayList<>();
    private String selectedOperation;

    public List<String> getOperation() {
        return operation;
    }

    public void setOperation(List<String> operation) {
        this.operation = operation;
    }

    public String getSelectedOperation() {
        return selectedOperation;
    }

    public void setSelectedOperation(String selectedOperation) {
        this.selectedOperation = selectedOperation;
    }

    public void setInitialOperations(){
        operation.add("Number");
        selectedOperation=null;
    }
}
