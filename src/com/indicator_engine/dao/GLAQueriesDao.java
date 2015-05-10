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

import com.indicator_engine.datamodel.GLAQueries;

import java.util.List;

/**
 * Created by Tanmaya Mahapatra on 08-05-2015.
 */
public interface GLAQueriesDao {

    public long add(GLAQueries glaQueries);
    public void addWithExistingIndicator(GLAQueries glaQueries, long id);
    public List<GLAQueries> displayall();
    public List<GLAQueries> searchQuestionsName(String searchParameter,boolean exactSearch);
    public long findQuestionID(String questionName);
    public void deleteQuestion(long question_id);

}
