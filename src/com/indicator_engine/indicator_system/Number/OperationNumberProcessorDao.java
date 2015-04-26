package com.indicator_engine.indicator_system.Number;

import com.indicator_engine.model.indicator_system.Number.SelectNumberParameters;

/**
 * Created by Tanmaya Mahapatra on 01-04-2015.
 */
public interface OperationNumberProcessorDao {

    public long computeResult(SelectNumberParameters selectNumberParameters);
}
