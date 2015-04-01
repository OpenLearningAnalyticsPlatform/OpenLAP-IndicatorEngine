package com.indicator_engine.indicator_system;

import com.indicator_engine.dao.GLACategoryDao;
import com.indicator_engine.dao.GLAOperationsDao;
import com.indicator_engine.model.IndicatorDefnOperationForm;
import com.indicator_engine.model.SelectNumberParameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

/**
 * Created by Tanmaya Mahapatra on 31-03-2015.
 */
public class IndicatorPreProcessing implements IndicatorPreProcessingDao {
    @Autowired
    private ApplicationContext appContext;

    public String retrieveOperation(IndicatorDefnOperationForm indicatorDefnOperationForm) {
        return indicatorDefnOperationForm.getSelectedOperation();
    }

    public SelectNumberParameters initSelectNumberParametersObject(){
        SelectNumberParameters obj = new SelectNumberParameters();
        GLACategoryDao glacategoryBean = (GLACategoryDao) appContext.getBean("glaCategory");
        obj.setMinors(glacategoryBean.selectAllMinors());
        return obj;
    }

    public IndicatorDefnOperationForm initAvailableOperations_DB(){
        IndicatorDefnOperationForm obj = new IndicatorDefnOperationForm();
        GLAOperationsDao glaOperationsBean = (GLAOperationsDao) appContext.getBean("glaOperations");
        obj.setOperation(glaOperationsBean.selectAllOperations());
        return obj;

    }
}
