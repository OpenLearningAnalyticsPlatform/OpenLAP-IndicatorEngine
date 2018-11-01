/*
 * Open Learning Analytics Platform (OpenLAP) : Indicator Engine

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

import com.indicator_engine.model.indicator_system.Number.*;
import com.sun.deploy.util.StringUtils;
import jxl.write.DateTime;
import org.drools.definition.process.*;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Parses the User Input given during Indicator Definition process and generates equivalent Hibernate Queries.
 * @author Tanmaya Mahapatra
 * @since 26/04/2015
 */
@SuppressWarnings({"unused", "unchecked"})
public class ProcessUserFilters implements ProcessUserFiltersDao {

    @Override
    public String processEntities(List<EntityValues> entityValues, String filter) {
        return null;
    }

    @Override
    public String processUsers(List<UserSearchSpecifications> userSpecifications, String filter) {
        return null;
    }

    @Override
    public String processSource(List<String> sources, String filter) {
        return null;
    }

//    public String processEntitiesFrom(List<EntityValues> list){
//        int count = list.size();
//        String returnValue = "";
//
//        for(int i=1; i<=count; i++)
//            returnValue += ", OpenLAPEntity e" + (i+1);
//
//        return returnValue;
//    }

    public String processEntitiesFrom(IndicatorParameters params, String indReference){

        List<EntityValues> list = params.getIndicatorDataset().get(indReference).getEntityValues();

        int count = list.size();
        String returnValue = "";

        for(int i=1; i<=count; i++)
            returnValue += ", OpenLAPEntity e" + (i+1);

        return returnValue;
    }

//    public String processEntitiesJoins(String baseTableIdentity, List<EntityValues> list){
//        int count = list.size();
//        String returnValue = "";
//
//        for(int i=1; i<=count; i++)
//            returnValue += " and " + baseTableIdentity + " = e" + (i+1) +".eventByEventFk.eventId";
//
//        return returnValue;
//    }

    public String processEntitiesJoins(String baseTableIdentity, IndicatorParameters params, String indReference){
        List<EntityValues> entityValues = params.getIndicatorDataset().get(indReference).getEntityValues();

        int count = entityValues.size();
        String returnValue = "";

        for(int i=1; i<=count; i++)
            returnValue += " and " + baseTableIdentity + " = e" + (i+1) +".eventByEventFk.eventId";

        return returnValue;
    }

//    public String processEntitiesFilter(List<EntityValues> entityValues){
//        int count = entityValues.size();
//        String returnValue = "";
//        for(int i=1; i<=count; i++) {
//
//            List<String> eValues = Arrays.asList(entityValues.get(i - 1).geteValues().split("\\s*,\\s*"));
//
//            //returnValue += " and (e" + (i + 1) + ".entityKey = '" + entityValues.get(i - 1).getKey() + "' and e" + (i + 1) + ".value = '" + entityValues.get(i - 1).geteValues() + "')";
//
//            if(eValues.size()>1)
//                returnValue += " and (e" + (i + 1) + ".entityKey = '" + entityValues.get(i - 1).getKey() + "' and e" + (i + 1) + ".value in (" + processStringList(eValues) + "))";
//            else
//                returnValue += " and (e" + (i + 1) + ".entityKey = '" + entityValues.get(i - 1).getKey() + "' and e" + (i + 1) + ".value = '" + entityValues.get(i - 1).geteValues() + "')";
//        }
//
//        return returnValue;
//    }

    public String processEntitiesFilter(IndicatorParameters params, String indReference){

        List<EntityValues> entityValues = params.getIndicatorDataset().get(indReference).getEntityValues();

        int count = entityValues.size();
        String returnValue = "";
        for(int i=1; i<=count; i++) {

            List<String> eValues = Arrays.asList(entityValues.get(i - 1).geteValues().split("\\s*,\\s*"));

            //returnValue += " and (e" + (i + 1) + ".entityKey = '" + entityValues.get(i - 1).getKey() + "' and e" + (i + 1) + ".value = '" + entityValues.get(i - 1).geteValues() + "')";

            if(eValues.size()>1)
                returnValue += " and (e" + (i + 1) + ".entityKey = '" + entityValues.get(i - 1).getKey() + "' and e" + (i + 1) + ".value in (" + processStringList(eValues) + "))";
            else
                returnValue += " and (e" + (i + 1) + ".entityKey = '" + entityValues.get(i - 1).getKey() + "' and e" + (i + 1) + ".value = '" + entityValues.get(i - 1).geteValues() + "')";
        }

        return returnValue;
    }

