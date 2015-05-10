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

package com.indicator_engine.indicator_system.Number;

import com.indicator_engine.model.indicator_system.Number.EntityValues;
import com.indicator_engine.model.indicator_system.Number.UserSearchSpecifications;

import java.util.List;

/**
 * Created by Tanmaya Mahapatra on 27-04-2015.
 */
public interface ProcessUserFiltersDao {

    public String processEntities( List<EntityValues> entityValues , String filter);
    public String processUsers( List<UserSearchSpecifications>  userSpecifications, String filter);
    public String processSource( List<String> sources , String filter);
}
