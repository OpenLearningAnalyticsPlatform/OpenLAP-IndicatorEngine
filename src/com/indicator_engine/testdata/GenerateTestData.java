/*
 *
 *  * Copyright (C) 2015  Tanmaya Mahapatra
 *  *
 *  * This program is free software; you can redistribute it and/or
 *  * modify it under the terms of the GNU General Public License
 *  * as published by the Free Software Foundation; either version 2
 *  * of the License, or (at your option) any later version.
 *  *
 *  * This program is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public License
 *  * along with this program; if not, write to the Free Software
 *  * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *
 */

/**
 * Created by Tanmaya Mahapatra on 25-02-2015.
 */

package com.indicator_engine.testdata;

import com.indicator_engine.datamodel.UserCredentials;
import com.indicator_engine.datamodel.UserProfile;
import org.fluttercode.datafactory.impl.DataFactory;

// http://www.andygibson.net/blog/article/generate-test-data-with-datafactory/
@SuppressWarnings({"unused", "unchecked"})
public class GenerateTestData {

    public UserCredentials generateUserData() {
        DataFactory df = new DataFactory();
        String uname = "Tanmaya";
        return( new UserCredentials(uname,"opensuse",
                new UserProfile(uname,"Mahapatra",df.getBirthDate(),df.getNumberBetween(10000, 999999),
                        df.getAddress(),"NRW","Germany",df.getNumberBetween(10000, 999999),df.getCity(), "tanmaya.mahapatra@rwth-aachen.de")));

    }
    public void generateEventData(){

    }
}
