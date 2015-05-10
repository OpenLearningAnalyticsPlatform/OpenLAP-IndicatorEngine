

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

import com.indicator_engine.datamodel.GLAEntity;
import com.indicator_engine.datamodel.GLAEvent;

import java.util.List;

/**
 * Created by Tanmaya Mahapatra on 28-02-2015.
 */
public interface GLAEventDao {
    public void add(GLAEvent gEvent, GLAEntity entity);
    public List<GLAEvent> loadAllEvents(String colName, String sortDirection, boolean sort);
    public int getTotalEvents();
    public List<String> selectAllEvents();
    public List<String> loadEventByCategoryID(Long categoryID);
    public GLAEvent loadEventByID(Long id);
    public List<String> selectAll(String EventComponent);
    public long findCategoryId(String action, String source, String platform);
    public List<String> searchSimilarSessionDetails(String searchType, String searchCriteria);
    public List<String> searchSimilarTimeDetails(String searchType, String searchCriteria);
    public List<GLAEvent> searchEventsByAction(String searchParameter, boolean exactSearch,
                                               String colName, String sortDirection, boolean sort);
}
