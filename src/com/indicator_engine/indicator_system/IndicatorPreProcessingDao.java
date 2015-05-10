/*
 * Open Platform Learning Analytics : Indicator Engine
 * Copyright (C) 2015  Learning Technologies Group, RWTH
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package com.indicator_engine.indicator_system;

import com.indicator_engine.model.indicator_system.IndicatorDefnOperationForm;
import com.indicator_engine.model.indicator_system.Number.NumberIndicator;
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
    public void addQuestion(SelectNumberParameters selectNumberParameters, NumberIndicator indicatorName);
    public void saveIndicator(NumberIndicator indicatorName);
    public void flushPrevQnData(SelectNumberParameters selectNumberParameters);
    public void flushAll(NumberIndicator numberIndicator, SelectNumberParameters selectNumberParameters, IndicatorDefnOperationForm availableOperations);
    public void retreiveFromDB(NumberIndicator numberIndicator);
}
