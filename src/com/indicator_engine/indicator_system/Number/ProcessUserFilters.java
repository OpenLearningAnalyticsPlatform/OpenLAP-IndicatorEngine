package com.indicator_engine.indicator_system.Number;

import com.indicator_engine.model.indicator_system.Number.EntityValues;
import com.indicator_engine.model.indicator_system.Number.SessionSpecifications;
import com.indicator_engine.model.indicator_system.Number.UserSearchSpecifications;
import org.drools.definition.process.*;

import java.util.List;

/**
 * Created by Tanmaya Mahapatra on 26-04-2015.
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
        String hibernateQuery = "(";
        String key, type, eValue;
        key = type = eValue = null;
        int counter = 1;
        int queryCounter = 0;
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
                    if(totalLength == 1) {
                        hibernateQuery += " ( SELECT key FROM GLAEntity WHERE  key = '"+key +"' )";
                    }
                    else{
                        if(counter == 1){
                            hibernateQuery += " ( SELECT key FROM GLAEntity WHERE key = '"+key +"' OR  ";
                        }
                        else if(counter == totalLength)
                            hibernateQuery += "  key = '"+key +"' ) ";
                        else
                            hibernateQuery += "  key = '"+key +"' OR  ";
                    }

                }
                else
                {
                    if(totalLength == 1)
                    {
                        hibernateQuery += " ( SELECT key FROM GLAEntity WHERE ( key = '"+key +"' "+ filter + " value LIKE'"+eValue+"' ) ) ";
                    }
                    else
                    {
                        if(counter == 1){
                            hibernateQuery += " ( SELECT key FROM GLAEntity WHERE ( key = '"+key +"' "+ filter + " value LIKE '"+eValue+"' ) OR ";
                        }
                        else if(counter == totalLength)
                            hibernateQuery += " (  key = '"+key +"' "+ filter + " value LIKE '"+eValue+"' ) ) ";
                        else
                            hibernateQuery += " (  key = '"+key +"' "+ filter + " value LIKE '"+eValue+"' ) OR";
                    }
                }
            }
            if(type.equals("REGEX"))
            {
                if(eValue.equals("ALL"))
                {
                    if(counter == 1){
                        hibernateQuery += " ( SELECT key FROM GLAEntity WHERE key LIKE '%"+key +"%' OR  ";
                    }
                    else if(counter == totalLength)
                        hibernateQuery += "  key LIKE '%"+key +"%' ) ";
                    else
                        hibernateQuery += "  key LIKE '%"+key +"%' OR  ";
                }
                else
                {
                    if(totalLength == 1)
                    {
                        hibernateQuery += " ( SELECT key FROM GLAEntity WHERE ( key LIKE '%"+key +"%' " + filter + " value LIKE '%"+eValue+"%' ) ) ";
                    }
                    else
                    {
                        if(counter == 1){
                            hibernateQuery += " ( SELECT key FROM GLAEntity WHERE ( key LIKE '%"+ key +"%' " + filter + " value LIKE '%"+eValue+"%' ) OR ";
                        }
                        else if(counter == totalLength)
                            hibernateQuery += " (  key LIKE '%"+key +"%' " + filter + " value LIKE '%"+eValue+"%' ) ) ";
                        else
                            hibernateQuery += " (  key LIKE '%"+key +"%' " + filter + " value LIKE '%"+eValue+"%' ) OR";
                    }
                }
            }

            counter++;
        }
        hibernateQuery += " )";
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
                        hibernateLikeEmailQuery += " ( SELECT username FROM GLAUser " +
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

}
