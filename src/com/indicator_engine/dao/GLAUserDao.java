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

import com.indicator_engine.datamodel.GLAUser;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Tanmaya Mahapatra on 19-03-2015.
 */
public interface GLAUserDao {

    public long add(GLAUser glaUser);
    public List<GLAUser> loadUsersRange(long startRange, long endRange);
    public int getTotalUsers();
    public List<String> selectAllUsers();
    public List<String> searchSimilarUserDetails(String userDetail, String searchCriteria);
    public GLAUser loaduserByName(String username);
    public List<GLAUser> searchLikeUsers(String searchParameter);
    public List<GLAUser> loadAll();
}
