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
import com.indicator_engine.model.indicator_system.Number.Questions;
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
    public List<String> initPopulateMinors(List<String> sources, String action, String platform);
    public List<String> initPopulateTypes(List<String> sources, String action, String platform);
    public List<String> initPopulateMajors(List<String> sources, String action, String platform);
    public void manageEValues(SelectNumberParameters obj);
    public List<String> searchUser(String keyword, String searchtype);
    public void clearSearchSettings(SelectNumberParameters selectNumberParameters);
    public List<String> searchSession(String keyword, String searchType);
    public void specifyNewTime(SelectNumberParameters selectNumberParameters);
    public void clearTimeSpecifications(SelectNumberParameters selectNumberParameters);
    public void searchTime(SelectNumberParameters selectNumberParameters);
    public void addDefaultEValues(SelectNumberParameters selectNumberParameters);
    public void addIndicator(SelectNumberParameters selectNumberParameters, Questions indicatorName);
    public void saveIndicator(Questions indicatorName);
    public void flushPrevQnData(SelectNumberParameters selectNumberParameters);
    public void flushAll(Questions questions, SelectNumberParameters selectNumberParameters, IndicatorDefnOperationForm availableOperations);
    public void retrieveQuestion(Questions questions);
}
