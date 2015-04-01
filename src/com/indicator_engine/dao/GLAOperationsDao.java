package com.indicator_engine.dao;

import com.indicator_engine.datamodel.GLAOperations;

import java.util.List;

/**
 * Created by Tanmaya Mahapatra on 01-04-2015.
 */
public interface GLAOperationsDao {
    public long addNewOperation(GLAOperations glaOperation);
    public List<String> selectAllOperations();
    public int getTotalOperations();
    public List<GLAOperations> loadOperationsRange(long maxId);
}