    public String processUserFilter(String baseTableIdentity, IndicatorParameters params, String indReference){

        List<UserSearchSpecifications> userFilters = params.getIndicatorDataset().get(indReference).getUserSpecifications();

        int count = userFilters.size();
        String returnValue = "";
        for(int i=0; i<count; i++) {
            if(userFilters.get(i).getKey().equals("mine"))
                returnValue += " and " + baseTableIdentity + ".usersByUId.name = '" + userFilters.get(i).getValue() + "'";
            else if(userFilters.get(i).getKey().equals("notmine"))
                returnValue += " and " + baseTableIdentity + ".usersByUId.name != '" + userFilters.get(i).getValue() + "'";
        }

        return returnValue;
    }

//    public String processTimestamp(String baseTableIdentity, List<TimeSearchSpecifications> timeValues){
//        int count = timeValues.size();
//        String returnValue = "";
//
//        for(int i=0; i<count; i++) {
//            if(timeValues.get(i).getType().equals("fromDate"))
//                returnValue += " and " + baseTableIdentity + " >= " + timeValues.get(i).getTimestamp();
//            else if(timeValues.get(i).getType().equals("toDate"))
//                returnValue += " and " + baseTableIdentity + " <= " + timeValues.get(i).getTimestamp();
//        }
//
//        return returnValue;
//    }

    public String processTimestamp(String baseTableIdentity, IndicatorParameters params, String indReference){

        List<TimeSearchSpecifications> timeValues = params.getIndicatorDataset().get(indReference).getTimeSpecifications();

        int count = timeValues.size();
        String returnValue = "";

        for(int i=0; i<count; i++) {
            if(timeValues.get(i).getType().equals("fromDate"))
                returnValue += " and " + baseTableIdentity + " >= " + timeValues.get(i).getTimestamp();
            else if(timeValues.get(i).getType().equals("toDate"))
                returnValue += " and " + baseTableIdentity + " <= " + timeValues.get(i).getTimestamp();
        }

        return returnValue;
    }



    public String processStringList(List<String> list) {
        StringBuffer returnValue = new StringBuffer();

        for(Iterator listIterator = list.iterator(); listIterator.hasNext(); returnValue.append((String)listIterator.next())) {
            if(returnValue.length() != 0) {
                returnValue.append("','");
            }
        }
        returnValue.append("'");

        return "'"+returnValue.toString();
    }

    public String processStringList(IndicatorParameters params, String attribute, String indReference) {
        StringBuffer returnValue = new StringBuffer();
        List<String> list = null;

        switch(attribute)
        {
            case "action":
                list = params.getIndicatorDataset().get(indReference).getSelectedAction();
                break;
            case "platform":
                list = params.getIndicatorDataset().get(indReference).getSelectedPlatform();
                break;
            case "source":
                list = params.getIndicatorDataset().get(indReference).getSelectedSource();
                break;
            case "entityDisplay":
                list = params.getIndicatorDataset().get(indReference).getEntityDisplayObjects();
                break;
            default:
                break;
        }

        if(list != null) {
            for (Iterator listIterator = list.iterator(); listIterator.hasNext(); returnValue.append((String) listIterator.next())) {
                if (returnValue.length() != 0) {
                    returnValue.append("','");
                }
            }
            returnValue.append("'");

            return "'"+returnValue.toString();
        }
        else
            return "";
    }

//    public String processIntegerList(List<Integer> list) {
//        StringBuffer returnValue = new StringBuffer();
//
//        for(Iterator listIterator = list.iterator(); listIterator.hasNext(); returnValue.append((int)listIterator.next())) {
//            if(returnValue.length() != 0) {
//                returnValue.append(",");
//            }
//        }
//
//        return returnValue.toString();
//    }

    public String processIntegerList(IndicatorParameters params, String attribute, String indReference) {
        StringBuffer returnValue = new StringBuffer();
        List<Integer> list = null;

        switch(attribute)
        {
            case "minor":
                list = params.getIndicatorDataset().get(indReference).getSelectedMinor();
                break;
            default:
                break;
        }

        if(list != null) {
            for (Iterator listIterator = list.iterator(); listIterator.hasNext(); returnValue.append((int) listIterator.next())) {
                if (returnValue.length() != 0) {
                    returnValue.append(",");
                }
            }

            return returnValue.toString();
        }
        else
            return "";
    }

