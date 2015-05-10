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
