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
import com.indicator_engine.model.indicator_system.Number.SessionSpecifications;
import com.indicator_engine.model.indicator_system.Number.TimeSearchSpecifications;
import com.indicator_engine.model.indicator_system.Number.UserSearchSpecifications;
import jxl.write.DateTime;
import org.drools.definition.process.*;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

/**
 * Parses the User Input given during Indicator Definition process and generates equivalent Hibernate Queries.
 * @author Tanmaya Mahapatra
 * @since 26/04/2015
 */
@SuppressWarnings({"unused", "unchecked"})
public class ProcessUserFilters implements ProcessUserFiltersDao {

    @Override
    public String processSource( List<String> sources , String filter){
        String hibenateQuery ="(";
        int counter = 0;
        for(String source : sources){
            if(counter == 0) {
                hibenateQuery += "'"+ source + "' ";
                counter++;
                continue;
            }
            if(counter >= 1 ) {
                hibenateQuery += ", '"+ source + "' ";
                counter++;
                continue;
            }
        }
        hibenateQuery += ")";
        return hibenateQuery;
    }

    @Override
    public String processEntities( List<EntityValues> entityValues , String filter){
        String entityTextAllQuery = "AND key IN (";
        String entityTextValueQuery = "AND key IN (";
        String entityRegexAllQuery = "AND key IN (";
        String entityRegexValueQuery = "AND key IN (";
        String hibernateQuery = " ";
        int entityTextAllCounter = 0;
        int entityTextValueCounter = 0;
        int entityRegexAllCounter = 0;
        int entityRegexValueCounter = 0;
        String key, type, eValue;
        key = type = eValue = null;
        int totalLength = entityValues.size();
        for (EntityValues eV : entityValues)
        {
            key = eV.getKey();
            type = eV.getType();
            eValue = eV.geteValues();
            if(type.equals("Text"))
            {
                if(eValue.equals("ALL"))
                {
                    if(entityTextAllCounter == 0 ) {
                        entityTextAllQuery += " ( SELECT key FROM GLAEntity WHERE  key = '"+key +"' ";
                        entityTextAllCounter++;
                    }
                    else{
                        entityTextAllQuery += "  OR key = '"+key +"' ";
                        entityTextAllCounter++;
                    }

                }
                else
                {
                    if(entityTextValueCounter == 0)
                    {
                        entityTextValueQuery += " ( SELECT key FROM GLAEntity WHERE ( key = '"+key +"' "+ filter + " value LIKE'"+eValue+"' ) ";
                        entityTextValueCounter++;
                    }
                    else
                    {
                        entityTextValueQuery += " OR (  key = '"+key +"' "+ filter + " value LIKE '"+eValue+"' ) ";
                        entityTextValueCounter++;
                    }
                }
            }
            else if(type.equals("REGEX"))
            {
                if(eValue.equals("ALL"))
                {
                    if(entityRegexAllCounter == 0)
                    {
                        entityRegexAllQuery += " ( SELECT key FROM GLAEntity WHERE  key LIKE '%"+key +"%' " ;
                        entityRegexAllCounter++;
                    }
                    else
                    {
                        entityRegexAllQuery += " OR key LIKE '%"+key +"%'  ";
                        entityRegexAllCounter++;
                    }


                }
                else
                {
                    if(entityRegexValueCounter == 0)
                    {
                        entityRegexValueQuery += " ( SELECT key FROM GLAEntity WHERE ( key LIKE '%"+key +"%' " + filter + " value LIKE '%"+eValue+"%' ) ";
                        entityRegexValueCounter++;
                    }
                    else
                    {
                        entityRegexValueQuery += " OR (  key LIKE '%"+key +"%' " + filter + " value LIKE '%"+eValue+"%' ) ";
                        entityRegexValueCounter++;
                    }
                }
            }
        }
        entityTextAllQuery += ") )";
        entityTextValueQuery += ") )";
        entityRegexAllQuery += ") )";
        entityRegexValueQuery += ") )";

        if(entityTextAllCounter > 0)
            hibernateQuery += entityTextAllQuery;
        if(entityTextValueCounter > 0)
            hibernateQuery += entityTextValueQuery;
        if(entityRegexAllCounter > 0)
            hibernateQuery += entityRegexAllQuery;
        if(entityRegexValueCounter > 0)
            hibernateQuery += entityRegexValueQuery;
        return hibernateQuery;
    }

