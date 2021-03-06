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

import com.indicator_engine.datamodel.GLAOperations;

import java.util.List;

/**
 * Created by Tanmaya Mahapatra on 01-04-2015.
 */
public interface GLAOperationsDao {
    public long addNewOperation(GLAOperations glaOperation);
    public List<String> selectAllOperations();
    public int getTotalOperations();
    public List<GLAOperations> loadOperationsRange(long maxId);
    public List<GLAOperations> loadAllOperations(String colName, String sortDirection, boolean sort);
    public List<GLAOperations> searchOperationsByName(String searchParameter, boolean exactSearch, String colName, String sortDirection, boolean sort);
}
