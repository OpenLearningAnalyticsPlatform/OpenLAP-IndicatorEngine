package com.indicator_engine.indicator_system;

import com.indicator_engine.dao.GLACategoryDao;
import com.indicator_engine.dao.GLAEntityDao;
import com.indicator_engine.dao.GLAEventDao;
import com.indicator_engine.dao.GLAOperationsDao;
import com.indicator_engine.model.IndicatorDefnOperationForm;
import com.indicator_engine.model.SelectNumberParameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Tanmaya Mahapatra on 31-03-2015.
 */
public class IndicatorPreProcessing implements IndicatorPreProcessingDao {
    @Autowired
    private ApplicationContext appContext;

    @Override
    public String retrieveOperation(IndicatorDefnOperationForm indicatorDefnOperationForm) {
        return indicatorDefnOperationForm.getSelectedOperation();
    }

    @Override
    public SelectNumberParameters initSelectNumberParametersObject(){
        SelectNumberParameters obj = new SelectNumberParameters();
        GLACategoryDao glacategoryBean = (GLACategoryDao) appContext.getBean("glaCategory");
        obj.setMinors(glacategoryBean.selectAllMinors());
        return obj;
    }
    @Override
    public IndicatorDefnOperationForm initAvailableOperations_DB(){
        IndicatorDefnOperationForm obj = new IndicatorDefnOperationForm();
        GLAOperationsDao glaOperationsBean = (GLAOperationsDao) appContext.getBean("glaOperations");
        obj.setOperation(glaOperationsBean.selectAllOperations());
        return obj;

    }

    @Override
    public List<String>  initAvailableEntities_DB(String minor){
        GLACategoryDao glacategoryBean = (GLACategoryDao) appContext.getBean("glaCategory");
        long category_id = glacategoryBean.findCategoryID(minor);
        GLAEntityDao glaEntityBean = (GLAEntityDao) appContext.getBean("glaEntity");
        return glaEntityBean.loadEntitiesByCategoryID(category_id);
    }
    @Override
    public List<String> initAvailableEvents_DB(String minor){
        GLACategoryDao glacategoryBean = (GLACategoryDao) appContext.getBean("glaCategory");
        GLAEventDao glaEventBean = (GLAEventDao) appContext.getBean("glaEvent");
        long category_id = glacategoryBean.findCategoryID(minor);
        return glaEventBean.loadEventByCategoryID(category_id);
    }
}
