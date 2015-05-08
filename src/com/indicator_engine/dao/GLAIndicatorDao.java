package com.indicator_engine.dao;

import com.indicator_engine.datamodel.GLAIndicator;
import com.indicator_engine.datamodel.GLAQueries;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Tanmaya Mahapatra on 08-05-2015.
 */
public interface GLAIndicatorDao {
    public void add(GLAIndicator glaIndicator, GLAQueries glaQueries);
}
