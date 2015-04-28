package com.indicator_engine.dao;

import com.indicator_engine.datamodel.GLAOperations;
import com.indicator_engine.datamodel.GLAUser;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Tanmaya Mahapatra on 01-04-2015.
 */
@SuppressWarnings({"unused", "unchecked"})
public class GLAOperationsDaoImpl implements GLAOperationsDao {
    static Logger log = Logger.getLogger(GLAOperationsDaoImpl.class.getName());
    @Autowired
    private SessionFactory factory;
    @Override
    @Transactional
    public long addNewOperation(GLAOperations glaOperation){
        log.info("Adding New GLA Operation" + glaOperation);
        factory.getCurrentSession().saveOrUpdate(glaOperation);
        log.info("Added New GLA Operation" + glaOperation + " with ID : "+ glaOperation.getId());
        return(glaOperation.getId());
    }

    @Override
    @Transactional
    public List<String> selectAllOperations(){
        Session session = factory.getCurrentSession();
        Criteria criteria = session.createCriteria(GLAOperations.class)
                .setProjection(Projections.property("operations"));
        return criteria.list();
    }

    @Override
    @Transactional
    public List<GLAOperations> loadOperationsRange(long maxId) {
        Session session = factory.getCurrentSession();
        Criteria criteria = session.createCriteria(GLAOperations.class);
        criteria.add(Restrictions.le("id", maxId));
        return  criteria.list();

    }

    @Override
    @Transactional
    public int getTotalOperations() {
        Session session = factory.getCurrentSession();
        return ((Number) session.createCriteria(GLAOperations.class).setProjection(Projections.rowCount()).uniqueResult()).intValue();

    }

}
