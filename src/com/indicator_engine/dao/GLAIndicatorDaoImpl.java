package com.indicator_engine.dao;

import com.indicator_engine.datamodel.GLAIndicator;
import com.indicator_engine.datamodel.GLAIndicatorProps;
import com.indicator_engine.datamodel.GLAQueries;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.List;

/**
 * Created by Tanmaya Mahapatra on 08-05-2015.
 */
public class GLAIndicatorDaoImpl implements GLAIndicatorDao {

    static Logger log = Logger.getLogger(GLAIndicatorDaoImpl.class.getName());
    @Autowired
    private SessionFactory factory;

    @Override
    @Transactional
    public long add(GLAIndicator glaIndicator, GLAQueries glaQueries) {
        log.info("Executing add() : GLAIndicatorDaoImpl : MODE : ADD OR UPDATE");
        Calendar calendar = Calendar.getInstance();
        java.util.Date now = calendar.getTime();
        factory.getCurrentSession().saveOrUpdate(glaIndicator);
        log.info("Indicator Saved..." + glaIndicator.getId());
        log.info("Saving associated Qeries \n");
        glaQueries.setGlaIndicator(glaIndicator);
        glaIndicator.getQueries().add(glaQueries);
        log.info("Finished Saving associated Qeries \n");
        factory.getCurrentSession().save(glaQueries);
        GLAIndicatorProps glaIndicatorProps  = new GLAIndicatorProps();
        glaIndicatorProps.setTotalExecutions(1);
        glaIndicatorProps.setLast_executionTime(new java.sql.Timestamp(now.getTime()));
        glaIndicatorProps.setGlaIndicator(glaIndicator);
        glaIndicator.setGlaIndicatorProps(glaIndicatorProps);
        factory.getCurrentSession().save(glaIndicatorProps);
        return glaIndicator.getId();
    }
    @Override
    @Transactional
    public List<GLAIndicator> loadByIndicatorID(long ID){
        Session session = factory.getCurrentSession();
        Criteria criteria = session.createCriteria(GLAIndicator.class);
        criteria.setFetchMode("queries", FetchMode.JOIN);
        criteria.setFetchMode("glaIndicatorProps", FetchMode.JOIN);
        criteria.add(Restrictions.eq("id", ID)).uniqueResult();
        return  criteria.list();

    }

    @Override
    @Transactional
    public List<GLAIndicator> displayall(){
        return factory.openSession().createQuery("from GLAIndicator gI").list();
    }
}
