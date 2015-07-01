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
import com.indicator_engine.datamodel.GLAQuestion;

import java.util.List;
import java.util.Set;

/**
 * Created by Tanmaya Mahapatra on 03-06-2015.
 */
public interface GLAQuestionDao {
    public long add(GLAQuestion glaQuestion, Set<GLAIndicator> glaIndicatorHashSet);
    public List<GLAQuestion> displayAll(String colName, String sortDirection, boolean sort);
    public long findQuestionID(String questionName);
    public GLAQuestion loadByQuestionID(long ID);
    public void updateStatistics(long ID);
    public List<GLAQuestion> loadByQuestionName(String questionName, boolean exact);
    public int getTotalQuestions();
    public List<GLAQuestion> searchQuestionsName(String searchParameter, boolean exactSearch,
                                                 String colName, String sortDirection, boolean sort);
}
