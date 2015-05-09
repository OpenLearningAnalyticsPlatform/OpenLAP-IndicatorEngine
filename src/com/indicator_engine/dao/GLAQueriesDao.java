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
}