    @Override
    public String processUsers( List<UserSearchSpecifications>  userSpecifications, String filter){
        String hibernateQuery=" ";
        String hibernateUserNameQuery =" AND glaEvent.glaUser.username IN (";
        String hibernateUserEmailQuery = " AND glaEvent.glaUser.email IN (";
        String hibernateLikeUserNameQuery = " AND glaEvent.glaUser.username IN (";
        String hibernateLikeEmailQuery = " AND glaEvent.glaUser.email IN (";
        // For keeping track of how many queries are generated for each case
        int counterUserName = 0;
        int counterEmail = 0;
        int counterLikeUserName = 0;
        int counterLikeEmail =0;
        // For individual members of each UserSearchSpecifications
        String pattern, type;
        String user;
        pattern = type = user = null;
        for(UserSearchSpecifications userSpec : userSpecifications){
            user = userSpec.getUserSearch();
            type = userSpec.getUserSearchType();
            pattern = userSpec.getSearchPattern();
            if(pattern.equals("EXACT")) {
                if(type.equals("UserName")) {
                    if(counterUserName == 0) {
                        hibernateUserNameQuery += "'"+ user + "' ";
                        counterUserName++;
                        continue;
                    }
                    if(counterUserName >= 1 ) {
                        hibernateUserNameQuery += ", '"+ user + "' ";
                        counterUserName++;
                        continue;
                    }

                }
                if(type.equals("UserEmail")) {
                    if(counterEmail == 0) {
                        hibernateUserEmailQuery += "'"+ user + "' ";
                        counterEmail++;
                        continue;
                    }
                    if(counterEmail >= 1 ) {
                        hibernateUserEmailQuery += ", '"+ user + "' ";
                        counterEmail++;
                        continue;
                    }

                }
            }
            if(pattern.equals("REGEX")) {
                if(type.equals("UserName")) {
                    if(counterLikeUserName == 0) {
                        hibernateLikeUserNameQuery += " ( SELECT username FROM GLAUser " +
                                " WHERE username LIKE  '%"+ user + "%' ";
                        counterLikeUserName++;
                        continue;
                    }
                    if(counterLikeUserName >= 1 ) {
                        hibernateLikeUserNameQuery += filter +" username LIKE '%"+ user + "%' ";
                        counterLikeUserName++;
                        continue;
                    }
                }
                if(type.equals("UserEmail")) {
                    if(counterLikeEmail == 0) {
                        hibernateLikeEmailQuery += " ( SELECT email FROM GLAUser " +
                                " WHERE email LIKE  '%"+ user + "%' ";
                        counterLikeEmail++;
                        continue;
                    }
                    if(counterLikeEmail >= 1 ) {
                        hibernateLikeEmailQuery += filter+" email LIKE '%"+ user + "%' ";
                        counterLikeEmail++;
                        continue;
                    }
                }
            }
        }
        hibernateLikeUserNameQuery +=") )";
        hibernateLikeEmailQuery +=") )";
        hibernateUserNameQuery += " )";
        hibernateUserEmailQuery += " )";
        if(counterUserName > 0)
            hibernateQuery += hibernateUserNameQuery;
        if(counterEmail > 0)
            hibernateQuery += hibernateUserEmailQuery;
        if(counterLikeUserName > 0)
            hibernateQuery += hibernateLikeUserNameQuery;
        if(counterLikeEmail > 0)
            hibernateQuery += hibernateLikeEmailQuery;
        return hibernateQuery;

    }

