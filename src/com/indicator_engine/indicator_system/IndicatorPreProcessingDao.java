package com.indicator_engine.indicator_system;

import com.indicator_engine.model.indicator_system.IndicatorDefnOperationForm;
import com.indicator_engine.model.indicator_system.Number.IndicatorNaming;
import com.indicator_engine.model.indicator_system.Number.SelectNumberParameters;

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
    public void specifyNewUser(SelectNumberParameters selectNumberParameters);
    public void clearUserSpecifications(SelectNumberParameters selectNumberParameters );
    public void searchUser(SelectNumberParameters selectNumberParameters);
    public void clearSearchSettings(SelectNumberParameters selectNumberParameters);
    public void specifyNewSession(SelectNumberParameters selectNumberParameters);
    public void clearSessionSpecifications(SelectNumberParameters selectNumberParameters);
    public void searchSession(SelectNumberParameters selectNumberParameters);
    public void specifyNewTime(SelectNumberParameters selectNumberParameters);
    public void clearTimeSpecifications(SelectNumberParameters selectNumberParameters);
    public void searchTime(SelectNumberParameters selectNumberParameters);
    public void addDefaultEValues(SelectNumberParameters selectNumberParameters);
    public void addQuestion(SelectNumberParameters selectNumberParameters, IndicatorNaming indicatorName);
    public void saveIndicator(IndicatorNaming indicatorName);
}
