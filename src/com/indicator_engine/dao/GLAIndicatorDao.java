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

package com.indicator_engine.dao;

import com.indicator_engine.datamodel.GLAIndicator;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Tanmaya Mahapatra on 08-05-2015.
 */
public interface GLAIndicatorDao {
    //public long  add(GLAIndicator glaIndicator, GLAQueries glaQueries);
    public GLAIndicator loadByIndicatorID(long ID);
    public List<GLAIndicator> displayall(String colName, String sortDirection, boolean sort);
    public List<GLAIndicator> loadIndicatorsRange(long startRange, long endRange);
    public List<GLAIndicator> searchIndicatorsName(String searchParameter,boolean exactSearch,
                                                   String colName, String sortDirection, boolean sort);
    public int getTotalIndicators();
    public List<GLAIndicator>  loadByIndicatorByName(String indicatorName, boolean exact);
    public long findIndicatorID(String indicatorName);
    public void deleteIndicator(long indicator_id);
    public void updateStatistics(long ID);
    public long findQuestionID(long indicatorID);
}