    //region Commented out code from Tanmaya and SQL
/*
    //SQL Code
    @Override
    public String processSource(List<String> sources, String filter) {
        String hibenateQuery = "(";
        int counter = 0;
        for (String source : sources) {
            if (counter == 0) {
                hibenateQuery += "'" + source + "' ";
                counter++;
                continue;
            }
            if (counter >= 1) {
                hibenateQuery += ", '" + source + "' ";
                counter++;
                continue;
            }
        }
        hibenateQuery += ")";
        return hibenateQuery;
    }

    //Tanmaya Code
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

    //SQL Code
    @Override
    public String processEntities(List<EntityValues> entityValues, String filter) {
        String hibernateQuery = "";

        int entityValueCounter = 0;

        String key, type, eValue;
        key = type = eValue = null;

        //int totalLength = entityValues.size();

        for (EntityValues eV : entityValues) {
            key = eV.getKey();
            //type = eV.getType();
            eValue = eV.geteValues();
            //if(type.equals("Text"))
            //{
            if (!eValue.equals("ALL")) {
                if (entityValueCounter == 0) {
                    hibernateQuery += " " + key + " = '" + eValue + "' ";
                    entityValueCounter++;
                } else {
                    hibernateQuery += filter + " " + key + " = '" + eValue + "' ";
                    entityValueCounter++;
                }
            }
        }
        return hibernateQuery;
    }

    //Tanmaya Code
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

    //SQL Code
    @Override
    public String processUsers(List<UserSearchSpecifications> userSpecifications, String filter) {
        String hibernateQuery = "";

        // Need to implement to get the Name of the current user and return that.

        return hibernateQuery;
    }

    //Tanmaya Code
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

    //SQL Code
    public String processSessions(List<SessionSpecifications> sessionSpecifications, String filter) {
        String hibernateQuery = "AND (";
        String hibernateExactSession = " E.Session IN (";
        String hibernateLikeSession = "";
        String session, type;
        session = type = null;
        int counterSession = 0;
        int counterLikeSesion = 0;
        for (SessionSpecifications sessionSpec : sessionSpecifications) {
            session = sessionSpec.getSession();
            type = sessionSpec.getType();
            if (type.equals("EXACT")) {
                if (counterSession <= 0)
                    hibernateExactSession += "'" + session + "'";
                else
                    hibernateExactSession += ", '" + session + "'";
                counterSession++;
            }
            if (type.equals("REGEX")) {
                if (counterLikeSesion == 0)
                    hibernateLikeSession += " E.Session LIKE '%" + session + "%'";
                else
                    hibernateLikeSession += filter + " E.Session LIKE '%" + session + "%'";
                counterLikeSesion++;
            }
        }

        hibernateExactSession += ")";

        if (counterSession > 0)
            hibernateQuery += hibernateExactSession;

        if (counterSession > 0 && counterLikeSesion > 0)
            hibernateQuery += filter + hibernateLikeSession;
        else if (counterSession <= 0 && counterLikeSesion > 0)
            hibernateQuery += hibernateLikeSession;

        hibernateQuery += ")";

        //When there are not filters than clearing the hibernate query
        if (counterSession <= 0 && counterLikeSesion <= 0)
            hibernateQuery = "";

        return hibernateQuery;
    }

    //Tanmaya Code
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

    //SQL Code
    public String processTime(List<TimeSearchSpecifications> timeSearchSpecifications, String filter) {
        String hibernateStartTime = "";
        String hibernateEndTime = "";
        String type = null;
        List<String> time = new ArrayList<>();
        int counterTime = 0;
        int counterRangeTime = 0;

        for (TimeSearchSpecifications timeSpec : timeSearchSpecifications) {
            time = timeSpec.getTimestamp();
            type = timeSpec.getType();

            Date date = new Date(Long.parseLong(time.get(0))); // *1000 is to convert seconds to milliseconds
            long uDate = date.getTime() / 1000L;

            if (type.equals("startdate") && uDate > 0)
                hibernateStartTime = " AND E.Timestamp >= " + uDate;
            else if (type.equals("enddate") && uDate > 0)
                hibernateEndTime = " AND E.Timestamp <= " + uDate;

        }
        return hibernateStartTime + hibernateEndTime;
    }

    //SQL Code
    public String processNullValues(String columnNames){

        StringBuilder updatedColStringBuilder = new StringBuilder();

        String emptyQueryCheck = " IS NOT NULL";
        String[] explodedColumns = columnNames.split(",");

        String appendedCol;
        for (String column : explodedColumns) {
            if (updatedColStringBuilder.length() > 0) {
                updatedColStringBuilder.append(" AND ");
            }
            appendedCol = column + emptyQueryCheck;
            updatedColStringBuilder.append(appendedCol);
        }
        return updatedColStringBuilder.toString();
    }
    */
//endregion
}