    public String processSessions( List<SessionSpecifications>  sessionSpecifications, String filter){
        String hibernateQuery=" ";
        String hibernateExactSession =" AND glaEvent.session IN (";
        String hibernateLikeSession =" AND glaEvent.session IN (";
        String session , type;
        session = type = null;
        int counterSession = 0;
        int counterLikeSesion = 0;
        for(SessionSpecifications sessionSpec : sessionSpecifications){
            session = sessionSpec.getSession();
            type = sessionSpec.getType();
            if(type.equals("EXACT")) {
                if(counterSession == 0) {
                    hibernateExactSession += "'"+ session + "' ";
                    counterSession++;
                    continue;
                }
                if(counterSession >= 1 ) {
                    hibernateExactSession += ", '"+ session + "' ";
                    counterSession++;
                    continue;
                }
            }
            if(type.equals("REGEX")) {
                if(counterLikeSesion == 0) {
                    hibernateLikeSession += " ( SELECT session FROM GLAEvent " +
                            " WHERE session LIKE  '%"+ session + "%' ";
                    counterLikeSesion++;
                    continue;
                }
                if(counterLikeSesion >= 1 ) {
                    hibernateLikeSession += filter +" session LIKE '%"+ session + "%' ";
                    counterLikeSesion++;
                    continue;
                }
            }
        }
        hibernateExactSession += " )";
        hibernateLikeSession +=") )";
        if(counterSession > 0)
            hibernateQuery += hibernateExactSession;
        if(counterLikeSesion > 0)
            hibernateQuery += hibernateLikeSession;

        return hibernateQuery;
    }
    public String processTime( List<TimeSearchSpecifications>  timeSearchSpecifications, String filter){
        String hibernateQuery=" ";
        String hibernateExactTime =" AND glaEvent.timestamp IN (";
        String hibernateRangeTime ="  ";
        String type = null;
        List<String> time = new ArrayList<>();
        int counterTime = 0;
        int counterRangeTime = 0;
        for(TimeSearchSpecifications timeSpec : timeSearchSpecifications){
            time = timeSpec.getTimestamp();
            type = timeSpec.getType();
            if(type.equals("EXACT")) {
                if(counterTime == 0) {

                    Date date = new Date(Long.parseLong(time.get(0))); // *1000 is to convert seconds to milliseconds
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // the format of your date
                    String formattedDate = sdf.format(date);
                    hibernateExactTime += "'"+formattedDate+"'";
                    counterTime++;
                    continue;
                }
                if(counterTime >= 1 ) {

                    Date date = new Date(Long.parseLong(time.get(0)));
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String formattedDate = sdf.format(date);
                    hibernateExactTime += ", '"+ formattedDate + "' ";
                    counterTime++;
                    continue;
                }
            }
            if(type.equals("RANGE")) {

                if(counterRangeTime == 0) {
                    Date startDate = new Date(Long.parseLong(time.get(0)));
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String startFormattedDate = sdf.format(startDate);
                    Date endDate = new Date(Long.parseLong(time.get(1)));
                    String endFormattedDate = sdf.format(endDate);
                    hibernateRangeTime += " AND glaEvent.timestamp BETWEEN ' " + startFormattedDate + "' AND '" + endFormattedDate+"' ";
                    counterRangeTime++;
                    continue;
                }
                if(counterRangeTime >= 1 ) {
                    Date startDate = new Date(Long.parseLong(time.get(0)));
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String startFormattedDate = sdf.format(startDate);
                    Date endDate = new Date(Long.parseLong(time.get(1)));
                    String endFormattedDate = sdf.format(endDate);
                    hibernateRangeTime += " AND glaEvent.timestamp BETWEEN ' " + startFormattedDate + "' AND '" + endFormattedDate+"' ";
                    counterRangeTime++;
                    continue;
                }
            }
        }
        hibernateExactTime += " )";
        if(counterTime > 0)
            hibernateQuery += hibernateExactTime;
        if(counterRangeTime > 0)
            hibernateQuery += hibernateRangeTime;

        return hibernateQuery;
    }

}
