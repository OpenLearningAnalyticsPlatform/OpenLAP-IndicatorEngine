package com.indicator_engine.indicator_system;

import com.indicator_engine.model.IndicatorDefnOperationForm;
import com.indicator_engine.model.SelectNumberParameters;

import java.util.List;

/**
 * Created by Tanmaya Mahapatra on 31-03-2015.
 */
public interface IndicatorPreProcessingDao {

    public String retrieveOperation(IndicatorDefnOperationForm indicatorDefnOperationForm);
    public List<String> initAvailableEntities_DB(String minor);
    public List<String> initAvailableEvents_DB(String minor);
    public IndicatorDefnOperationForm initAvailableOperations_DB();
    public SelectNumberParameters initSelectNumberParametersObject();
    public List<String> initPopulateMinors(SelectNumberParameters selectNumberParameters);
    public List<String> initPopulateTypes(SelectNumberParameters selectNumberParameters);
    public List<String> initPopulateMajors(SelectNumberParameters selectNumberParameters);
    public void manageEValues(SelectNumberParameters obj);
    public void clearEValuesSpecifications(SelectNumberParameters selectNumberParameters );
}
