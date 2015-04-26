package com.indicator_engine.indicator_system.Number;

import com.indicator_engine.model.indicator_system.Number.EntityValues;

import java.util.List;

/**
 * Created by Tanmaya Mahapatra on 26-04-2015.
 */
public class ProcessUserFilters {

    public String processEntities( List<EntityValues> entityValues){
        String hibernateQuery = null;
        String key, type, eValue;
        key = type = eValue = null;
        for (EntityValues eV : entityValues)
        {
            key = eV.getKey();
            type = eV.getType();
            eValue = eV.geteValues();
            if(type.equals("Text"))
            {
                if(eValue.equals("ALL")) {
                    hibernateQuery += " ( SELECT KEY FROM GlaEntity WHERE KEY = '"+key +"' ) OR";
                }
                else
                {
                    hibernateQuery += " ( SELECT KEY FROM GlaEntity WHERE KEY = '"+key +"' AND VALUE ='"+eValue+"' ) OR";
                }
            }



        }
        return hibernateQuery;
    }

}
